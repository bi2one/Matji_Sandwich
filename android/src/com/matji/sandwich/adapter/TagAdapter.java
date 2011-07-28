package com.matji.sandwich.adapter;

import com.matji.sandwich.data.SimpleTag;

import android.content.Context;

public class TagAdapter extends DefaultAdapter {

	public TagAdapter(Context context) {
		super(context);
	}

	@Override
	protected String getText(int position) {
		SimpleTag tag = (SimpleTag) data.get(position);
		return tag.getTag();
	}
}