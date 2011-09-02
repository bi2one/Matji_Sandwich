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
import com.matji.sandwich.adapter.SimpleStoreAdapter;
import com.matji.sandwich.adapter.SimpleStoreAdapter.StoreElement;
import com.matji.sandwich.data.Store;

//public class SelectStoreContents extends RelativeLayout implements AdapterView.OnItemClickListener,
public class SelectStoreContents extends RelativeLayout implements View.OnClickListener,
								   SelectStoreListView.OnItemClickListener {
    private Context context;
    private SelectStoreListView listView;
    private TextView selectText;
    private Store selectedStore;
    private StoreSelectListener selectListener;
    private OnClickListener clickListener;
    private ImageButton searchLocationButton;
    private ImageButton writeStoreButton;

    public SelectStoreContents(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.select_store_contents, this, true);
	
        listView = (SelectStoreListView)findViewById(R.id.select_store_contents_listview);
	listView.setOnItemClickListener(this);

        selectText = (TextView)findViewById(R.id.select_store_contents_selected);
        searchLocationButton = (ImageButton)findViewById(R.id.select_store_contents_search);
        writeStoreButton = (ImageButton)findViewById(R.id.select_store_contents_add);
        searchLocationButton.setOnClickListener(this);
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

    public void setStoreSelectListener(StoreSelectListener selectListener) {
        this.selectListener = selectListener;
    }

    public void setOnClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public Store getStore() {
        return selectedStore;
    }

    public void onItemClick(View v) {
       StoreElement element = (StoreElement)v.getTag();
       Store store = element.getStore();

       setStore(store);
       if (selectListener != null)
           selectListener.onStoreSelect(store);
    }

    public void onClick(View v) {
        if (clickListener == null)
            return ;

        int viewId = v.getId();
        if (viewId == searchLocationButton.getId()) {
            clickListener.onSearchLocationClick(v);
        } else if (viewId == writeStoreButton.getId()) {
            clickListener.onWriteStoreClick(v);
        }
    }

    public interface StoreSelectListener {
        public void onStoreSelect(Store store);
    }

    public interface OnClickListener {
        public void onSearchLocationClick(View v);
        public void onWriteStoreClick(View v);
    }
}