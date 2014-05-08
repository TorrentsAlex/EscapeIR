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
package com.escapeir.logic;

import android.content.Context;
import android.util.Log;

import com.escapeir.application.EscapeIRApplication;
import com.example.escapeir.R;

public class Logic  {
	
	public static void chargeArray(int room, Context context) {
		switch(room) {
		case EscapeIRApplication.MODE_CLASSROOM:
			EscapeIRApplication.CHOOSEN_ROOM = context.getResources().getStringArray(R.array.reference_classroom);
			EscapeIRApplication.GUIDE_ROOM = context.getResources().getStringArray(R.array.guide_classroom);
			EscapeIRApplication.REWARD_ROOM = context.getResources().getStringArray(R.array.reward_classroom);
			EscapeIRApplication.INTRO_ROOM = context.getResources().getString(R.string.intro_body_classroom);
			
			for(byte i=0;i<EscapeIRApplication.CHOOSEN_ROOM.length;i++) {
			
				Log.i(EscapeIRApplication.TAG, EscapeIRApplication.CHOOSEN_ROOM[i] + i);
			}
			break;
		case EscapeIRApplication.MODE_KITCHEN:
			EscapeIRApplication.CHOOSEN_ROOM = context.getResources().getStringArray(R.array.reference_prueba);
			break;
		}
	}

}
