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
		EscapeIRApplication.USER_TIME = null;
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
