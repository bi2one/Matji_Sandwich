package com.matji.sandwich.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
    
import com.matji.sandwich.R;
import com.matji.sandwich.data.Store;

// import com.matji.android.v2.common.Constants;
// import com.matji.android.v2.common.ImageDownloader;

public class StoreAdapter extends MBaseAdapter {
    public StoreAdapter(Context context) {
	super(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
	Log.d("========", position + "");
	StoreElement storeElement;
	Store store = (Store) data.get(position);

	// When convertView is not null, we can reuse it directly, there is no
	// need
	// to reinflate it. We only inflate a new View when the convertView
	// supplied
	// by ListView is null.
	if (convertView == null) {
	    storeElement = new StoreElement();
	    convertView = getLayoutInflater().inflate(R.layout.store_adapter, null);
	    // Creates a ViewHolder and store references to the two children
	    // views
	    // we want to bind data to.
	    storeElement.image = (ImageView) convertView.findViewById(R.id.thumnail);
	    storeElement.name = (TextView) convertView.findViewById(R.id.store_adapter_name);
	    storeElement.address = (TextView) convertView.findViewById(R.id.store_adapter_address);
	    convertView.setTag(storeElement);
	} else {
	    // Get the ViewHolder back to get fast access to the TextView
	    // and the ImageView.
	    storeElement = (StoreElement) convertView.getTag();
	}

	// Bind the data efficiently with the holder.
	storeElement.name.setText(store.getName());
	storeElement.address.setText(store.getAddress());
	return convertView;
    }

    private class StoreElement {
	ImageView image;
	TextView name;
	TextView address;
    }
}
