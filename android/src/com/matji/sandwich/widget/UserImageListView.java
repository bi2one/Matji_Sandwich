package com.matji.sandwich.widget;

import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.AttachFileIdsHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.widget.cell.UserCell;

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
	}

	@Override
	public HttpRequest request() {
		createRequest();
		((AttachFileIdsHttpRequest) request).actionUserList(user.getId(), getPage(), getLimit() * imageCount);
		
		return request;
	}

	public void setUser(User user) {
		this.user = user;

        addHeaderView(new UserCell(getContext(), user));
		init();
	}

	@Override
	protected String getTotalImageCountText() {
		return user.getImageCount() + "개의 " + user.getNick() + " 사진";
	}
}
