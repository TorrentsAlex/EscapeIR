package com.escapeir.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.catchoom.CatchoomARItem;
import com.catchoom.CatchoomActivity;
import com.catchoom.CatchoomCamera;
import com.catchoom.CatchoomCameraView;
import com.catchoom.CatchoomCloudRecognition;
import com.catchoom.CatchoomCloudRecognitionError;
import com.catchoom.CatchoomCloudRecognitionItem;
import com.catchoom.CatchoomImage;
import com.catchoom.CatchoomImageHandler;
import com.catchoom.CatchoomResponseHandler;
import com.catchoom.CatchoomSDK;
import com.catchoom.CatchoomTracking;
import com.escapeir.application.EscapeIRApplication;
import com.escapeir.server.Connect;
import com.escapeir.util.Preferences;
import com.example.escapeir.R;

public class Camera extends CatchoomActivity implements OnClickListener,
		CatchoomResponseHandler, CatchoomImageHandler {

	private CatchoomCloudRecognition cloudRecognition;
	private CatchoomTracking catchoomTracking;
	private CatchoomCamera catchoomCamera;

	private Button btnHelp;
	private Button btnPhoto;
	private Button btnIntro;
	
	private Chronometer chronometer;
	
	private LinearLayout layoutHelp;
	private LinearLayout layoutPhoto;
	private LinearLayout layoutIntro;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onPostCreate() {
		View mainLayout = (View) getLayoutInflater().inflate(
				R.layout.photo_layout, null);
		CatchoomCameraView cameraView = (CatchoomCameraView) mainLayout
				.findViewById(R.id.camera_preview);
		super.setCameraView(cameraView);
		setContentView(mainLayout);

		// Initialize the SDK.
		CatchoomSDK.init(getApplicationContext(), this);

		cloudRecognition = CatchoomSDK.getCloudRecognition();
		cloudRecognition.setResponseHandler(this);

		cloudRecognition.connect(EscapeIRApplication.escapeToken);

		catchoomTracking = CatchoomSDK.getTracking();

		catchoomCamera = CatchoomSDK.getCamera();
		catchoomCamera.setImageHandler(this);

		btnHelp = (Button) findViewById(R.id.btn_help);
		btnPhoto = (Button) findViewById(R.id.btn_photo);
		btnIntro = (Button) findViewById(R.id.btn_start);
		layoutPhoto = (LinearLayout) findViewById(R.id.layout_btn_photo);
		layoutHelp = (LinearLayout) findViewById(R.id.layout_btn_help);
		layoutIntro = (LinearLayout) findViewById(R.id.layout_introducing);
		chronometer = (Chronometer) findViewById(R.id.chronometer);
		
		btnHelp.setOnClickListener(this);
		btnPhoto.setOnClickListener(this);
		btnIntro.setOnClickListener(this);
		
		layoutPhoto.setVisibility(View.INVISIBLE);
		layoutHelp.setVisibility(View.INVISIBLE);
		
		// cargar los arrays
	}

	@Override
	public void connectCompleted() {
		Log.d(EscapeIRApplication.TAG, "connect Completed");

	}

	@Override
	public void requestFailedResponse(int arg0,
			CatchoomCloudRecognitionError arg1) {
		Log.i(EscapeIRApplication.TAG, arg1.getErrorMessage());

	}

	@Override
	public void searchCompleted(ArrayList<CatchoomCloudRecognitionItem> results) {
		boolean haveContent = false;
		Log.i(EscapeIRApplication.TAG, "searchCompleted");
		
		// Look for trackable results
		for (CatchoomCloudRecognitionItem item : results) {
			haveContent = true;
			Log.i(EscapeIRApplication.TAG, item.getItemName());

			if (item.getItemName()
					.equals(EscapeIRApplication.REFERENCE_ROOM[EscapeIRApplication.COUNT_REFERENCE])) {
				EscapeIRApplication.COUNT_REFERENCE++;
				// Item Augmented Reality
				if (item.isAR()) {
					CatchoomARItem itemAR = (CatchoomARItem) item;
					if (itemAR.getContents().size() > 0) {
						catchoomTracking.addItem(itemAR);
						catchoomTracking.startTracking();
					}
					// Item Image Recognition
				} else { 
					Toast.makeText(this, item.getItemName(), Toast.LENGTH_SHORT)
					.show();
				}
				// finish the game, go to results
				if(EscapeIRApplication.COUNT_REFERENCE == EscapeIRApplication.REFERENCE_ROOM.length) {
					String time = (String) chronometer.getText();
					Connect connect = new Connect();
					Log.i(EscapeIRApplication.TAG, EscapeIRApplication.USER_NAME+ "-"+ time);
					connect.setUser(EscapeIRApplication.USER_NAME, time);
					chronometer.stop();
					Intent intent = new Intent(this, Results.class);
					intent.putExtra("time", time);
					startActivity(intent);
					finish();
				}

			} else {
				Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
			}
		}
		// Nothing found
		if(!haveContent) {
			
		}
		catchoomCamera.restartCameraPreview();
		Log.i(EscapeIRApplication.TAG, "restart Camera");

	}

	@Override
	public void requestImageError(String error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestImageReceived(CatchoomImage image) {
		Log.i(EscapeIRApplication.TAG, "searchImage");

		cloudRecognition
				.searchWithImage(EscapeIRApplication.escapeToken, image);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_help:
			break;
		case R.id.btn_photo:
			catchoomCamera.takePicture();
			break;
		case R.id.btn_start:
			// Start Game
			layoutPhoto.setVisibility(View.VISIBLE);
			layoutHelp.setVisibility(View.VISIBLE);
			layoutIntro.setVisibility(View.INVISIBLE);
			
			chronometer.setBase(SystemClock.elapsedRealtime());
			chronometer.start();
			break;
		}

	}

	@Override
	public void onPause() {
		if(chronometer!=null) {
			chronometer.stop();
		}
		Log.i("EscapeIR","onPause");
		
		super.onPause();
	}

	@Override
	public void onResume() {
		if(chronometer!=null) {
			chronometer.start();
			chronometer.setVisibility(View.VISIBLE);
		}
		Log.i("EscapeIR","onResume");
		
		super.onResume();
	}

}
