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
import com.matji.sandwich.data.AttachFileIds;
import com.matji.sandwich.data.MatjiData;

import com.matji.sandwich.http.request.AttachFileIdsHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.util.ModelType;

public class ImageListView extends RequestableMListView implements View.OnClickListener {
	private Context context;
	private HttpRequest request;

	private int id;
	private int imageCount;
	
	private ModelType type;
	private static final int LIMIT = 5;
	
	public ImageListView(Context context, AttributeSet attrs) {
		super(context, attrs, new ImageAdapter(context), LIMIT);
		this.context = context;
		
		imageCount = ((ImageAdapter) getMBaseAdapter()).getImageViewCount();
		request = new AttachFileIdsHttpRequest(context, imageCount);
				
		setDivider(null);
		setCacheColorHint(Color.TRANSPARENT);
		setVerticalScrollBarEnabled(false);

	}
	
	public void setModelId(int id) {
		this.id = id;
	}

	public void setType(ModelType type) {
		this.type = type;
	}

	public HttpRequest request() {
		switch (type) {
		case STORE:
			((AttachFileIdsHttpRequest) request).actionStoreList(id, getPage(), LIMIT * imageCount);
			break;
		case USER:
			((AttachFileIdsHttpRequest) request).actionUserList(id, getPage(), LIMIT * imageCount);
			break;
		}

		return request;
	}

	private int[] convertIds() {
		int idCount = 0;
		ArrayList<MatjiData> adapterData = getAdapterData();
		
		for (MatjiData arr : adapterData) {
			idCount += ((AttachFileIds) arr).getIds().length;
		}
		
		int[] convertedIds = new int[idCount];

		for (int i = 0; i < adapterData.size(); i++) {
			for (int j = 0; j < ((AttachFileIds) adapterData.get(i)).getIds().length; j++) {
				convertedIds[i * imageCount + j] = ((AttachFileIds) adapterData.get(i)).getIds()[j];
			}
		}
		
		return convertedIds;
	}
	
	public void onClick(View v) {
		int position = Integer.parseInt((String)v.getTag());
		Log.d("Matji", position+"");
		if (position >= 0) {
			callImageViewer(position);
		}
	}

	public void callImageViewer(int position) {
		Intent viewerIntent = new Intent(context, ImageSliderActivity.class);
		viewerIntent.putExtra("attach_file_ids", convertIds());
		viewerIntent.putExtra("position", position);
		context.startActivity(viewerIntent);
	}	
	
	public void onListItemClick(int position) {}
}