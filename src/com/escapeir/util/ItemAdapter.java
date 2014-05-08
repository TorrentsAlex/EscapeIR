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

import com.escapeir.userclass.User;
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
