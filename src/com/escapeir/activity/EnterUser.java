package com.escapeir.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.escapeir.application.EscapeIRApplication;
import com.escapeir.preferences.Preferences;
import com.example.escapeir.R;

public class EnterUser extends Activity implements OnClickListener {
	
	private EditText editUser;
	private Button buttonUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		if(!Preferences.setUsername().equals(EscapeIRApplication.preferencesUserDefault)) {
			Intent mainIntent = new Intent(this, Main.class);
			startActivity(mainIntent);
		}

		//TODO: introducir el nickname y guardarlo en las preferences
		editUser = (EditText) findViewById(R.id.edit_user);
		buttonUser = (Button) findViewById(R.id.btn_name);
		buttonUser.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btn_name) {
			
			if(null != EscapeIRApplication.preferencesUser) {
				Log.i(EscapeIRApplication.TAG,"save user name");
				Preferences.saveUsername(editUser.getText().toString());
			}
			
			editUser.setText("");
			Log.i(EscapeIRApplication.TAG,"go Main.class");
			Intent mainIntent = new Intent(this, Main.class);
			startActivity(mainIntent);
			finish();
		}
	}

}
