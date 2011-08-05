package com.matji.sandwich.widget;

import android.view.View;
import android.view.LayoutInflater;
import android.content.Context;

public class ViewContainer {
	private View rootView;
	private LayoutInflater inflater;

	public ViewContainer(Context context, int id) {
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rootView = getViewById(id);
	}

	protected View getViewById(int id) {
		return inflater.inflate(id, null);
	}

	public void setRootView(View v) {
		rootView = v;
	}

	public View getRootView() {
		return rootView;
	}
}