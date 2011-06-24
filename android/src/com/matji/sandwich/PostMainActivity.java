package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.matji.sandwich.data.Comment;
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
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.CommentListView;
import com.matji.sandwich.widget.PostViewContainer;

public class PostMainActivity extends MainActivity implements Requestable {
	private Post post;
	private User user;
	private Store store;
	private int intent_post_id;

	private Session session;
	private DBProvider dbProvider;
	private HttpRequest request;
	private HttpRequestManager manager;

	private PostViewContainer header;
	private CommentListView commentListView;
	private Button likeButton;

	private static final int POST_ID_IS_NULL = -1;
	private static final int POST_REQUEST = 0;
	private static final int LIKE_REQUEST = 3;
	private static final int UN_LIKE_REQUEST = 4;
	private static final int POST_DELETE_REQUEST = 5;
	private static final int COMMENT_DELETE_REQUEST = 6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_main);

		session = Session.getInstance(this);
		dbProvider = DBProvider.getInstance(this);
		manager = new HttpRequestManager(this, this);

		intent_post_id = getIntent().getIntExtra("post_id", POST_ID_IS_NULL);
		if (intent_post_id == POST_ID_IS_NULL) {
			initInfo();
		} else {
			postRequest(intent_post_id);
		}
	}

	private void initInfo() {
		post = (Post) SharedMatjiData.getInstance().top();
		user = post.getUser();
		store = post.getStore();

		header = new PostViewContainer(this, this, post, user, store);
		commentListView = (CommentListView) findViewById(R.id.post_main_comment_list);
		likeButton = (Button) findViewById(R.id.post_main_like_btn);

		commentListView.setPostId(post.getId());
		commentListView.setActivity(this);
		commentListView.requestReload();
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
	}

	@Override
	public void onResume() {
		super.onResume();
		if (intent_post_id == POST_ID_IS_NULL) {
			setInfo();
			commentListViewReload();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case WRITE_COMMENT_ACTIVITY:
			if (resultCode == RESULT_OK) {
				Comment comment = (Comment) data.getParcelableExtra("comment");
				if (comment != null) {
					comment.setUser(session.getCurrentUser());
					commentListView.addComment(comment);
				}

				post.setCommentCount(post.getCommentCount() + 1);
			}
		}
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


	public void onDeleteButtonClicked(View v) {
		PostHttpRequest postRequest = new PostHttpRequest(this);
		postRequest.actionDelete(post.getId());
		manager.request(this, postRequest, POST_DELETE_REQUEST);
	}


	public void onCommentButtonClicked(View view) {
		if (loginRequired()) {	
			Intent intent = new Intent(getApplicationContext(), WriteCommentActivity.class);
			intent.putExtra("post_id", post.getId());
			startActivityForResult(intent, WRITE_COMMENT_ACTIVITY);
		}
	}

	public void onLikeButtonClicked(View view) {
		if (loginRequired()) {
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

	private void postRequest(int post_id) {
		if (request == null || !(request instanceof PostHttpRequest)) {
			request = new PostHttpRequest(this);
		}
		((PostHttpRequest) request).actionShow(post_id);
		manager.request(this, request, POST_REQUEST);
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
		case LIKE_REQUEST: case UN_LIKE_REQUEST:
			likeButton.setClickable(true);
			setInfo();
			break;
		case POST_REQUEST:
			if (data != null && data.size() > 0) {
				Post post = (Post) data.get(0);
				SharedMatjiData.getInstance().push(post);
				intent_post_id = POST_ID_IS_NULL;
			}

			initInfo();
			onResume();
			break;
		case POST_DELETE_REQUEST:
			setResult(RESULT_FIRST_USER);
			finish();
			break;
		case COMMENT_DELETE_REQUEST:
			break;
		}
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		// TODO Auto-generated method stub
		e.showToastMsg(getApplicationContext());
	}
}