package com.matji.sandwich.widget;

import com.matji.sandwich.SharedMatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.http.request.AttachFileIdsHttpRequest;
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
		((AttachFileIdsHttpRequest) request).actionStoreList(store.getId(), getPage(), getLimit() * imageCount);
		
		return request;
	}

	@Override
	protected void setModelData() {
		/* 기본적으로 맛집 이야기 페이지 안에 있는 뷰이므로,
		 * SharedMatjiData의 top에 해당 Store의 정보가 들어있어야 한다.
		 */
		store = (Store) SharedMatjiData.getInstance().top();
	}

	@Override
	protected String getTotalImageCountText() {
		return store.getImageCount() + "개의 " + store.getName() + " 사진";
	}
}
