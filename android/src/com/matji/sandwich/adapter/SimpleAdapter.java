package com.matji.sandwich.adapter;

import com.matji.sandwich.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class SimpleAdapter extends MBaseAdapter {
	protected abstract String getText(int position);
	
	public SimpleAdapter(Context context) {
		super(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		DefaultElement defaultElement;
		if (convertView == null) {
			defaultElement = new DefaultElement();
			convertView = getLayoutInflater().inflate(R.layout.row_simple, null);

			defaultElement.text = (TextView) convertView.findViewById(R.id.row_simple_text);
			convertView.setTag(defaultElement);
		} else {
			defaultElement = (DefaultElement) convertView.getTag();
		}
		
		defaultElement.text.setText(getText(position));
		return convertView;
	}

	private class DefaultElement {
		TextView text;
	}
}