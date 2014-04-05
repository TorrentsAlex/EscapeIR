package com.escapeir.activity;

import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
import com.example.escapeir.R;

public class Camera extends CatchoomActivity implements OnClickListener, CatchoomResponseHandler, CatchoomImageHandler {

	private CatchoomCloudRecognition cloudRecognition;
	private CatchoomTracking catchoomTracking;
	private CatchoomCamera catchoomCamera;
	
	private Button btnHelp;
	private Button btnPhoto;
	
	@Override
	public void onPostCreate() {
        View mainLayout= (View) getLayoutInflater().inflate(R.layout.photo_layout, null);
        CatchoomCameraView cameraView = (CatchoomCameraView) mainLayout.findViewById(R.id.camera_preview);
        super.setCameraView(cameraView);
        setContentView(mainLayout);

        //Initialize the SDK.
        CatchoomSDK.init(getApplicationContext(),this);
       
        cloudRecognition= CatchoomSDK.getCloudRecognition();
        cloudRecognition.setResponseHandler(this);

        cloudRecognition.connect(EscapeIRApplication.escapeToken);

        catchoomTracking = CatchoomSDK.getTracking();
        
        catchoomCamera = CatchoomSDK.getCamera();
        catchoomCamera.setImageHandler(this);
        
        btnHelp =(Button) findViewById(R.id.btn_help);
        btnPhoto=(Button) findViewById(R.id.btn_photo);
        btnHelp.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
        
        //cargar los arrays
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
    	Log.i(EscapeIRApplication.TAG, "name: "+results.get(0).getItemName());
    	
        // Look for trackable results
        for (CatchoomCloudRecognitionItem item : results) {
        	Log.i(EscapeIRApplication.TAG, item.getItemName());

            // Setup the AR experience with content provided in the response
            if (item.isAR()) {
                CatchoomARItem itemAR = (CatchoomARItem) item;
                if (itemAR.getContents().size() > 0) {
                    catchoomTracking.addItem(itemAR);
                    catchoomTracking.startTracking();
                }
            }else {
            	if(item.getItemName().equals(EscapeIRApplication.REFERENCE_ROOM[EscapeIRApplication.COUNT_REFERENCE])){
            		EscapeIRApplication.COUNT_REFERENCE++;
            		Toast.makeText(this,  item.getItemName() , Toast.LENGTH_SHORT).show();
            	}else {
            		Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
            	}
            }
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

        cloudRecognition.searchWithImage(EscapeIRApplication.escapeToken, image);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_help: break;
		case R.id.btn_photo:
			catchoomCamera.takePicture();
			break;
		}
		
	}

}
