package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.data.Like;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.CommentHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.LikeHttpRequest;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.CommentInputBar;
import com.matji.sandwich.widget.CommentListView;
import com.matji.sandwich.widget.title.PageableTitle;
import com.matji.sandwich.widget.title.PageableTitle.Pageable;

// TODO
// 댓글을 달거나, 라이크 했을 때 삭제 한 메모면.
// 1. 리스트뷰 리로드, 2. 리스트뷰 어댑터 item remove, notifyDataSetChanged

public class PostActivity extends BaseActivity implements Requestable, Pageable, OnClickListener {
	private ArrayList<MatjiData> posts;
	private int position;
	private Post currentPost;
	private DBProvider dbProvider;

	private boolean showKeyboard;

	private HttpRequest request;
	private HttpRequestManager manager;

	private PageableTitle pageableTitle;
	private CommentListView commentListView;
	private CommentInputBar commentInputBar;

	private Toast toast;

	private static final int COMMENT_WRITE_REQUEST = 10;

	public static final String SHOW_KEYBOARD = "show_keyboard"; 
	public static final String POSTS = "posts";
	public static final String POSITION = "position";
	private static final int NOT_POSITIONED = -1;
	private static final int LIKE_REQUEST = 11;
	private static final int UN_LIKE_REQUEST = 12;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		intent_post_id = getIntent().getIntExtra("post_id", POST_ID_IS_NULL);

		//		if (intent_post_id == POST_ID_IS_NULL) {
		//		} else {
		//			postRequest(intent_post_id);
		//		}
	}

	protected void init() {
		super.init();
		setContentView(R.layout.activity_post);

		toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		dbProvider = DBProvider.getInstance(this);
		manager = HttpRequestManager.getInstance(this);

		posts = getIntent().getParcelableArrayListExtra(POSTS);
		position = getIntent().getIntExtra(POSITION, NOT_POSITIONED);
		if (!(position == NOT_POSITIONED)) {
			currentPost = (Post) posts.get(position);
		}
		showKeyboard = getIntent().getBooleanExtra(SHOW_KEYBOARD, false);
		pageableTitle = (PageableTitle) findViewById(R.id.Titlebar);
		pageableTitle.setPageable(this);
		pageableTitle.setTitle(R.string.default_string_post);
		commentListView = (CommentListView) findViewById(R.id.comment_list);
		commentListView.setPost((Post) posts.get(position));
		commentListView.setActivity(this);
		commentListView.requestReload();

		commentInputBar = (CommentInputBar) findViewById(R.id.comment_input_bar);
		if (showKeyboard) {
			commentInputBar.requestFocusToTextField();
		}
		commentInputBar.setLikeListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		//		if (intent_post_id == POST_ID_IS_NULL) {
		//			commentListView.setCanRequestNext(true);
		//			setInfo();
		//		}
	}


	
	public void onConfirmButtonClicked(View v) {
		if (loginRequired() && !manager.isRunning(this)) {
			if (request == null || !(request instanceof CommentHttpRequest)) {
				request = new CommentHttpRequest(getApplicationContext());
			}

			if(commentInputBar.getText().trim().equals("")) {
				Toast.makeText(getApplicationContext(), R.string.writing_content_comment, Toast.LENGTH_SHORT).show();
			} else {
				((CommentHttpRequest) request).actionNew(((Post) posts.get(position)).getId(), commentInputBar.getText().trim(), MatjiConstants.string(R.string.android));
				manager.request(this, request, COMMENT_WRITE_REQUEST, this);
				commentInputBar.setText("");
				KeyboardUtil.hideKeyboard(this);
			}
		}
	}

	public void onLikeButtonClicked() {
		if (loginRequired()) {
			if (!manager.isRunning(this)) {
//				likeButton.setClickable(false);
				pageableTitle.lock();
				if (dbProvider.isExistLike(currentPost.getId(), DBProvider.POST)){
					// api request
					unlikeRequest();
				}else {
					// api request
					likeRequest();
				}
			}
		}
	}

	private void likeRequest() {
		if (request == null || !(request instanceof LikeHttpRequest)) {
			request = new LikeHttpRequest(this);
		}
		((LikeHttpRequest) request).actionPostLike(currentPost.getId());
		manager.request(this, request, LIKE_REQUEST, this);
	}

	private void unlikeRequest() {
		if (request == null || !(request instanceof LikeHttpRequest)) {
			request = new LikeHttpRequest(this);
		}
		((LikeHttpRequest) request).actionPostUnLike(currentPost.getId());
		manager.request(this, request, UN_LIKE_REQUEST, this);
	}


	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		// TODO Auto-generated method stub
		pageableTitle.unlock();

		switch (tag) {
		case LIKE_REQUEST:
			postLikeRequest();
			break;
		case UN_LIKE_REQUEST:
			postUnlikeRequest();
			break;
		case COMMENT_WRITE_REQUEST:
			if (data != null && data.size() > 0) {
				Comment newComment = (Comment) data.get(0);
				commentListView.addComment(newComment);
			} else {
				Log.d("Matji", "데이터가 없음??");
			}
		}
	}
	
	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.showToastMsg(getApplicationContext());
	}


	private void postLikeRequest() {
		Like like = new Like();
		like.setForeignKey(currentPost.getId());
		like.setObject("Post");
		dbProvider.insertLike(like);
		currentPost.setLikeCount(currentPost.getLikeCount() + 1);
		commentListView.setPost(currentPost);
		commentListView.dataRefresh();
	}
	
	private void postUnlikeRequest() {
		dbProvider.deleteLike(currentPost.getId(), "Post");
		currentPost.setLikeCount(currentPost.getLikeCount() - 1);
		commentListView.setPost(currentPost);
		commentListView.dataRefresh();
	}
	
	public String getCount(int id, int count) {
		return getString(id) + ": " + count;
	}

	public String getCountNumberOf(int id, int count) {
		return getString(id) + ": " + count + getString(R.string.default_string_number_of);
	}

	@Override
	public void prev() {
		if (position-1 < 0) {
			toast.setText(MatjiConstants.string(R.string.does_not_exist_prev_post));
			toast.show();
		}
		else {
			manager.cancelTask();
			currentPost = (Post) posts.get(--position);
			commentListView.clearAdapter();
			commentListView.setPost(currentPost);
			commentListView.requestReload();
		}
	}

	@Override
	public void next() {
		if (position+1 >= posts.size()) {
			toast.setText(MatjiConstants.string(R.string.does_not_exist_next_post));
			toast.show();
		} else {
			manager.cancelTask();
			currentPost = (Post) posts.get(++position);
			commentListView.setPost(currentPost);
			commentListView.clearAdapter();
			commentListView.requestReload();
		}
	}

	@Override
	public void onClick(View v) {
		Object tag = v.getTag();
		if (tag != null && tag instanceof Integer) {
			if (((Integer) tag).intValue() == CommentInputBar.LIKE_BUTTON) {
				onLikeButtonClicked();
			}
		}
	}
}