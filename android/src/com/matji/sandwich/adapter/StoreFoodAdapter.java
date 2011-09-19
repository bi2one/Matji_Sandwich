package com.matji.sandwich.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.data.StoreFood;

public class StoreFoodAdapter extends MBaseAdapter {

    public static final String ADD_STORE_FOOD = "StoreFoodAdapter.add_store_food";

    public StoreFoodAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        FoodElement foodElement;

        StoreFood storeFood = (StoreFood) data.get(position);

        if (convertView == null) {
            foodElement = new FoodElement();
            convertView = getLayoutInflater().inflate(R.layout.row_food, null);
            foodElement.name = (TextView) convertView.findViewById(R.id.row_food_name);
            foodElement.count = (TextView) convertView.findViewById(R.id.row_food_like_count);
            foodElement.line = convertView.findViewById(R.id.row_food_horizontal_line);
            convertView.setTag(foodElement);
        } else {
            foodElement = (FoodElement) convertView.getTag();
        }

        String name = storeFood.getFood().getName();
        if (name.equals(ADD_STORE_FOOD)) {
            foodElement.name.setText(R.string.store_food_add);
            foodElement.count.setVisibility(View.GONE);
            foodElement.line.setVisibility(View.GONE);
            // food add listener
        } else {
            foodElement.name.setText(storeFood.getFood().getName());
            foodElement.count.setText(storeFood.getLikeCount()+"");
            foodElement.line.setVisibility(View.VISIBLE);
            // food like listener            
        }

        return convertView;
    }

    private class FoodElement {
        TextView name;
        TextView count;
        View line;
    }
}