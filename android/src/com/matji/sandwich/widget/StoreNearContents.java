package com.matji.sandwich.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.matji.sandwich.R;
import com.matji.sandwich.data.Store;

public class StoreNearContents extends RelativeLayout implements View.OnClickListener {
	private Store selectedStore;
	private Context context;
	private TextView selectText;
	private SelectStoreListView listView;
	private OnClickListener clickListener;
	private ImageButton writeStoreButton;

	public StoreNearContents(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.store_near_contents, this, true);

		listView = (SelectStoreListView)findViewById(R.id.store_near_contents_listview);

		selectText = (TextView)findViewById(R.id.store_near_contents_selected);
		selectText.setText(R.string.find_near_store_view_select);
		
		writeStoreButton = (ImageButton)findViewById(R.id.store_near_contents_add);
		writeStoreButton.setOnClickListener(this);
	}

	public void setStore(Store store) {
		if (store == null) {
			selectText.setText(R.string.write_post_store_view_select);
			selectText.setTextColor(context.getResources().getColor(R.color.map_title_bar_hint));
		} else {
			selectText.setText(store.getName());
			selectText.setTextColor(context.getResources().getColor(R.color.map_title_bar_address));
		}
		selectedStore = store;
	}

	public void init(Activity activity) {
		listView.init(activity);
	}

	public void refresh(GeoPoint neBound, GeoPoint swBound) {
		listView.setBound(neBound, swBound);
		listView.requestReload();
	}

	public void refresh(int neLat, int neLng, int swLat, int swLng) {
		refresh(new GeoPoint(neLat, neLng), new GeoPoint(swLat, swLng));
	}

	public void setOnClickListener(OnClickListener clickListener) {
		this.clickListener = clickListener;
	}

	public Store getStore() {
		return selectedStore;
	}
	public void onClick(View v) {
		if (clickListener == null)
			return ;
		int viewId = v.getId();
		if (viewId == writeStoreButton.getId())
			clickListener.onWriteStoreClick(v);
	}

	public interface OnClickListener {
		public void onWriteStoreClick(View v);
	}
}