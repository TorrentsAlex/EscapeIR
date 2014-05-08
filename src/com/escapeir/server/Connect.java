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
