package com.aishindus.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
	private Context context;
	private final String[] menuValues;
 
	public ImageAdapter(Context context, String[] menuValues) {
		this.context = context;
		this.menuValues = menuValues;
	}
 
	public View getView(int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View gridView;
 
		if (convertView == null) {
			gridView = new View(context);
			// get layout from menu_grid_view.xmld_view.xml
			gridView = inflater.inflate(R.layout.menu_grid_view, null);
			// set value into textview
			TextView textView = (TextView) gridView.findViewById(R.id.textView);
			textView.setText(menuValues[position]);
 			// set image based on selected text
			ImageView imageView = (ImageView) gridView.findViewById(R.id.imageView);
			String mobile = menuValues[position];

			if (mobile.equals("Daily Report")) {
				imageView.setImageResource(R.drawable.daily_report);
			} else if (mobile.equals("Search")) {
				imageView.setImageResource(R.drawable.search_by_style);
			} else if (mobile.equals("Search By Date")) {
				imageView.setImageResource(R.drawable.search_by_date);
			} else {
				imageView.setImageResource(R.drawable.about_us);
			}
		} else {
			gridView = (View) convertView;
		}
		return gridView;
	}
 
	@Override
	public int getCount() {
		return menuValues.length;
	}
 
	@Override
	public Object getItem(int position) {
		return null;
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}
 
}