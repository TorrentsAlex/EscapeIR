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
package com.escapeir.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.escapeir.activity.Results;
import com.escapeir.application.EscapeIRApplication;
import com.escapeir.userclass.User;

public class Connect extends AsyncTask<Boolean, String, Boolean> {
	private ProgressDialog dialog;
	private Activity activity;
	private Context context;
	private Boolean setOrGet;
	
	public Connect(Context context,Activity activity) {
		this.context = context;
		this.activity = activity;
		dialog = new ProgressDialog(activity);
	}

	@Override
	protected void onPreExecute() {
		dialog.setMessage("Cargando datos, porfavor espere");
		dialog.show();
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		if (!setOrGet){
			Intent intent = new Intent(activity, Results.class);
			context.startActivity(intent); 
		}
	}
	@Override
	protected Boolean doInBackground(Boolean... params) {
		HttpPost httppost=null;
		// setOrGet = true is set user
		// setOrGet = false is Get user
		setOrGet = params[0];
		String urlExecute = "", urlSet = "http://escapeir.uphero.com/setUser.php", urlGet = "http://escapeir.uphero.com/getUser.php";
		String result = "";
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			if (setOrGet) {
				httppost = new HttpPost(urlSet);

				final ArrayList<NameValuePair> insertUser = new ArrayList<NameValuePair>();
				insertUser.add(new BasicNameValuePair("user", EscapeIRApplication.USER_NAME));
				insertUser.add(new BasicNameValuePair("time", EscapeIRApplication.USER_TIME));
				httppost.setEntity(new UrlEncodedFormEntity(insertUser));

			} else {				
				httppost = new HttpPost(urlGet);

			}
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Log.i("Connect", "Connect");
		} catch (Exception e) {
			Log.e(EscapeIRApplication.TAG,
					"Error in http connection " + e.toString());
		}
		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();

			Log.i(EscapeIRApplication.TAG, "resultado");
			Log.i(EscapeIRApplication.TAG, result);
		} catch (Exception e) {
			Log.e(EscapeIRApplication.TAG,
					"Error converting result " + e.toString());
		}
		// parse json data
		if (setOrGet) {
			// return if is set user
			return true;
		} else {
			try {
				JSONArray jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					EscapeIRApplication.usersServer.add(new User(json_data
							.getString("user"), json_data.getString("time")));
					Log.i(EscapeIRApplication.TAG,
							"time: " + json_data.getString("time") + ", name: "
									+ json_data.getString("user"));
				}
			} catch (JSONException e) {
				Log.e(EscapeIRApplication.TAG,
						"Error parsing data " + e.toString());
			}
		}
		return true;
	}
}
