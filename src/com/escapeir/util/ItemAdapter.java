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
package com.escapeir.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.escapeir.classes.User;
import com.example.escapeir.R;

public class ItemAdapter extends ArrayAdapter<User> {
	private Context context;
	private ArrayList<User> listItems;

	public ItemAdapter(Context context, int resource, List<User> objects) {
		super(context, resource, objects);
		this.context = context;
		this.listItems = (ArrayList<User>) objects;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.result_list, null);
		}
		TextView item_user = (TextView) convertView
				.findViewById(R.id.text_list_user);
		TextView item_time = (TextView) convertView
				.findViewById(R.id.text_list_time);
		
		// Set the name and time for top 5
		item_user.setText(listItems.get(position).getName());
		item_time.setText(""+listItems.get(position).getTime());
		
		// set colors for the best results 
		LinearLayout listLayout = (LinearLayout) convertView.findViewById(R.id.layout_list);
		TextView textPosition = (TextView) convertView.findViewById(R.id.text_list_position);
		if (position == 0){
			listLayout.setBackgroundResource(R.color.gold);
			textPosition.setText(R.string.primero);
			textPosition.setVisibility(View.VISIBLE);
			
		}else if (position == 1) {
			listLayout.setBackgroundResource(R.color.silver);
			textPosition.setText(R.string.segundo);
			textPosition.setVisibility(View.VISIBLE);
		
		}else if (position == 2) {
			listLayout.setBackgroundResource(R.color.bronze);
			textPosition.setText(R.string.tercero);
			textPosition.setVisibility(View.VISIBLE);
		}

		return convertView;
	}

}
