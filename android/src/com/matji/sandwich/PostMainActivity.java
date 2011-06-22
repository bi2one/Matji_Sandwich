package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.matji.sandwich.data.Like;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.LikeHttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.CommentListView;
import com.matji.sandwich.widget.PostViewContainer;

public class PostMainActivity extends MainActivity implements Requestable {
	private Post post;
	private User user;
	private Store store;

	private Session session;
	private DBProvider dbProvider;
	private HttpRequest request;
	private HttpRequestManager manager;

	private PostViewContainer header;
	private CommentListView commentListView;

	private Button likeButton;

	public static final int LOGIN_ACTIVITY = 1;
	public static final int WRITE_COMMENT_ACTIVITY = 2;
	public final static int LIKE_REQUEST = 3;
	public final static int UN_LIKE_REQUEST = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_main);

		post = (Post) SharedMatjiData.getInstance().top();
		user = post.getUser();
		store = post.getStore();

		session = Session.getInstance(this);
		dbProvider = DBProvider.getInstance(this);
		manager = new HttpRequestManager(this, this);

		header = new PostViewContainer(this, this, post, user, store);
		commentListView = (CommentListView) findViewById(R.id.post_main_comment_list);
		likeButton = (Button) findViewById(R.id.post_main_like_btn);

		commentListView.setPostId(post.getId());
		commentListView.setActivity(this);

	}

	private void setInfo() {
		header.setInfo();
		
		if (session.isLogin()) {
			if (dbProvider.isExistLike(post.getId(), "Post")) {
				likeButton.setText(getString(R.string.default_string_unlike));
			} else {
				likeButton.setText(getString(R.string.default_string_like));
			}
		}
	}

	private void commentListViewReload() {
		commentListView.getHeaderViewContainer().removeView(header.getRootView());
		commentListView.addHeaderView(header);
		commentListView.requestReload();		
	}
	
	@Override
	public void onResume() {
		super.onResume();

		setInfo();
		commentListViewReload();
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
		return false;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
	}

	public void onCommentButtonClicked(View view) {
		if (session.getToken() == null) {
			startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), LOGIN_ACTIVITY);
		} else {
			Intent intent = new Intent(getApplicationContext(), WriteCommentActivity.class);
			intent.putExtra("post_id", post.getId());
			startActivityForResult(intent, WRITE_COMMENT_ACTIVITY);
		}
	}

	public void onLikeButtonClicked(View view) {
		if (session.isLogin()){
			likeButton.setClickable(false);
			if (dbProvider.isExistLike(post.getId(), "Post")){
				dbProvider.deleteLike(post.getId(), "Post");
				// api request
				unlikeRequest();
			}else {
				Like like = new Like();
				like.setForeignKey(post.getId());
				like.setObject("Post");
				dbProvider.insertLike(like);
				// api request
				likeRequest();
			}
		}
	}

	private void likeRequest() {
		if (request == null || !(request instanceof LikeHttpRequest)) {
			request = new LikeHttpRequest(this);
		}
		((LikeHttpRequest) request).actionPostLike(post.getId());
		manager.request(this, request, LIKE_REQUEST);
		post.setLikeCount(post.getLikeCount() + 1);
	}

	private void unlikeRequest() {
		if (request == null || !(request instanceof LikeHttpRequest)) {
			request = new LikeHttpRequest(this);
		}

		((LikeHttpRequest) request).actionPostUnLike(post.getId());
		manager.request(this, request, UN_LIKE_REQUEST);
		post.setLikeCount(post.getLikeCount() - 1);
	}

	
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		// TODO Auto-generated method stub
		switch (tag) {
		case LIKE_REQUEST:
			likeButton.setClickable(true);
		case UN_LIKE_REQUEST:
			likeButton.setClickable(true);
		}
		
		setInfo();
	}


	public void requestExceptionCallBack(int tag, MatjiException e) {
		// TODO Auto-generated method stub
		e.showToastMsg(getApplicationContext());
	}
}