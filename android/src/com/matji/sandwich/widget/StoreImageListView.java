package com.matji.sandwich.widget;

import com.matji.sandwich.data.Store;
import com.matji.sandwich.http.request.AttachFilesHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

import android.content.Context;
import android.util.AttributeSet;

public class StoreImageListView extends ImageListView {
	
	private Store store;
	
	public StoreImageListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public HttpRequest request() {
		createRequest();
		((AttachFilesHttpRequest) request).actionStoreList(store.getId(), getPage(), getLimit() * imageCount);
		
		return request;
	}
	
	public void setStore(Store store) {
		this.store = store;
		
		init();
	}
	
	@Override
	protected String getTotalImageCountText() {
		return store.getImageCount() + "개의 " + store.getName() + " 사진";
	}
}
