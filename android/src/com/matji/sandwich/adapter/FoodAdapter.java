package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Food;
import com.matji.sandwich.data.StoreFood;
import com.matji.sandwich.widget.StoreMenuListView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FoodAdapter extends MBaseAdapter {
	public FoodAdapter(Context context) {
		super(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		FoodElement foodElement;
		StoreFood storeFood = (StoreFood) data.get(position);
		Food food = storeFood.getFood();

		if (convertView == null) {
			foodElement = new FoodElement();
			convertView = getLayoutInflater().inflate(R.layout.adapter_menu, null);

			foodElement.name = (TextView) convertView.findViewById(R.id.menu_adapter_name);
			foodElement.likeCount = (TextView) convertView.findViewById(R.id.menu_adapter_like_cnt);
			foodElement.like = (Button) convertView.findViewById(R.id.menu_adapter_like_btn);
			
			convertView.setTag(foodElement);

			StoreMenuListView storeMenuListView = (StoreMenuListView) parent;
			foodElement.like.setOnClickListener(storeMenuListView);
		} else {
			foodElement = (FoodElement) convertView.getTag();
		}

		foodElement.like.setTag(position+"");
		
		foodElement.name.setText(food.getName());
		if (storeFood.getLikeCount() > 0) {
			foodElement.likeCount.setVisibility(View.VISIBLE);
			foodElement.likeCount.setText(storeFood.getLikeCount() + "person");
		} else foodElement.likeCount.setVisibility(View.GONE);

		return convertView;
	}

	private class FoodElement {
		TextView name;
		TextView likeCount;
		Button like;
	}
}