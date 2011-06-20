package com.matji.sandwich.adapter;

import com.matji.sandwich.data.Tag;

import android.content.Context;

public class TagAdapter extends DefaultAdapter {

	public TagAdapter(Context context) {
		super(context);
	}

	@Override
	protected String getText(int position) {
		Tag tag = (Tag) data.get(position);
		return tag.getTag();
	}
}