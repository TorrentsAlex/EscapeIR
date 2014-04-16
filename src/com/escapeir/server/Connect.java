package com.escapeir.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.escapeir.application.EscapeIRApplication;
import com.escapeir.userclass.User;

import android.util.Log;

public class Connect {
	
	public void getUser() {
		Thread sqlThread = new Thread() {
			public void run() {
				String result = "";
				InputStream is = null;
				// Execute getUser.php from escapeir.uphero.com
				try {
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(
							"http://escapeir.uphero.com/getUser.php");
					HttpResponse response = httpclient.execute(httppost);
					HttpEntity entity = response.getEntity();
					is = entity.getContent();
					Log.i("Connect", "Connect");
				} catch (Exception e) {
					Log.e(EscapeIRApplication.TAG, "Error in http connection " + e.toString());
				}
				// convert response to string
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();

					result = sb.toString();

					Log.i("connect", "resultado");
					Log.i("connect", result);
				} catch (Exception e) {
					Log.e(EscapeIRApplication.TAG, "Error converting result " + e.toString());
				}
				// parse json data
				try {
					JSONArray jArray = new JSONArray(result);
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						EscapeIRApplication.usersServer.add(new User(json_data.getString("user"), json_data.getInt("time")));
						Log.i(EscapeIRApplication.TAG,
								"time: " + json_data.getInt("time") + ", name: "
										+ json_data.getString("user"));
					}
				} catch (JSONException e) {
					Log.e("log_tag", "Error parsing data " + e.toString());
				}

			}
		};
		sqlThread.start();
	}
}
