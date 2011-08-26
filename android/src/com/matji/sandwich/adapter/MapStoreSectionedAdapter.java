//package com.matji.sandwich.adapter;
//
//import java.util.ArrayList;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.matji.sandwich.R;
//import com.matji.sandwich.adapter.listener.StoreClickListener;
//import com.matji.sandwich.data.MatjiData;
//import com.matji.sandwich.data.Store;
//import com.matji.sandwich.http.request.StoreHttpRequest;
//import com.matji.sandwich.widget.BookmarkStarToggleView;
//
//public class MapStoreSectionedAdapter extends SectionedAdapter {
//    private static final String SECTION_BOOKMARK_STORE = "MapStoreSectionedAdapter.section_bookmarked_store";
//    private static final String SECTION_STORE = "MapStoreSectionedAdapter.section_store";
//
//    private LayoutInflater inflater;
//    private Context context;
//    private StoreHttpRequest storeRequest;
//    private int nearByStoreIndex;
//    private ArrayList<MatjiData> bookmarkedList;
//
//    public MapStoreSectionedAdapter(Context context) {
//        super(context);
//        this.context = context;
//        inflater = getLayoutInflater();
//    }
//
//    public void init(StoreHttpRequest storeRequest) {
//        setNearBookmarkStoreRequest(storeRequest);
//    }
//
//    public void setNearBookmarkStoreRequest(StoreHttpRequest storeRequest) {
//        this.storeRequest = storeRequest;
//    }
//
//    public View getSectionView(View convertView, String section) {
//        StoreSectionElement storeSectionElement;
//
//        if (convertView == null) {
//            storeSectionElement = new StoreSectionElement();
//            convertView = inflater.inflate(R.layout.adapter_map_store_section, null);
//
//            storeSectionElement.subject = (TextView)convertView.findViewById(R.id.map_store_section_subject);
//            convertView.setTag(storeSectionElement);
//        } else {
//            storeSectionElement = (StoreSectionElement)convertView.getTag();
//        }
//
//        if (section.equals(SECTION_BOOKMARK_STORE)) {
//            storeSectionElement.subject.setText(context.getString(R.string.map_store_sectioned_adapter_bookmarked_store));
//        } else {
//            storeSectionElement.subject.setText(context.getString(R.string.map_store_sectioned_adapter_store));
//        }
//
//        return convertView;
//    }
//
//    public View getItemView(int position, View convertView, ViewGroup parent) {
//        StoreElement storeElement;
//        Store store = (Store) data.get(position);
//
//        if (convertView == null) {
//            storeElement = new StoreElement();
//            convertView = getLayoutInflater().inflate(R.layout.adapter_near_store, null);
//
//            storeElement.name = (TextView) convertView.findViewById(R.id.adapter_near_store_name);
//            storeElement.likeCount = (TextView) convertView.findViewById(R.id.adapter_near_store_like_count);
//            storeElement.postCount = (TextView) convertView.findViewById(R.id.adapter_near_store_post_count);
//            storeElement.bookmarkToggle = (BookmarkStarToggleView) convertView.findViewById(R.id.adapter_near_store_bookmark);
//            storeElement.listener = new StoreClickListener(context);
//
//            convertView.setTag(storeElement);
//        } else {
//            storeElement = (StoreElement) convertView.getTag();
//        }
//
//        storeElement.name.setText(store.getName());
//        storeElement.likeCount.setText("" + store.getLikeCount());
//        storeElement.postCount.setText("" + store.getPostCount());
//        storeElement.bookmarkToggle.init(this, bookmarkedList, store);
//        storeElement.listener.setStore(store);
//        convertView.setOnClickListener(storeElement.listener);
//
//        return convertView;
//    }
//
//    public String putSectionName(int position) {
//        // if (position >= nearByStoreIndex) {
//        return SECTION_STORE;
//        // } else {
//        //     return SECTION_BOOKMARK_STORE;
//        // }
//    }
//
//    private class StoreSectionElement {
//        TextView subject;
//    }
//
//    private class StoreElement {
//        TextView name;
//        TextView likeCount;
//        TextView postCount;
//        BookmarkStarToggleView bookmarkToggle;
//        StoreClickListener listener;
//    }
//}