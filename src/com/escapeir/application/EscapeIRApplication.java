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
package com.escapeir.application;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.escapeir.classes.User;

public class EscapeIRApplication extends Application {
	
	public static final String TAG = "EscapeIR";
	public static final String escapeToken="dd46676fc6b445ea";
	
	public static boolean initialized=false;
	public static boolean haveResult=false;
	
	public static int PLAY_MODE;
	public static final int MODE_KITCHEN = 1;
	public static final int MODE_BEDROOM = 2;
	public static final int MODE_CLASSROOM = 3;
	
	public static String[] CHOOSEN_ROOM;
	public static String[] GUIDE_ROOM;
	public static String[] REWARD_ROOM;
	public static String INTRO_ROOM;
	
	public static String preferencesEscape="EscapeIRPreferences";
	
	public int preferencesMode;
	public static String preferencesModeKey="EscapeIRPlayMode";
	public static int preferencesModeDefault=3;
	
	public static String preferencesUserKey="EscapeIRUser";
	public static String preferencesUserDefault="escapeIR";
	
	public static String preferencesEditorUser="Username";
	public static SharedPreferences preferencesUser=null;

	public static String USER_NAME=null;
	public static String USER_TIME=null;
	
	public static ArrayList<User> usersServer = new ArrayList();
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		EscapeIRApplication.preferencesUser = getSharedPreferences(
				EscapeIRApplication.preferencesEditorUser, Context.MODE_PRIVATE);
	}
}
