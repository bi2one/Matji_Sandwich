package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.matji.sandwich.ImageSliderActivity;
import com.matji.sandwich.adapter.ImageAdapter;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.request.AttachFileHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.util.ModelType;

public class ImageListView extends RequestableMListView implements View.OnClickListener {
	private Context context;
	private HttpRequest request;
	private int id;
	private ModelType type;
	private ImageAdapter adapter;

	private ArrayList<int[]> idList;

	private static final int LIMIT = 5;

	public ImageListView(Context context, AttributeSet attrs) {
		super(context, attrs, new ImageAdapter(context), LIMIT);
		this.context = context;
		adapter = (ImageAdapter) getMBaseAdapter();

		idList = new ArrayList<int[]>();
		adapter.setData(idList);		
		request = new AttachFileHttpRequest(context);
		setPage(1); 
		setDivider(null);
		setCacheColorHint(Color.TRANSPARENT);
		setVerticalScrollBarEnabled(false);
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setType(ModelType type) {
		this.type = type;
	}

	public HttpRequest request() {
		switch (type) {
		case STORE:
			((AttachFileHttpRequest) request).actionStoreList(id, getPage(), getLimit() * adapter.getImageViewCount());
			break;
		case USER:
			((AttachFileHttpRequest) request).actionUserList(id, getPage(), getLimit() * adapter.getImageViewCount());
			break;
		}

		return request;
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		super.requestCallBack(tag, data);
		
		getIdList(data);
		adapter.notifyDataSetChanged();
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		super.requestExceptionCallBack(tag, e);
	}

	private void getIdList(ArrayList<MatjiData> data) {
		for (int i = 0; i < data.size(); i = i+adapter.getImageViewCount()){
			int[] ids = new int[adapter.getImageViewCount()];
			
			for (int j = 0; j < ids.length; j++){
				ids[j] = ImageAdapter.IMAGE_IS_NULL;
			}
			
			for (int j = 0; (j < ids.length) && (i + j < data.size()); j++) {
				ids[j] = ((AttachFile) data.get(i + j)).getId();
			}
			
			idList.add(ids);
		}
	}
	
	private int[] convertIds() {
		int idCount = 0;
		for (int[] arr : idList) {
			idCount += arr.length;
		}
		int[] convertedIds = new int[idCount];

		for (int i = 0; i < idList.size(); i++) {
			for (int j = 0; j < idList.get(i).length; j++) {
				convertedIds[i * adapter.getImageViewCount() + j] = idList.get(i)[j];
			}
		}
		
		return convertedIds;
	}
	
	public void onClick(View v) {
		int position = Integer.parseInt((String)v.getTag());
		callImageViewer(position);
	}

	public void callImageViewer(int position) {
		Intent viewerIntent = new Intent(context, ImageSliderActivity.class);
		viewerIntent.putExtra("attach_file_ids", convertIds());
		viewerIntent.putExtra("position", position);
		context.startActivity(viewerIntent);
	}
	
	public void onListItemClick(int position) {}
}