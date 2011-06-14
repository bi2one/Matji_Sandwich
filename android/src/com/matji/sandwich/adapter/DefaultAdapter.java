package com.matji.sandwich.adapter;

import com.matji.sandwich.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class DefaultAdapter extends MBaseAdapter {
	protected abstract String getText(int position);
	
	public DefaultAdapter(Context context) {
		super(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		DefaultElement defaultElement;
		if (convertView == null) {
			defaultElement = new DefaultElement();
			convertView = getLayoutInflater().inflate(R.layout.adapter_default, null);

			defaultElement.text = (TextView) convertView.findViewById(R.id.default_adapter_text);
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