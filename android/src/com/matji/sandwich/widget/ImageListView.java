package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.ImageSliderActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.adapter.ImageAdapter;
import com.matji.sandwich.data.AttachFileIds;
import com.matji.sandwich.data.MatjiData;

import com.matji.sandwich.http.request.AttachFileIdsHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

public class ImageListView extends RequestableMListView implements View.OnClickListener {
	protected HttpRequest request;

	protected int imageCount;
	
	private static final int LIMIT = 5;
	
	public ImageListView(Context context, AttributeSet attrs) {
		super(context, attrs, new ImageAdapter(context), LIMIT);
		
		imageCount = ((ImageAdapter) getMBaseAdapter()).getImageViewCount();
				
		setDivider(null);
		setSelector(android.R.color.transparent);
		setCacheColorHint(Color.TRANSPARENT);
		setVerticalScrollBarEnabled(false);
		setBackgroundDrawable(getResources().getDrawable(R.drawable.pattern_bg));
	}

	protected void createRequest() {
		if (request == null || !(request instanceof AttachFileIdsHttpRequest)) {
			request = new AttachFileIdsHttpRequest(getContext(), imageCount);
		}
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

		if (position >= 0) {
			callImageViewer(position);
		}
	}

	public void callImageViewer(int position) {
		Intent viewerIntent = new Intent(getContext(), ImageSliderActivity.class);
		viewerIntent.putExtra("attach_file_ids", convertIds());
		viewerIntent.putExtra("position", position);
		getContext().startActivity(viewerIntent);
	}	
	
	public void onListItemClick(int position) {}

	@Override
	public HttpRequest request() {
		return request;
	}
}