package com.matji.sandwich.adapter;

import com.matji.sandwich.data.StoreFood;

import android.content.Context;

public class FoodAdapter extends DefaultAdapter {

	public FoodAdapter(Context context) {
		super(context);
	}

	@Override
	protected String getText(int position) {
		// TODO Auto-generated method stub

		StoreFood storeFood = (StoreFood) data.get(position);
		return storeFood.getFood().getName();
	}
}