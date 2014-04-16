package com.escapeir.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.catchoom.CatchoomCloudRecognitionItem;
import com.escapeir.application.EscapeIRApplication;
import com.escapeir.server.Connect;
import com.escapeir.userclass.User;
import com.escapeir.util.ItemAdapter;
import com.example.escapeir.R;

public class Results extends Activity {
	private TextView userTime;
	private Connect connect = new Connect();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_layout);
		
		userTime = (TextView) findViewById(R.id.timeUser);
		ListView viewList = (ListView) findViewById(R.id.listView1);
		
		userTime.setText(getIntent().getExtras().getString("time"));
		viewList.setAdapter(new ItemAdapter(this,
				R.layout.result_list, EscapeIRApplication.usersServer));
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EscapeIRApplication.usersServer.clear();
		finish();
	}
	
}
