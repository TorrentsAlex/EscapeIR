package com.escapeir.activity;

import com.example.escapeir.R;

import android.app.Activity;
import android.os.Bundle;

public class HowToPlay extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.how_to_play);

	}

	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}

}
