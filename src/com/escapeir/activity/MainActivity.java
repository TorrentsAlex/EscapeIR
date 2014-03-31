package com.escapeir.activity;

import com.escapeir.application.EscapeIRApplication;
import com.example.escapeir.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	private Button bedroom;
	private Button classroom;
	private Button kitchen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bedroom = (Button) findViewById(R.id.btn_bedroom);
		classroom=(Button) findViewById(R.id.btn_classroom);
		kitchen=(Button) findViewById(R.id.btn_kitchen);
		
		bedroom.setOnClickListener(this);
		classroom.setOnClickListener(this);
		kitchen.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_bedroom: 
			chargeArray(EscapeIRApplication.MODE_BEDROOM);
			Intent bedroomIntent = new Intent(this, CameraActivity.class);
			bedroomIntent.putExtra("escapeMode", EscapeIRApplication.MODE_BEDROOM);
			startActivity(bedroomIntent);
			break;
		case R.id.btn_kitchen: 
			chargeArray(EscapeIRApplication.MODE_KITCHEN);
			Intent kitchenIntent = new Intent(this, CameraActivity.class);
			kitchenIntent.putExtra("escapeMode", EscapeIRApplication.MODE_KITCHEN);
			startActivity(kitchenIntent);
			break;
		case R.id.btn_classroom: 
			chargeArray(EscapeIRApplication.MODE_CLASSROOM);
			Intent classroomIntent = new Intent(this, CameraActivity.class);
			classroomIntent.putExtra("escapeMode", EscapeIRApplication.MODE_CLASSROOM);
			startActivity(classroomIntent);
			break;
		}		
	}
	public void chargeArray(int room) {
		switch(room) {
		case EscapeIRApplication.MODE_BEDROOM:
			EscapeIRApplication.REFERENCE_ROOM = this.getResources().getStringArray(R.array.reference_prueba);
			for(byte i=0;i<EscapeIRApplication.REFERENCE_ROOM.length;i++) {
				Log.i(EscapeIRApplication.TAG, EscapeIRApplication.REFERENCE_ROOM[i]+ i);
			}
			break;
		case EscapeIRApplication.MODE_CLASSROOM:
			EscapeIRApplication.REFERENCE_ROOM = this.getResources().getStringArray(R.array.reference_prueba);
			for(byte i=0;i<EscapeIRApplication.REFERENCE_ROOM.length;i++) {
				Log.i(EscapeIRApplication.TAG, EscapeIRApplication.REFERENCE_ROOM[i] + i);
			}
			break;
		case EscapeIRApplication.MODE_KITCHEN:
			EscapeIRApplication.REFERENCE_ROOM = this.getResources().getStringArray(R.array.reference_prueba);
			for(byte i=0;i<EscapeIRApplication.REFERENCE_ROOM.length;i++) {
				Log.i(EscapeIRApplication.TAG, EscapeIRApplication.REFERENCE_ROOM[i]+ i);
			}
			break;
		}
	}

}
