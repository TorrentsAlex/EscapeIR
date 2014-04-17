package com.escapeir.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

import android.util.Log;

import com.escapeir.application.EscapeIRApplication;
import com.escapeir.userclass.User;

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
					Log.e(EscapeIRApplication.TAG, "Error in http connection "
							+ e.toString());
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

					Log.i(EscapeIRApplication.TAG, "resultado");
					Log.i(EscapeIRApplication.TAG, result);
				} catch (Exception e) {
					Log.e(EscapeIRApplication.TAG, "Error converting result "
							+ e.toString());
				}
				// parse json data
				try {
					JSONArray jArray = new JSONArray(result);
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						EscapeIRApplication.usersServer.add(new User(json_data
								.getString("user"), json_data.getString("time")));
						Log.i(EscapeIRApplication.TAG,
								"time: " + json_data.getString("time")
										+ ", name: "
										+ json_data.getString("user"));
					}
				} catch (JSONException e) {
					Log.e(EscapeIRApplication.TAG, "Error parsing data " + e.toString());
				}

			}
		};
		sqlThread.start();
	}

	public void setUser(String user, String time) {
		
		final ArrayList<NameValuePair> insertUser = new ArrayList<NameValuePair>();
		insertUser.add(new BasicNameValuePair("user",user));
		insertUser.add(new BasicNameValuePair("time",time));
		
		
		Thread sqlThread = new Thread() {
			public void run() {
				String result = "";
				InputStream is = null;
				// Execute getUser.php from escapeir.uphero.com
				try {
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(
							"http://escapeir.uphero.com/setUser.php");
			        httppost.setEntity(new UrlEncodedFormEntity(insertUser));
			        HttpResponse response = httpclient.execute(httppost);
					HttpEntity entity = response.getEntity();
					is = entity.getContent();
					Log.i(EscapeIRApplication.TAG, "Insert");
				} catch (Exception e) {
					Log.e(EscapeIRApplication.TAG, "Error in http connection "
							+ e.toString());
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

					Log.i(EscapeIRApplication.TAG, "resultado");
					Log.i(EscapeIRApplication.TAG, result);
				} catch (Exception e) {
					Log.e(EscapeIRApplication.TAG, "Error converting result "
							+ e.toString());
				}
			}
		};
		sqlThread.start();
	}
}
