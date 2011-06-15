package com.matji.sandwich;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.CommentListView;
import com.matji.sandwich.widget.PostViewContainer;

public class PostMainActivity extends MainActivity {
	private Post post;
	private User user;
	private Store store;

	private PostViewContainer header;
	private TextView tagsText;
	private CommentListView commentListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_main);
		
		post = (Post) SharedMatjiData.getInstance().top();
		user = post.getUser();
		store = post.getStore();
		
		header = new PostViewContainer(this, post, user, store);
		commentListView = (CommentListView) findViewById(R.id.post_main_comment_list);
		commentListView.setPostId(post.getId());
		commentListView.setActivity(this);
		commentListView.requestReload();		
	}

	private void setInfo() {
		commentListView.getHeaderViewContainer().removeView(header.getRootView());
		commentListView.addHeaderView(header);
		commentListView.requestReload();		
	}

	@Override
	public void onResume() {
		super.onResume();
		
		setInfo();
	}

	@Override
	public void finish() {
		super.finishWithMatjiData();
	}

	@Override
	protected String titleBarText() {
		return "PostMainActivity";
	}

	@Override
	protected boolean setTitleBarButton(Button button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		// TODO Auto-generated method stub
		
	}
}