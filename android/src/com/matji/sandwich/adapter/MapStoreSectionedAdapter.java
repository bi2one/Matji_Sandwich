package com.matji.sandwich.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.drawable.Drawable;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.http.request.NearBookmarkStoreRequest;

public class MapStoreSectionedAdapter extends SectionedAdapter {
    private static final String SECTION_BOOKMARK_STORE = "MapStoreSectionedAdapter.section_bookmarked_store";
    private static final String SECTION_STORE = "MapStoreSectionedAdapter.section_store";
    
    private LayoutInflater inflater;
    private Context context;
    private MatjiImageDownloader downloader;
    private NearBookmarkStoreRequest storeRequest;
    private int nearByStoreIndex;
    
    public MapStoreSectionedAdapter(Context context) {
	super(context);
	this.context = context;
	inflater = getLayoutInflater();
	downloader = new MatjiImageDownloader(context);
    }

    public void init(NearBookmarkStoreRequest storeRequest) {
	setNearBookmarkStoreRequest(storeRequest);
    }

    public void setNearBookmarkStoreRequest(NearBookmarkStoreRequest storeRequest) {
	this.storeRequest = storeRequest;
    }

    public void notifyDataSetChanged() {
	nearByStoreIndex = storeRequest.getFirstNearByStoreIndex();
	super.notifyDataSetChanged();
    }

    public View getSectionView(View convertView, String section) {
	StoreSectionElement storeSectionElement;
	
	if (convertView == null) {
	    storeSectionElement = new StoreSectionElement();
	    convertView = inflater.inflate(R.layout.adapter_map_store_section, null);

	    storeSectionElement.subject = (TextView)convertView.findViewById(R.id.map_store_section_subject);
	    convertView.setTag(storeSectionElement);
	} else {
	    storeSectionElement = (StoreSectionElement)convertView.getTag();
	}

	if (section.equals(SECTION_BOOKMARK_STORE)) {
	    storeSectionElement.subject.setText(context.getString(R.string.map_store_sectioned_adapter_bookmarked_store));
	} else {
	    storeSectionElement.subject.setText(context.getString(R.string.map_store_sectioned_adapter_store));
	}
	
	return convertView;
    }

    public View getItemView(int position, View convertView, ViewGroup parent) {
	StoreElement storeElement;
	Store store = (Store) data.get(position);

	if (convertView == null) {
	    storeElement = new StoreElement();
	    convertView = getLayoutInflater().inflate(R.layout.adapter_store, null);

	    storeElement.thumbnail = (ImageView) convertView.findViewById(R.id.store_adapter_thumbnail);
	    storeElement.name = (TextView) convertView.findViewById(R.id.store_adapter_name);
	    storeElement.address = (TextView) convertView.findViewById(R.id.store_adapter_address);
	    storeElement.likeCount = (TextView) convertView.findViewById(R.id.store_adapter_like_count);
	    convertView.setTag(storeElement);
	} else {
	    storeElement = (StoreElement) convertView.getTag();
	}

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

    public String putSectionName(int position) {
	if (position >= nearByStoreIndex) {
	    return SECTION_STORE;
	} else {
	    return SECTION_BOOKMARK_STORE;
	}
    }


    private class StoreSectionElement {
	TextView subject;
    }

    private class StoreElement {
	ImageView thumbnail;
	TextView name;
	TextView address;
	TextView likeCount;
    }
}