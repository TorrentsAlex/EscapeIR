package com.escapeir.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class EscapeIRApplication extends Application {
	
	public static final String TAG = "EscapeIR";
	public static final String escapeToken="dd46676fc6b445ea";
	
	public static int PLAY_MODE;
	public static final int MODE_KITCHEN = 1;
	public static final int MODE_BEDROOM = 2;
	public static final int MODE_CLASSROOM = 3;
	
	public static int COUNT_REFERENCE=0;
	public static String[] REFERENCE_ROOM;
	
	public static String preferencesEscape="EscapeIRPreferences";
	
	public int preferencesMode;
	public static String preferencesModeKey="EscapeIRPlayMode";
	public static int preferencesModeDefault=3;
	
	public static String preferencesUserKey="EscapeIRUser";
	public static String preferencesUserDefault="escapeIR";
	
	public static String preferencesEditorUser="Username";
	public static SharedPreferences preferencesUser=null;

	public static String USER_NAME=null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		EscapeIRApplication.preferencesUser = getSharedPreferences(
				EscapeIRApplication.preferencesEditorUser, Context.MODE_PRIVATE);
	}
}
