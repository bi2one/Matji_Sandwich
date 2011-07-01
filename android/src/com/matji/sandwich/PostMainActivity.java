package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

// TODO
// 댓글을 달거나, 라이크 했을 때 삭제 한 메모면.
// 1. 리스트뷰 리로드, 2. 리스트뷰 어댑터 item remove, notifyDataSetChanged

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
	
	private int position;

	private static final int POST_ID_IS_NULL = -1;
	private static final int POST_REQUEST = 0;
	private static final int LIKE_REQUEST = 3;
	private static final int UN_LIKE_REQUEST = 4;
	private static final int POST_DELETE_REQUEST = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_main);

		session = Session.getInstance(this);
		dbProvider = DBProvider.getInstance(this);
		manager = HttpRequestManager.getInstance(this);

		intent_post_id = getIntent().getIntExtra("post_id", POST_ID_IS_NULL);
		position = getIntent().getIntExtra("position", -1);
		
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
			if (post.getUserId() == session.getCurrentUser().getId()) {
				findViewById(R.id.post_main_delete_btn).setVisibility(View.VISIBLE);
			}
			if (dbProvider.isExistLike(post.getId(), "Post")) {
				likeButton.setText(getString(R.string.default_string_unlike));
			} else {
				likeButton.setText(getString(R.string.default_string_like));
			}
		} else {
			findViewById(R.id.post_main_delete_btn).setVisibility(View.GONE);
		}
	}

	private void commentListViewReload() {
		commentListView.getHeaderViewContainer().removeView(header.getRootView());
		commentListView.addHeaderView(header);
		commentListView.initItemVisible();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (intent_post_id == POST_ID_IS_NULL) {
//			commentListView.setCanRequestNext(true);
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

	public void postDoesNotExist() {
		Toast.makeText(this, R.string.post_does_not_exist, Toast.LENGTH_SHORT).show(); 
		super.finish();
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
		if (!manager.isRunning(this)) {
			PostHttpRequest postRequest = new PostHttpRequest(this);
			postRequest.actionDelete(post.getId());
			manager.request(this, postRequest, POST_DELETE_REQUEST, this);
		}
	}

	public void onCommentButtonClicked(View view) {
		if (loginRequired()) {
			if (!manager.isRunning(this)) {
				Intent intent = new Intent(getApplicationContext(), WriteCommentActivity.class);
				intent.putExtra("post_id", post.getId());
				startActivityForResult(intent, WRITE_COMMENT_ACTIVITY);
			}
		}
	}

	public void onLikeButtonClicked(View view) {
		if (loginRequired()) {
			if (!manager.isRunning(this)) {
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
	}

	private void postRequest(int post_id) {
		if (request == null || !(request instanceof PostHttpRequest)) {
			request = new PostHttpRequest(this);
		}
		((PostHttpRequest) request).actionShow(post_id);
		manager.request(this, request, POST_REQUEST, this);
	}

	private void likeRequest() {
		if (request == null || !(request instanceof LikeHttpRequest)) {
			request = new LikeHttpRequest(this);
		}
		((LikeHttpRequest) request).actionPostLike(post.getId());
		manager.request(this, request, LIKE_REQUEST, this);
		post.setLikeCount(post.getLikeCount() + 1);
	}

	private void unlikeRequest() {
		if (request == null || !(request instanceof LikeHttpRequest)) {
			request = new LikeHttpRequest(this);
		}

		((LikeHttpRequest) request).actionPostUnLike(post.getId());
		manager.request(this, request, UN_LIKE_REQUEST, this);
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
			Log.d("Matji","A");
			if (data != null && data.size() > 0) {
				Log.d("Matji", data.size()+"");
				Post post = (Post) data.get(0);
				SharedMatjiData.getInstance().push(post);
				intent_post_id = POST_ID_IS_NULL;
			} else {
				postDoesNotExist();
				return;
			}
			initInfo();
			onResume();
			break;
		case POST_DELETE_REQUEST:
			Intent intent = new Intent();
			intent.putExtra("position", position);
			setResult(RESULT_OK, intent);
			finish();
			break;
		}
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.showToastMsg(getApplicationContext());
	}
}