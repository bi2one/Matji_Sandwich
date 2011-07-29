package com.matji.sandwich.widget;

import com.matji.sandwich.SharedMatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.AttachFileIdsHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 유저가 올린 이미지를 보여주는 뷰
 * 
 * @author mozziluv
 */
public class UserImageListView extends ImageListView {
	private User user;
	
	public UserImageListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		/* 기본적으로 유저 이야기 페이지 안에 있는 뷰이므로,
		 * SharedMatjiData의 top에 해당 User의 정보가 들어있어야 한다.
		 */
		user = (User) SharedMatjiData.getInstance().top();
	}

	@Override
	public HttpRequest request() {
		createRequest();
		((AttachFileIdsHttpRequest) request).actionUserList(user.getId(), getPage(), getLimit() * imageCount);
		
		return request;
	}
}
