//��怨녹� JSON ����몃� ���怨녹����.

package com.matji.sandwich;

import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.*;
import com.matji.sandwich.exception.MatjiException;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

public class JsonActivity<T> extends Activity implements Requestable<T> {
	HttpRequestManager manager;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		manager = new HttpRequestManager(getApplicationContext(), this);
		request();
	}

	private void request() {
		StoreFoodHttpRequest request = new StoreFoodHttpRequest(getApplicationContext());
//		FollowingHttpRequest request = new FollowingHttpRequest();
//		CommentHttpRequest request = new CommentHttpRequest();
//		NoticeHttpRequest request = new NoticeHttpRequest();
//		TagHttpRequest request = new TagHttpRequest();
		request.actionList(12296);
//		request.actionList(12296);
//		request.actionNew(12296, "�↔���);
//		request.actionDelete(16931);
//		request.actionLike(32);		
//		request.actionDelete(34);		
//		manager.request(request, 1);
	}

	public void requestCallBack(int tag, ArrayList<T> data) {
//		StoreFood food = (StoreFood)data.get(0);
//		Log.d("Matji", "" + food.getId());
//		Log.d("Matji", "name: " +  food.getFood().getName());
//		Log.d("Matji", "like_count: " +  food.getLikeCount());
//		Log.d("Matji", "created_at: " + food.getFood().getCreatedAt());
//		Comment comment = (Comment)data.get(0);
//		Log.d("Matji", "" + comment.getComment());
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.showToastMsg(getApplicationContext());
	}
}