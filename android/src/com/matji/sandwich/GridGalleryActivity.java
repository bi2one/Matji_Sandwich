package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.adapter.GridImageAdapter;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.AttachFileHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.UserHttpRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class GridGalleryActivity extends Activity implements Requestable {
	private Intent intent; 
	private HttpRequest request;
	private HttpRequestManager manager;
	private GridView g;

	private int[] attachFileIds;
	private int id;
	private AttachFileType type;

	private static final int IMAGE_REQUEST = 0;
	protected static final String ATTACH_FILE_TYPE = "type";

	protected enum AttachFileType {
		STORES,
		USERS
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid_gallery);

		intent = getIntent();
		type = (AttachFileType) intent.getSerializableExtra(ATTACH_FILE_TYPE);
		switch (type) {
		case STORES:
			id = intent.getIntExtra("store_id", 0);
			request = new AttachFileHttpRequest(this);
			break;
		case USERS:
			id = intent.getIntExtra("user_id", 0);
			request = new UserHttpRequest(this);
			break;
		}
		manager = new HttpRequestManager(this, this);
		g = (GridView) findViewById(R.id.grid_gallery);
		manager.request(this, request(), IMAGE_REQUEST);
	}

	public HttpRequest request() {
		switch (type) {
		case STORES:
			((AttachFileHttpRequest) request).actionStoreList(id);
			break;
		case USERS:
			((UserHttpRequest) request).actionShow(id, false, false, true);
			break;
		}

		return request;
	}

	
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		// TODO Auto-generated method stub		
		/* Set AttachFile ID */
		switch (type) {
		case STORES:
			attachFileIds = new int[data.size()];
			for (int i = 0; i < data.size(); i++) {
				attachFileIds[i] = ((AttachFile) data.get(i)).getId();
			}
			break;
		case USERS:
			User user = (User) data.get(0);
			ArrayList<AttachFile> attachFiles = user.getAttachFiles();
			if (attachFiles != null ) {
				attachFileIds = new int[attachFiles.size()];
				for (int i = 0; i < attachFiles.size(); i++) {
					attachFileIds[i] = attachFiles.get(i).getId();
				}
			}
			break;
		}

		GridImageAdapter adapter = new GridImageAdapter(this);
		adapter.setAttachFileIds(attachFileIds);

		g.setAdapter(adapter);
		g.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				callImageViewer(position);
			}
		});
	}


	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(this);
	}

	public void callImageViewer(int position) { 
		Intent viewerIntent = new Intent(this, ImageSliderActivity.class);
		viewerIntent.putExtra("attach_file_ids", attachFileIds);
		viewerIntent.putExtra("position", position);
		startActivity(viewerIntent);
	}
}