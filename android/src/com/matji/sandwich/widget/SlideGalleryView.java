package com.matji.sandwich.widget;

import java.util.ArrayList;

import com.matji.sandwich.Requestable;
import com.matji.sandwich.adapter.SlideImageAdapter;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.AttachFileHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;

public class SlideGalleryView extends Gallery implements Requestable {
	private Context context;
	private Activity activity;
	private HttpRequest request;
	private HttpRequestManager manager;

	private int[] attachFileIds;
	private int post_id;
	private static final int IMAGE_REQUEST = 0;

	
	public SlideGalleryView(Context context, AttributeSet attr) {
		super(context, attr);
		this.context = context;
		
		request = new AttachFileHttpRequest(context);
		manager = new HttpRequestManager(context, this);
	}

	public void setPostId(int post_id) {
		this.post_id = post_id;
	}
	
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	public void refresh() {
		manager.request(activity, request(), IMAGE_REQUEST);
	}
	
	public HttpRequest request() {
		((AttachFileHttpRequest) request).actionPostList(post_id);
		return request;
	}
	
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		// TODO Auto-generated method stub	
		Log.d("Matji", data.size()+"");

		attachFileIds = new int[data.size()];
		/* Set AttachFile ID */
		for (int i = 0; i < data.size(); i++) {
			attachFileIds[i] = ((AttachFile) data.get(i)).getId();
		}

		SlideImageAdapter adapter = new SlideImageAdapter(context);
		adapter.setAttachFileIds(attachFileIds);

		setAdapter(adapter);
		setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Log.d("Matji", "onItemClick | SlideGalleryView");
			}
		});
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(context);
	}
}