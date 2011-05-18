package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.adapter.TestCommentAdapter;
import com.matji.sandwich.data.Alarm;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.data.Following;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.AlarmHttpRequest;

import com.matji.sandwich.http.request.CommentHttpRequest;
import com.matji.sandwich.http.request.FollowingHttpRequest;
import com.matji.sandwich.http.request.TagHttpRequest;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import android.widget.ListView;

public class TestListActivity extends Activity implements Requestable {	
	HttpRequestManager manager;
	ArrayList<MatjiData> data;
	TestCommentAdapter adapter;
//	TestAttachFileAdapter adapter

	ListView list;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_list);

		data = new ArrayList<MatjiData>();
		adapter = new TestCommentAdapter(this, R.layout.test_list_view_row, data);
//		adapter = new TestAttachFileAdapter(this, R.layout.test_list_view_row, data);
		list = (ListView) findViewById(R.id.list);
		list.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		Log.d("Matji", data.size()+"");
		manager = new HttpRequestManager(getApplicationContext(), this);
		request();
	}

	private void request() {///		AttachFileHttpRequest request = new AttachFileHttpRequest();
//		request.actionList();
//		request.actionStoreList(12296);
		CommentHttpRequest request = new CommentHttpRequest();
//		request.actionNew(20180, "Comment test", "ANDROID");
		request.actionList(20180);
		manager.request(request, 1);
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		this.data = data;
		adapter = new TestCommentAdapter(this, R.layout.test_list_view_row, data);
//		adapter = new TestAttachFileAdapter(this, R.layout.test_list_view_row, data);
		list.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.showToastMsg(getApplicationContext());
	}
}