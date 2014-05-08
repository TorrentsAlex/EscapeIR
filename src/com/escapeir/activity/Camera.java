package com.escapeir.activity;

import java.util.ArrayList;

import android.app.ProgressDialog;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.example.escapeir.R;

public class Camera extends CatchoomActivity implements OnClickListener,
		CatchoomResponseHandler, CatchoomImageHandler {

	private CatchoomCloudRecognition cloudRecognition;
	private CatchoomTracking catchoomTracking;
	private CatchoomCamera catchoomCamera;

	private Button btnPhoto;
	private Button btnIntro;

	private TextView txtGuide;
	private TextView txtBody;
	private TextView txtTitle;

	private Chronometer chronometer;

	private RelativeLayout layoutHelp;
	private LinearLayout layoutPhoto;
	private LinearLayout layoutIntro;

	private int COUNT_REFERENCE=0;
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
		EscapeIRApplication.initialized = true;
		CatchoomSDK.init(getApplicationContext(), this);

		cloudRecognition = CatchoomSDK.getCloudRecognition();
		cloudRecognition.setResponseHandler(this);

		cloudRecognition.connect(EscapeIRApplication.escapeToken);

		catchoomTracking = CatchoomSDK.getTracking();

		catchoomCamera = CatchoomSDK.getCamera();
		catchoomCamera.setImageHandler(this);
	
		btnPhoto = (Button) findViewById(R.id.btn_photo);
		btnIntro = (Button) findViewById(R.id.btn_start);
		layoutPhoto = (LinearLayout) findViewById(R.id.layout_btn_photo);
		layoutHelp = (RelativeLayout) findViewById(R.id.layout_btn_help);
		layoutIntro = (LinearLayout) findViewById(R.id.layout_introducing);
		chronometer = (Chronometer) findViewById(R.id.chronometer);
		txtGuide = (TextView) findViewById(R.id.txt_guide);
		txtBody = (TextView) findViewById(R.id.txt_body);
		txtTitle = (TextView) findViewById(R.id.text_title);

		btnPhoto.setOnClickListener(this);
		btnIntro.setOnClickListener(this);
		
		layoutPhoto.setVisibility(View.INVISIBLE);
		layoutHelp.setVisibility(View.INVISIBLE);

		txtTitle.setText(R.string.intro_classroom);
		txtBody.setText(EscapeIRApplication.INTRO_ROOM);
		
	}

	@Override
	public void connectCompleted() {
		Log.d(EscapeIRApplication.TAG, "connect Completed");

	}

	@Override
	public void requestFailedResponse(int arg0,
			CatchoomCloudRecognitionError arg1) {
		Log.i(EscapeIRApplication.TAG, "requestFailed: " + arg1.getErrorMessage());
		
		int errorCode = arg1.getErrorCode();
		
		String error = "";
		switch (errorCode) {
		case CatchoomCloudRecognitionError.ErrorCodes.IMAGE_NO_DETAILS:
			error = "Porfavor, haz una foto a un objeto con mas detalles";
			break;
		case CatchoomCloudRecognitionError.ErrorCodes.CONNECTION_ERROR:
			error = "Error de conexión";
			break;
		default:
			error = "Error desconocido";
		break;
		}
		Toast.makeText(this, error, Toast.LENGTH_LONG).show();
		catchoomCamera.restartCameraPreview();
	}

	@Override
	public void searchCompleted(ArrayList<CatchoomCloudRecognitionItem> results) {
		Log.i(EscapeIRApplication.TAG, "searchCompleted");
		boolean haveContent = false;
		catchoomCamera.restartCameraPreview();
		Log.i(EscapeIRApplication.TAG, "restart Camera");

		// Look for trackable results
		for (CatchoomCloudRecognitionItem item : results) {
			haveContent = true;
			Log.i(EscapeIRApplication.TAG, item.getItemName());
			Log.i(EscapeIRApplication.TAG, "item a scanear: "+EscapeIRApplication.CHOOSEN_ROOM[COUNT_REFERENCE]);
			Log.i(EscapeIRApplication.TAG, "CountReference: "+ COUNT_REFERENCE+ "|| length: "+EscapeIRApplication.CHOOSEN_ROOM.length);
			if (item.getItemName().toLowerCase()
					.equals(EscapeIRApplication.CHOOSEN_ROOM[COUNT_REFERENCE])) {

				// Item Augmented Reality
				if (item.isAR()) {
					CatchoomARItem itemAR = (CatchoomARItem) item;
					if (itemAR.getContents().size() > 0) {
						catchoomTracking.addItem(itemAR);
						catchoomTracking.startTracking();
					}
					// Item Image Recognition
				} else {
					Toast.makeText(this, EscapeIRApplication.REWARD_ROOM[COUNT_REFERENCE], Toast.LENGTH_SHORT)
							.show();
				}
				// finish the game, go to results
				if (COUNT_REFERENCE == EscapeIRApplication.CHOOSEN_ROOM.length-1) {
					EscapeIRApplication.USER_TIME = (String) chronometer.getText();
					chronometer.stop();
					Log.i(EscapeIRApplication.TAG,
							EscapeIRApplication.USER_NAME + "-" + EscapeIRApplication.USER_TIME);

					new Connect(this, Camera.this).execute(true);
					new Connect(this, Camera.this).execute(false);
					
					EscapeIRApplication.GUIDE_ROOM = null;
					EscapeIRApplication.CHOOSEN_ROOM = null;
					
					//finish();
				} else {
					COUNT_REFERENCE++;

					txtGuide.setText(EscapeIRApplication.GUIDE_ROOM[COUNT_REFERENCE]);
				}

			} else {
				Toast.makeText(this, "Objeto incorrecto aunque mas adelante sera útil...", Toast.LENGTH_LONG).show();
			}
		}
		// Nothing found
		if (!haveContent) {
			Toast.makeText(this,
					"No se ha encontrado nada, por favor sigue la guia",
					Toast.LENGTH_LONG).show();
		}
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
		case R.id.btn_photo:
			catchoomCamera.takePicture();
			break;
		case R.id.btn_start:
			// Start Game
			txtGuide.setText(EscapeIRApplication.GUIDE_ROOM[0]);

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
		if (chronometer != null) {
			chronometer.stop();
		}
		Log.i("EscapeIR", "onPause");

		super.onPause();
	}

	@Override
	public void onResume() {
		if (chronometer != null) {
			chronometer.start();
			chronometer.setVisibility(View.VISIBLE);
		}
		Log.i("EscapeIR", "onResume");

		super.onResume();
	}

	@Override
	public void finish() {
		catchoomTracking.removeAllItems();
		//if (array != null) {
		//	array.clear();
		//}
		super.finish();
	}
}
