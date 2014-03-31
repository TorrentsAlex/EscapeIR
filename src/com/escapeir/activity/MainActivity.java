package com.escapeir.activity;

import com.escapeir.application.EscapeIRApplication;
import com.escapeir.logic.Logic;
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
		Intent cameraIntent = new Intent(this, CameraActivity.class);
		
		switch (v.getId()) {
		
		case R.id.btn_bedroom: 
			Logic.chargeArray(EscapeIRApplication.MODE_BEDROOM, this);
			
			cameraIntent.putExtra("escapeMode", EscapeIRApplication.MODE_BEDROOM);
			startActivity(cameraIntent);
			break;
		case R.id.btn_kitchen: 
			Logic.chargeArray(EscapeIRApplication.MODE_KITCHEN, this);
			
			cameraIntent.putExtra("escapeMode", EscapeIRApplication.MODE_KITCHEN);
			startActivity(cameraIntent);
			break;
		case R.id.btn_classroom: 
			Logic.chargeArray(EscapeIRApplication.MODE_CLASSROOM, this);
			
			cameraIntent.putExtra("escapeMode", EscapeIRApplication.MODE_CLASSROOM);
			startActivity(cameraIntent);
			break;
		}		
	}
	
}
