package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.SearchToken;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleAdapter extends MBaseAdapter {
	
	public SimpleAdapter(Context context) {
		super(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		DefaultElement defaultElement;
		SearchToken store = (SearchToken) data.get(position); 
		if (convertView == null) {
			defaultElement = new DefaultElement();
			convertView = getLayoutInflater().inflate(R.layout.row_simple, null);
			
			defaultElement.text = (TextView) convertView.findViewById(R.id.row_simple_text);
			convertView.setTag(defaultElement);
		} else {
			defaultElement = (DefaultElement) convertView.getTag();
		}
		
		defaultElement.text.setText(store.getSeed());

		return convertView;
	}
	
	public class DefaultElement {
		TextView text;
	}
}