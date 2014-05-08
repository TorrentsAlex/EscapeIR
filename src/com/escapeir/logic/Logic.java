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
