package com.escapeir.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.escapeir.R;

public class Results extends Activity {
	private TextView userTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_layout);
		
		userTime = (TextView) findViewById(R.id.timeUser);
		
		userTime.setText(getIntent().getExtras().getString("time"));
	}
	
}
