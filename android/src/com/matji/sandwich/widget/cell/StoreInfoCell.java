package com.matji.sandwich.widget.cell;

import com.matji.sandwich.R;
import com.matji.sandwich.SharedMatjiData;
import com.matji.sandwich.data.Store;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StoreInfoCell extends RelativeLayout {
	
	public StoreInfoCell(Context context) {
		super(context);
		init();
	}
	
	public StoreInfoCell(Context context, AttributeSet attr) {
		super(context, attr);
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		inflater.inflate(R.layout.cell_store_info, this);
		
		Store store = (Store) SharedMatjiData.getInstance().top();
		
		((TextView) findViewById(R.id.cell_store_tel)).setText(store.getTel());
		((TextView) findViewById(R.id.cell_store_address)).setText(store.getAddress());

	}
	

}
