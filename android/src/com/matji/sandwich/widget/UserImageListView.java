package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.AttachFilesHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

/**
 * 유저가 올린 이미지를 보여주는 뷰
 * 
 * @author mozziluv
 */
public class UserImageListView extends ImageListView {
	private User user;
	
	public UserImageListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public HttpRequest request() {
		createRequest();
		((AttachFilesHttpRequest) request).actionUserList(user.getId(), getPage(), getLimit() * imageCount);
		
		return request;
	}

	public void setUser(User user) {
		this.user = user;
		init();
	}
}
