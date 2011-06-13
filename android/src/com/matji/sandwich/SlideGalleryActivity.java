package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.adapter.GridImageAdapter;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.AttachFileHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;

public class SlideGalleryActivity extends BaseActivity implements Requestable {
	private Intent intent; 
	private HttpRequest request;
	private HttpRequestManager manager;
	private Gallery g;

	private int[] attachFileIds;
	private int store_id;
	private static final int IMAGE_REQUEST = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slide_gallery);

		intent = getIntent();
		store_id = intent.getIntExtra("store_id", 0);
		request = new AttachFileHttpRequest(this);
		manager = new HttpRequestManager(this, this);
		g = (Gallery) findViewById(R.id.slide_gallery);
		manager.request(this, request(), IMAGE_REQUEST);
	}

	public HttpRequest request() {
		((AttachFileHttpRequest) request).actionStoreList(store_id);
		return request;
	}


	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		// TODO Auto-generated method stub		
		attachFileIds = new int[data.size()];
		/* Set AttachFile ID */
		for (int i = 0; i < data.size(); i++) {
			attachFileIds[i] = ((AttachFile) data.get(i)).getId();
		}

		GridImageAdapter adapter = new GridImageAdapter(this);
		adapter.setAttachFileIds(attachFileIds);

		g.setAdapter(adapter);
		g.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Log.d("Matji", "AA");
			}
		});
	}


	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(this);
	}
}