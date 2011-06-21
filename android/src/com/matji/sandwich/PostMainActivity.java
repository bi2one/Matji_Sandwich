package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.CommentListView;
import com.matji.sandwich.widget.PostViewContainer;

public class PostMainActivity extends MainActivity {
	private Post post;
	private User user;
	private Store store;
	private Session session;
	
	private PostViewContainer header;
	private TextView tagsText;
	private CommentListView commentListView;

	public static final int LOGIN_ACTIVITY = 1;
	public static final int WRITE_COMMENT_ACTIVITY = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_main);

		post = (Post) SharedMatjiData.getInstance().top();
		user = post.getUser();
		store = post.getStore();

		session = Session.getInstance(this);
		
		header = new PostViewContainer(this, this, post, user, store);
		commentListView = (CommentListView) findViewById(R.id.post_main_comment_list);
		commentListView.setPostId(post.getId());
		commentListView.setActivity(this);
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
		button.setText("Comment");

		return true;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		if (session.getToken() == null) {
			startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), LOGIN_ACTIVITY);
		} else {
			Intent intent = new Intent(getApplicationContext(), WriteCommentActivity.class);
			intent.putExtra("post_id", post.getId());
			startActivityForResult(intent, WRITE_COMMENT_ACTIVITY);
		}		
	}
}