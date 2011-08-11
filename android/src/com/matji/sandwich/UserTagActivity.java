package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.TagHttpRequest;
import com.matji.sandwich.widget.tag.TagCloudView;

public class UserTagActivity extends BaseActivity implements Requestable {
	private HttpRequest request;
	private TagCloudView tagCloudView;
	private User user;
	
	private final int USER_TAG_LIST_REQUEST = 11;
	public static final String USER = "user";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_tag);
		user = (User) getIntent().getParcelableExtra(USER);
		HttpRequestManager.getInstance(this).request(this, request(), USER_TAG_LIST_REQUEST, this);
		
		tagCloudView = (TagCloudView) findViewById(R.id.user_tag_cloud);
		
		TextView tagCount = (TextView) findViewById(R.id.user_tag_count);
		tagCount.setText(user.getTagCount()+getString(R.string.number_of_tag));
	}
	
	private HttpRequest request() {
		if (request == null || !(request instanceof TagHttpRequest)) {
			request = new TagHttpRequest(this);
		}
		
		((TagHttpRequest) request).actionUserTagListForCloud(user.getId());

		return request;
	}
	
	
	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		// TODO Auto-generated method stub
		ArrayList<Tag> tags = new ArrayList<Tag>();
		for (MatjiData d : data) {
			tags.add((Tag) d);
		}
		
		tagCloudView.init(tags);
	}

	@Override
	public void requestExceptionCallBack(int tag, MatjiException e) {
		// TODO Auto-generated method stub
		e.performExceptionHandling(this);
	}
}
