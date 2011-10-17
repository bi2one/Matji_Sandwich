package com.matji.sandwich.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;

public abstract class SimpleBoxAdapter extends MBaseAdapter {
	protected abstract String getText(int position);
	
	public SimpleBoxAdapter(Context context) {
		super(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		DefaultElement defaultElement;
		if (convertView == null) {
			defaultElement = new DefaultElement();
			convertView = getLayoutInflater().inflate(R.layout.row_simple_box, null);
			defaultElement.wrapper = convertView.findViewById(R.id.row_simple_box_wrapper);
			defaultElement.text = (TextView) convertView.findViewById(R.id.row_simple_box_text);
			defaultElement.line = convertView.findViewById(R.id.row_simple_box_horizontal_line);
			
			convertView.setTag(defaultElement);
		} else {
			defaultElement = (DefaultElement) convertView.getTag();
		}
		
		defaultElement.line.setVisibility(View.VISIBLE);
		int margin = MatjiConstants.dimenInt(R.dimen.default_distance);
        RelativeLayout.LayoutParams params;

        if (data.size() == 1) {
            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(margin, margin, margin, margin);
            defaultElement.wrapper.setLayoutParams(params);
            defaultElement.line.setVisibility(View.GONE);
            defaultElement.wrapper.setBackgroundResource(R.drawable.txtbox_selector);
		} else if (position == 0) {
            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(margin, margin, margin, 0);
            defaultElement.wrapper.setLayoutParams(params);
		    defaultElement.wrapper.setBackgroundResource(R.drawable.txtbox_t_selector);
		} else if (position == data.size()-1) {
            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(margin, 0, margin, margin);
            defaultElement.wrapper.setLayoutParams(params);
		    defaultElement.line.setVisibility(View.GONE);
		    defaultElement.wrapper.setBackgroundResource(R.drawable.txtbox_u_selector);
		} else {
            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(margin, 0, margin, 0);
            defaultElement.wrapper.setLayoutParams(params);
		    defaultElement.wrapper.setBackgroundResource(R.drawable.txtbox_c_selector);
		}
		
		defaultElement.text.setText(getText(position));
		return convertView;
	}

	private class DefaultElement {
	    View wrapper;
		TextView text;
		View line;
	}
}