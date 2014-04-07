package com.escapeir.preferences;

import android.content.SharedPreferences.Editor;

import com.escapeir.application.EscapeIRApplication;

public class Preferences {
	
	public static void saveUsername(String name) {
		EscapeIRApplication.USER_NAME = name;
		
		Editor editor = EscapeIRApplication.preferencesUser.edit();
		editor.putString(EscapeIRApplication.preferencesUserKey, name);
		editor.commit();
	}
	
	public static String setUsername() {
		EscapeIRApplication.USER_NAME = EscapeIRApplication.preferencesUser.getString(
				EscapeIRApplication.preferencesUserKey,
				EscapeIRApplication.preferencesUserDefault);
		return EscapeIRApplication.USER_NAME;
	}
}
