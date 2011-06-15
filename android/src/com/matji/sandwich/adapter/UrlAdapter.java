package com.matji.sandwich.adapter;

import com.matji.sandwich.data.Url;

import android.content.Context;

public class UrlAdapter extends DefaultAdapter {

	public UrlAdapter(Context context) {
		super(context);
	}

	@Override
	protected String getText(int position) {
		// TODO Auto-generated method stub

		Url url = (Url) data.get(position);
		return url.getUrl();
	}
}