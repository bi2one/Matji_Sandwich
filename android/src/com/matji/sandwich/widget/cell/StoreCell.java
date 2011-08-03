package com.matji.sandwich.widget.cell;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Store;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;

public class StoreCell extends RelativeLayout {
	private Store store;

	public StoreCell(Context context) {
		super(context);
		init();
	}
	
	public StoreCell(Context context, AttributeSet attr) {
		super(context, attr);
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.cell_store_name, this);
	}

	private void init() {
		((TextView) findViewById(R.id.cell_store_name)).setText(store.getName());
		((Button) findViewById(R.id.cell_store_like_btn)).setText("1");
	}
	
	public void setStore(Store store) {
		this.store = store;
		init();
	}
}
