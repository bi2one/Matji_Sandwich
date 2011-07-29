package com.matji.sandwich.widget.cell;

import com.matji.sandwich.R;
import com.matji.sandwich.SharedMatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.session.Session;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StoreCell extends RelativeLayout {

	public StoreCell(Context context) {
		super(context);
		init();
	}
	
	public StoreCell(Context context, AttributeSet attr) {
		super(context, attr);
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		inflater.inflate(R.layout.cell_store_name, this);
		
		Store store = (Store) SharedMatjiData.getInstance().top();
		
		((TextView) findViewById(R.id.cell_store_name)).setText(store.getName());
		
	}

}
