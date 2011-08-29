// package com.matji.sandwich.adapter;

// import android.content.Context;
// import android.view.View;
// import android.view.ViewGroup;
// import android.view.LayoutInflater;
// import android.widget.TextView;
// import android.widget.RelativeLayout;
// import android.graphics.drawable.Drawable;
// import android.util.Log;

// import com.matji.sandwich.R;
// import com.matji.sandwich.data.MatjiData;
// import com.matji.sandwich.data.Store;
// import com.matji.sandwich.data.AttachFile;
// import com.matji.sandwich.widget.BookmarkStarToggleView;
// import com.matji.sandwich.http.request.NearBookmarkStoreRequest;
// import com.matji.sandwich.adapter.listener.StoreClickListener;

// import java.util.ArrayList;

// public class MapStoreSectionedAdapter extends SectionedAdapter {
//     private static final String SECTION_BOOKMARK_STORE = "MapStoreSectionedAdapter.section_bookmarked_store";
//     private static final String SECTION_STORE = "MapStoreSectionedAdapter.section_store";
    
//     private LayoutInflater inflater;
//     private Context context;
//     private NearBookmarkStoreRequest storeRequest;
//     private int nearByStoreIndex;
//     private ArrayList<MatjiData> bookmarkedList;
//     private RelativeLayout spinnerContainer;
    
//     public MapStoreSectionedAdapter(Context context) {
// 	super(context);
// 	this.context = context;
// 	inflater = getLayoutInflater();
//     }

//     public void init(NearBookmarkStoreRequest storeRequest, RelativeLayout spinnerContainer) {
// 	setNearBookmarkStoreRequest(storeRequest);
// 	this.spinnerContainer = spinnerContainer;
//     }

//     public void setNearBookmarkStoreRequest(NearBookmarkStoreRequest storeRequest) {
// 	this.storeRequest = storeRequest;
//     }

//     public void notifyDataSetChanged() {
// 	nearByStoreIndex = storeRequest.getFirstNearByStoreIndex();
// 	bookmarkedList = storeRequest.getBookmarkedList();
// 	// Log.d("=====", "index: " + bookmarkedList.size());
// 	super.notifyDataSetChanged();
//     }

//     public View getSectionView(View convertView, String section) {
// 	StoreSectionElement storeSectionElement;
	
// 	if (convertView == null) {
// 	    storeSectionElement = new StoreSectionElement();
// 	    convertView = inflater.inflate(R.layout.adapter_map_store_section, null);

// 	    storeSectionElement.subject = (TextView)convertView.findViewById(R.id.map_store_section_subject);
// 	    convertView.setTag(storeSectionElement);
// 	} else {
// 	    storeSectionElement = (StoreSectionElement)convertView.getTag();
// 	}

// 	if (section.equals(SECTION_BOOKMARK_STORE)) {
// 	    storeSectionElement.subject.setText(context.getString(R.string.map_store_sectioned_adapter_bookmarked_store));
// 	} else {
// 	    storeSectionElement.subject.setText(context.getString(R.string.map_store_sectioned_adapter_store));
// 	}
	
// 	return convertView;
//     }

//     public View getItemView(int position, View convertView, ViewGroup parent) {
// 	StoreElement storeElement;
// 	Store store = (Store) data.get(position);

// 	if (convertView == null) {
// 	    storeElement = new StoreElement();
// 	    convertView = getLayoutInflater().inflate(R.layout.adapter_near_store, null);

// 	    storeElement.name = (TextView) convertView.findViewById(R.id.adapter_near_store_name);
// 	    storeElement.likeCount = (TextView) convertView.findViewById(R.id.adapter_near_store_like_count);
// 	    storeElement.postCount = (TextView) convertView.findViewById(R.id.adapter_near_store_post_count);
// 	    storeElement.bookmarkToggle = (BookmarkStarToggleView) convertView.findViewById(R.id.adapter_near_store_bookmark);
// 	    storeElement.listener = new StoreClickListener(context);

// 	    convertView.setTag(storeElement);
// 	} else {
// 	    storeElement = (StoreElement) convertView.getTag();
// 	}

// 	storeElement.name.setText(store.getName());
// 	storeElement.likeCount.setText("" + store.getLikeCount());
// 	storeElement.postCount.setText("" + store.getPostCount());
// 	storeElement.bookmarkToggle.init(this, bookmarkedList, store, spinnerContainer);
// 	storeElement.listener.setStore(store);
// 	convertView.setOnClickListener(storeElement.listener);

// 	return convertView;
//     }

//     public String putSectionName(int position) {
// 	// if (position >= nearByStoreIndex) {
// 	return SECTION_STORE;
// 	// } else {
// 	//     return SECTION_BOOKMARK_STORE;
// 	// }
//     }

//     private class StoreSectionElement {
// 	TextView subject;
//     }

//     private class StoreElement {
// 	TextView name;
// 	TextView likeCount;
// 	TextView postCount;
// 	BookmarkStarToggleView bookmarkToggle;
// 	StoreClickListener listener;
//     }
// }
