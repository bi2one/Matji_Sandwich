//이 곳은 JSON 테스트를 하는곳입니다.

package com.matji.sandwich;

import com.matji.sandwich.data.*;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.*;
import com.matji.sandwich.exception.MatjiException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class JsonActivity extends Activity implements Requestable{
	HttpRequestManager manager;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		manager = new HttpRequestManager(getApplicationContext(), this);
		request();
	}

	private void request() {
		FoodHttpRequest request = new FoodHttpRequest();
//		FollowingHttpRequest request = new FollowingHttpRequest();
//		CommentHttpRequest request = new CommentHttpRequest();
//		NoticeHttpRequest request = new NoticeHttpRequest();
//		TagHttpRequest request = new TagHttpRequest();
//		request.actionList(12296);
		request.actionList(1);
//		request.actionList(12296);
//		request.actionNew(12296, "육개장");
//		request.actionDelete(16931);
//		request.actionLike(32);		
//		request.actionDelete(34);		
		manager.request(request, 1);
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
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
