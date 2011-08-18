package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Store;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WritePostStoreAdapter extends MBaseAdapter {
    public WritePostStoreAdapter(Context context) {
        super(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        StoreElement storeElement;
        Store store = (Store) data.get(position);

        if (convertView == null) {
            storeElement = new StoreElement();
            convertView = getLayoutInflater().inflate(R.layout.row_write_post_store, null);

            storeElement.name = (TextView) convertView.findViewById(R.id.row_write_post_store_name);
            storeElement.likeCount = (TextView) convertView.findViewById(R.id.row_write_post_store_like_count);
            storeElement.postCount = (TextView) convertView.findViewById(R.id.row_write_post_store_post_count);
	    
            convertView.setTag(storeElement);
        } else {
            storeElement = (StoreElement) convertView.getTag();
        }

        storeElement.name.setText(store.getName());
	storeElement.likeCount.setText("" + store.getLikeCount());
	storeElement.postCount.setText("" + store.getPostCount());
	storeElement.setStore(store);

        return convertView;
    }

    public static class StoreElement {
	TextView name;
	TextView likeCount;
	TextView postCount;
	private Store store;

	public void setStore(Store store) {
	    this.store = store;
	}

	public Store getStore() {
	    return store;
	}
    }
}