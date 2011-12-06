package com.matji.sandwich.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.RelativeLayout;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.listener.StoreClickListener;
import com.matji.sandwich.data.Food;
import com.matji.sandwich.data.SimpleTag;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.widget.BookmarkStarToggleView;

public class SimpleStoreAdapter extends MBaseAdapter {
    private boolean isVisibleStar = false;
    private OnClickListener listener;

    public SimpleStoreAdapter(Context context) {
        super(context);
    }

    public void visibleBookmarkView() {
        isVisibleStar = true;
    }

    public void goneBookmarkView() {
        isVisibleStar = false;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
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
            convertView = getLayoutInflater().inflate(R.layout.row_simple_store, null);
            // Creates a ViewHolder and store references to the two children
            // views
            // we want to bind data to.
            storeElement.name = (TextView) convertView.findViewById(R.id.row_simple_store_name);
            storeElement.address = (TextView) convertView.findViewById(R.id.row_simple_store_address);
            storeElement.likeCount = (TextView) convertView.findViewById(R.id.row_simple_store_like_count);
            storeElement.postCount = (TextView) convertView.findViewById(R.id.row_simple_store_post_count);
            storeElement.foodList = (TextView) convertView.findViewById(R.id.row_simple_store_foodList);
            storeElement.bookmarkToggle = (BookmarkStarToggleView) convertView.findViewById(R.id.row_simple_store_bookmark);
            storeElement.spinnerWrapper = (RelativeLayout) convertView.findViewById(R.id.row_simple_store_spinner_wrapper);
            storeElement.listener = new StoreClickListener(context);
            convertView.setTag(storeElement);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            storeElement = (StoreElement) convertView.getTag();
        }

        // Bind the data efficiently with the holder.
        storeElement.name.setText(store.getName());
        storeElement.address.setText(store.getAddress());
        storeElement.likeCount.setText("" + store.getLikeCount());
        storeElement.postCount.setText("" + store.getPostCount());
        String foodText = "";
        String tagText = "";
        for (Food food : store.getFoods()) {
        	foodText += food.getName() + ", ";
        }
        for (SimpleTag tag : store.getTags()) {
        	tagText += tag.getTag() + ", ";
        }
        String mergeText = foodText + tagText;
        if (mergeText.length() > 1)
        	mergeText = mergeText.substring(0, mergeText.length()-2);
        storeElement.foodList.setText("" + mergeText); 
        
        if (isVisibleStar) {
            storeElement.bookmarkToggle.init(store, storeElement.spinnerWrapper);
            //        storeElement.bookmarkToggle.init(this, bookmarkedList, store);
            storeElement.bookmarkToggle.setVisibility(View.VISIBLE);
        } else {
            storeElement.bookmarkToggle.setVisibility(View.GONE);
        }
        storeElement.listener.setStore(store);
        storeElement.setStore(store);

        if (listener == null)
            convertView.setOnClickListener(storeElement.listener);
        else
            convertView.setOnClickListener(listener);

        return convertView;
    }

    public class StoreElement {
        TextView name;
        TextView address;
        TextView likeCount;
        TextView postCount;
        TextView foodList;
        RelativeLayout spinnerWrapper;
        BookmarkStarToggleView bookmarkToggle;
        StoreClickListener listener;
        Store store;
        

        public void setStore(Store store) {
            this.store = store;
        }

        public Store getStore() {
            return store;
        }
    }
}