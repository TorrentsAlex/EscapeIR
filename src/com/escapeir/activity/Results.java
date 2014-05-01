package com.escapeir.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.escapeir.application.EscapeIRApplication;
import com.escapeir.util.ItemAdapter;
import com.example.escapeir.R;

public class Results extends Activity implements OnClickListener {
	private TextView userTime;
	private TextView txtYourTime;
	private Button btnReturn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_layout);

		txtYourTime = (TextView) findViewById(R.id.txt_your_time);
		userTime = (TextView) findViewById(R.id.time_user);
		btnReturn = (Button) findViewById(R.id.btn_return);
		ListView viewList = (ListView) findViewById(R.id.listView1);
		
		btnReturn.setOnClickListener(this);
		
		if (EscapeIRApplication.USER_TIME == null) {
			txtYourTime.setVisibility(View.GONE);
			userTime.setVisibility(View.GONE);
		} else {
			userTime.setText(EscapeIRApplication.USER_TIME);
		}

		viewList.setAdapter(new ItemAdapter(this, R.layout.result_list,
				EscapeIRApplication.usersServer));

	}

	@Override
	public void onBackPressed() {
		txtYourTime.setVisibility(View.VISIBLE);
		userTime.setVisibility(View.VISIBLE);
		EscapeIRApplication.usersServer.clear();
		Intent intent = new Intent(this, Main.class);
		startActivity(intent);
		finish();
		
		super.onBackPressed();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_return:
			onBackPressed();
			break;
		}

	}

}
