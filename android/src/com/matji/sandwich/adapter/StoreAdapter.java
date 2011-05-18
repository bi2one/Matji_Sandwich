package com.matji.sandwich.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;

import java.util.ArrayList;
// import com.matji.android.v2.common.Constants;
// import com.matji.android.v2.common.ImageDownloader;

public class StoreAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<MatjiData> data;
    
    public StoreAdapter(Context context, ArrayList<MatjiData> data) {
	inflater = LayoutInflater.from(context);
	this.context = context;
	this.data = data;
    }
  	
    public int getCount() {
	return data.size();
    }
   
    public Object getItem(int position) {
	return data.get(position);
    }
   
    public long getItemId(int position) {
	return position;
    }
   
    public View getView(int position, View convertView, ViewGroup parent) {
        StoreElement storeElement;
	Store store = (Store)data.get(position);

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
            storeElement = new StoreElement();
            convertView = inflater.inflate(R.layout.store_adapter, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            storeElement.name = (TextView)convertView.findViewById(R.id.store_adapter_name);
            convertView.setTag(storeElement);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            storeElement = (StoreElement)convertView.getTag();
        }

        // Bind the data efficiently with the holder.
	// Log.d("aaaaaa", "" + store.getName());
	storeElement.name.setText(store.getName());
        return convertView;
    }
    
    private class StoreElement {
        TextView name;
    }
}
