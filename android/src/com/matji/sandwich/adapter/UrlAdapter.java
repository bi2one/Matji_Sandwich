package com.matji.sandwich.adapter;

import android.content.Context;

import com.matji.sandwich.data.Url;

public class UrlAdapter extends SimpleBoxAdapter {

	public UrlAdapter(Context context) {
		super(context);
	}

	@Override
	protected String getText(int position) {
		Url url = (Url) data.get(position);
		return url.getUrl();
	}
}