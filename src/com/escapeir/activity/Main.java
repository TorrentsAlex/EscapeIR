package com.escapeir.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.escapeir.application.EscapeIRApplication;
import com.escapeir.logic.Logic;
import com.escapeir.server.Connect;
import com.example.escapeir.R;

public class Main extends Activity implements OnClickListener {
	
	private Button howToPlay;
	private Button ranking;
	
	private Button classroom;
	private Button kitchen;

	private EditText editUser;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		classroom = (Button) findViewById(R.id.btn_classroom);
		kitchen = (Button) findViewById(R.id.btn_kitchen);
		editUser = (EditText) findViewById(R.id.edit_user);
		howToPlay = (Button) findViewById(R.id.btn_how_to_play);
		ranking = (Button) findViewById(R.id.btn_show_ranking);
		TextView textUser = (TextView) findViewById(R.id.text_user);
		
		classroom.setOnClickListener(this);
		kitchen.setOnClickListener(this);
		howToPlay.setOnClickListener(this);
		ranking.setOnClickListener(this);
		
		if (null != EscapeIRApplication.USER_NAME)
			textUser.setText(EscapeIRApplication.USER_NAME);
	}

	@Override
	public void onClick(View v) {
		Intent cameraIntent = new Intent(this, Camera.class);

		switch (v.getId()) {
		case R.id.btn_kitchen:
			Logic.chargeArray(EscapeIRApplication.MODE_KITCHEN, this);

			cameraIntent.putExtra("escapeMode",
					EscapeIRApplication.MODE_KITCHEN);
			startActivity(cameraIntent);
			break;
		case R.id.btn_classroom:
			Logic.chargeArray(EscapeIRApplication.MODE_CLASSROOM, this);

			cameraIntent.putExtra("escapeMode",
					EscapeIRApplication.MODE_CLASSROOM);
			startActivity(cameraIntent);
			break;
		case R.id.btn_show_ranking:
			new Connect(this,Main.this).execute(false);

		}
	}

}
