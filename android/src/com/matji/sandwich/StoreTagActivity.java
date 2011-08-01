package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.TagHttpRequest;
import com.matji.sandwich.widget.tag.TagCloudView;

public class StoreTagActivity extends BaseActivity implements Requestable {
	private final int STORE_TAG_LIST_REQUEST = 10;
	
	private HttpRequest request;
	
	private TagCloudView tagCloudView;
	private TextView tagCount;
	
	private Store store;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_tag);
		store = (Store) SharedMatjiData.getInstance().top();
		HttpRequestManager.getInstance(this).request(this, request(), STORE_TAG_LIST_REQUEST, this);

		tagCloudView = (TagCloudView) findViewById(R.id.store_tag_cloud);
		
		tagCount = (TextView) findViewById(R.id.store_tag_count);
		tagCount.setText(store.getTagCount()+getString(R.string.number_of_tag));
	}
	
	
	private HttpRequest request() {
		if (request == null || !(request instanceof TagHttpRequest)) {
			request = new TagHttpRequest(this);
		}
		
		((TagHttpRequest) request).actionStoreTagList(store.getId(), 1, 50);

		return request;
	}
	
	
	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		// TODO Auto-generated method stub
		ArrayList<Tag> tags = new ArrayList<Tag>();
		for (MatjiData d : data) {
			tags.add((Tag) d);
		}
		
		tagCloudView.init(tags);
	}

	@Override
	public void requestExceptionCallBack(int tag, MatjiException e) {
		// TODO Auto-generated method stub
		e.performExceptionHandling(this);
	}
}
