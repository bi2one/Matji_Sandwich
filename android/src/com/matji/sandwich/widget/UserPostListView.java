package com.matji.sandwich.widget;

import android.app.TabActivity;
import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.widget.cell.UserCell;
import com.matji.sandwich.widget.cell.UserIntroCell;

public class UserPostListView extends PostListView {
	private HttpRequest request;
	private User user;

	public UserPostListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	protected void init() {
		super.init();
		
		request = new PostHttpRequest(getContext());
		setPage(1);
	}

	public void setUser(User user) {
		this.user = user;
		
		addHeaderView(new UserCell(getContext(), user));
		addHeaderView(new UserIntroCell(getContext(), user));
	}
	
	@Override
	public HttpRequest request() {
		((PostHttpRequest) request).actionUserList(user.getId(), getPage(), getLimit());
		return request;
	}
	
	@Override
	protected void gotoUserPage(Post post) {
		TabActivity act = (TabActivity) getActivity().getParent();
		act.getTabHost().setCurrentTab(UserTabActivity.MAIN_PAGE);
	}
}