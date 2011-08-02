package com.matji.sandwich.widget.cell;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Store;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StoreInfoCell extends RelativeLayout {
	private Store store;
	
	public StoreInfoCell(Context context) {
		super(context);
		init();
	}
	
	public StoreInfoCell(Context context, AttributeSet attr) {
		super(context, attr);
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.cell_store_info, this);
	}

	protected void init() {
		((TextView) findViewById(R.id.cell_store_tel)).setText(store.getTel());
		((TextView) findViewById(R.id.cell_store_address)).setText(store.getAddress());
//		((TextView) findViewById(R.id.cell_store_tag)).setText(tagListToCSV(store.getTags()));
//		((TextView) findViewById(R.id.cell_store_food)).setText(foodListToCSV(store.getStoreFoods()));
	}
	
	public void setStore(Store store) {
		this.store = store;
		init();
	}	
}
