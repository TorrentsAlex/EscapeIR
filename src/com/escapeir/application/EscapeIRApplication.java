package com.escapeir.application;

import android.app.Application;

public class EscapeIRApplication extends Application {
	
	public static final String TAG = "EscapeIR";
	public static final String escapeToken="dd46676fc6b445ea";
	
	public static int PLAY_MODE;
	public static final int MODE_KITCHEN = 1;
	public static final int MODE_BEDROOM = 2;
	public static final int MODE_CLASSROOM = 3;
	
	public static int COUNT_REFERENCE=0;
	public static String[] REFERENCE_ROOM;
	
	public int preferencesMode;
	public static String preferencesModeKey="EscapeIRPlayMode";
	public static int preferencesModeDefault=3;
	@Override
	public void onCreate() {
		super.onCreate();

	}
}
