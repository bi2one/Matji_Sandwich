package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toast;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.FoodAdapter;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.StoreFood;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreFoodHttpRequest;

public class StoreMenuListView extends RequestableMListView {
	private Context context;
	private HttpRequest request;
	private int store_id;

	public StoreMenuListView(Context context, AttributeSet attrs) {
		super(context, attrs, new FoodAdapter(context), 10);
		this.context = context;

		request = new StoreFoodHttpRequest(context);
		setPage(1);
	}

	public void addMenu(StoreFood food) {
		for (MatjiData data : getAdapterData()) {
			if (food.getFood().getName().equals(((StoreFood) data).getFood().getName())) {
				Toast.makeText(context, R.string.store_menu_already_exist_menu, Toast.LENGTH_SHORT).show();
				return;
			}
		}
		
		getAdapterData().add(0, food);

		if (getAdapterData().size() > getLimit() * (getPage() - 1)) {
			requestSetOn();
			getAdapterData().remove(getAdapterData().size() - 1);
		}
		getMBaseAdapter().notifyDataSetChanged();		
	}

	public void setUserId(int store_id) {
		this.store_id = store_id;
	}

	public HttpRequest request() {
		((StoreFoodHttpRequest) request).actionList(store_id, getPage(), getLimit());

		return request;
	}

	public void onListItemClick(int position) {}
}