package com.matji.sandwich.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
    
import com.matji.sandwich.R;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.http.util.MatjiImageDownloader;

public class SimpleStoreAdapter extends MBaseAdapter {
	MatjiImageDownloader downloader;
	
	public SimpleStoreAdapter(Context context) {
		super(context);
		downloader = new MatjiImageDownloader(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		StoreElement storeElement;
		Store store = (Store) data.get(position);

		// When convertView is not null, we can reuse it directly, there is no
		// need
		// to reinflate it. We only inflate a new View when the convertView
		// supplied
		// by ListView is null.
		if (convertView == null) {
			storeElement = new StoreElement();
			convertView = getLayoutInflater().inflate(R.layout.adapter_store, null);
			// Creates a ViewHolder and store references to the two children
			// views
			// we want to bind data to.
			storeElement.thumbnail = (ImageView) convertView.findViewById(R.id.store_adapter_thumbnail);
			storeElement.name = (TextView) convertView.findViewById(R.id.store_adapter_name);
			storeElement.address = (TextView) convertView.findViewById(R.id.store_adapter_address);
			storeElement.likeCount = (TextView) convertView.findViewById(R.id.store_adapter_like_count);
			convertView.setTag(storeElement);
		} else {
			// Get the ViewHolder back to get fast access to the TextView
			// and the ImageView.
			storeElement = (StoreElement) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		AttachFile file = store.getFile();
		if (file != null) {
			downloader.downloadAttachFileImage(file.getId(), MatjiImageDownloader.IMAGE_SMALL, storeElement.thumbnail);
		} else {			
			Drawable defaultImage = context.getResources().getDrawable(R.drawable.img_thumbnail_bg);
			storeElement.thumbnail.setImageDrawable(defaultImage);
		}
		storeElement.name.setText(store.getName());
		storeElement.address.setText(store.getAddress());
		storeElement.likeCount.setText(store.getLikeCount()+"");
		
		return convertView;
	}

	private class StoreElement {
		ImageView thumbnail;
		TextView name;
		TextView address;
		TextView likeCount;
	}
}