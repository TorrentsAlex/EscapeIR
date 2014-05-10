//	com.escapeir is free software. You may use it under the MIT license, which is copied
//	below and available at
//	
//		http://opensource.org/licenses/MIT
//
//	The MIT License (MIT)
//
//	Copyright (c) <2014> Alex Torrents Sanchez
//
//	Permission is hereby granted, free of charge, to any person obtaining a copy
//	of this software and associated documentation files (the "Software"), to deal
//	in the Software without restriction, including without limitation the rights
//	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//	copies of the Software, and to permit persons to whom the Software is
//	furnished to do so, subject to the following conditions:
//	
//	The above copyright notice and this permission notice shall be included in
//	all copies or substantial portions of the Software.
//	
//	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//	THE SOFTWARE.

/**
*	author: Alex Torrents Sanchez
*/
package com.escapeir.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.escapeir.application.EscapeIRApplication;
import com.escapeir.util.Preferences;
import com.example.escapeir.R;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;

public class EnterUser extends Activity implements OnClickListener,  ConnectionCallbacks, OnConnectionFailedListener {
	
	private EditText editUser;
	private Button buttonUser;
	private TextView textInvalidUser;

	private GoogleApiClient mGoogleApiClient;
	 /* A flag indicating that a PendingIntent is in progress and prevents
	   * us from starting further intents.
	   */
	private boolean mIntentInProgress;

	/* Request code used to invoke sign in user interactions. */
	private static final int RC_SIGN_IN = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_user);
		
		if(!Preferences.setUsername().equals(EscapeIRApplication.preferencesUserDefault)) {
			Intent mainIntent = new Intent(this, Main.class);
			startActivity(mainIntent);
			finish();
		}

		textInvalidUser = (TextView) findViewById(R.id.txt_invalid_user);
		editUser = (EditText) findViewById(R.id.edit_user);
		buttonUser = (Button) findViewById(R.id.btn_name);
		buttonUser.setOnClickListener(this);
		
		mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks( this)
        .addOnConnectionFailedListener(this)
        .addApi(Plus.API, null)
        .addScope(Plus.SCOPE_PLUS_LOGIN)
        .build();

		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_name: 
			String user = editUser.getText().toString(); 
			
			if (!user.isEmpty()) {
				
				textInvalidUser.setVisibility(View.GONE);
				
				if(null != EscapeIRApplication.preferencesUser) {
					Log.i(EscapeIRApplication.TAG,"save user name");
					Preferences.saveUsername(editUser.getText().toString());
				}
				
				editUser.setText("");
				Log.i(EscapeIRApplication.TAG,"go Main class");
				Intent mainIntent = new Intent(this, Main.class);
				startActivity(mainIntent);
				finish();
			// User Not valid
			} else {
				textInvalidUser.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.sign_in_button: break;
		}
	}

	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	@Override
	protected void onStop() {
		super.onStop();

		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionSuspended(int cause) {
		mGoogleApiClient.connect();

	}

}
