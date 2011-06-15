package com.matji.sandwich.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public abstract class MBaseAdapter extends BaseAdapter {
	protected LayoutInflater inflater;
	protected Context context;
	protected ArrayList<?> data;

	public MBaseAdapter(Context context, ArrayList<?> data) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.data = data;
	}

	public MBaseAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		this.context = context;
	}

	protected LayoutInflater getLayoutInflater() {
		return inflater;
	}

	public void setData(ArrayList<?> data) {
		this.data = data;
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
}
