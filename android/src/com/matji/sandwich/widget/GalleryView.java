//package com.matji.sandwich.widget;
//
//import java.util.ArrayList;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Gallery;
//import android.widget.Toast;
//import android.widget.AdapterView.OnItemClickListener;
//
//import com.matji.sandwich.GalleryActivity;
//import com.matji.sandwich.R;
//import com.matji.sandwich.Requestable;
//import com.matji.sandwich.adapter.ImageAdapter;
//import com.matji.sandwich.data.AttachFile;
//import com.matji.sandwich.data.MatjiData;
//import com.matji.sandwich.exception.MatjiException;
//import com.matji.sandwich.http.HttpRequestManager;
//import com.matji.sandwich.http.request.AttachFileHttpRequest;
//import com.matji.sandwich.http.request.HttpRequest;
//
//public class GalleryView implements Requestable {
//	private HttpRequest request;
//	private HttpRequestManager manager;
//	private Gallery g;
//
//	private int[] attachFileIds;
//	private int store_id;
//	private static final int IMAGE_REQUEST = 0;
//	라이크 북마크 메모 뜨는 것 까지 확인 activity memo<<
//	// View 로 빼는건 나중에 
//	public GalleryView(Context context) {
//		request = new AttachFileHttpRequest(context);
//		manager = new HttpRequestManager(context, this);
//		g = (Gallery) findViewById(R.id.gallery);
//		manager.request(this, request(), IMAGE_REQUEST);
//	}
//
//	public void setStoerId(int store_id) {
//		this.store_id = store_id;
//	}
//	
//	public HttpRequest request() {
//		((AttachFileHttpRequest) request).actionStoreList(store_id);
//		return request;
//	}
//	
//	@Override
//	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
//		// TODO Auto-generated method stub
//		ImageAdapter adapter = new ImageAdapter(this);
//		
//		attachFileIds = new int[data.size()];
//		/* Set AttachFile ID */
//		for (int i = 0; i < data.size(); i++) {
//			attachFileIds[i] = ((AttachFile) data.get(i)).getId();
//		}
//		adapter.setAttachFileIds(attachFileIds);
//		
//		g.setAdapter(adapter);
//		g.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//				Toast.makeText(GalleryActivity.this, "" + position, Toast.LENGTH_SHORT).show();
//			}
//		});
//	}
//
//	@Override
//	public void requestExceptionCallBack(int tag, MatjiException e) {
//		e.performExceptionHandling(this);
//	}
//}
