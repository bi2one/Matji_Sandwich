package com.matji.sandwich.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.matji.sandwich.R;
import com.matji.sandwich.adapter.WritePostStoreAdapter.StoreElement;
import com.matji.sandwich.data.Store;

public class WritePostStoreView extends RelativeLayout implements AdapterView.OnItemClickListener,
View.OnClickListener {
	private WritePostStoreListView listView;
	private TextView selectText;
	private Store selectedStore;
	private StoreSelectListener selectListener;
	private OnClickListener clickListener;
	private ImageButton searchLocationButton;
	private ImageButton writeStoreButton;

	public WritePostStoreView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.write_post_store_view, this, true);

		listView = (WritePostStoreListView)findViewById(R.id.write_post_store_view_listview);
		listView.setOnItemClickListener(this);

		selectText = (TextView)findViewById(R.id.write_post_store_view_selected);
		searchLocationButton = (ImageButton)findViewById(R.id.write_post_store_view_search);
		writeStoreButton = (ImageButton)findViewById(R.id.write_post_store_view_add);
		searchLocationButton.setOnClickListener(this);
		writeStoreButton.setOnClickListener(this);
	}

	public void init(Activity activity) {
		listView.init(activity);
	}

	public void refresh(GeoPoint neBound, GeoPoint swBound) {
		listView.setBound(neBound, swBound);
		listView.requestReload();
	}

	public void setStoreSelectListener(StoreSelectListener selectListener) {
		this.selectListener = selectListener;
	}

	public void setOnClickListener(OnClickListener clickListener) {
		this.clickListener = clickListener;
	}

	public Store getSelectedStore() {
		return selectedStore;
	}

	public void onItemClick(AdapterView parent, View view, int position, long id) {
		StoreElement element = (StoreElement)view.getTag();
		Store store = element.getStore();

		selectText.setText(store.getName());
		selectedStore = store;

		if (selectListener != null)
			selectListener.onStoreSelect(store);
	}

	public void onClick(View v) {
		if (clickListener == null)
			return ;
		int viewId = v.getId();
		if (viewId == searchLocationButton.getId())
			clickListener.onSearchLocationClick(v);
		else if (viewId == writeStoreButton.getId())
			clickListener.onWriteStoreClick(v);
	}

	public interface StoreSelectListener {
		public void onStoreSelect(Store store);
	}

	public interface OnClickListener {
		public void onSearchLocationClick(View v);
		public void onWriteStoreClick(View v);
	}
}