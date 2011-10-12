package com.matji.sandwich.adapter;

import com.matji.sandwich.data.Url;

import android.content.Context;
import android.view.Gravity;

public class UrlAdapter extends SimpleAdapter {

	public UrlAdapter(Context context) {
		super(context);
		alignText(Gravity.CENTER_VERTICAL);
	}

	@Override
	protected String getText(int position) {
		// TODO Auto-generated method stub

		Url url = (Url) data.get(position);
		return url.getUrl();
	}
}