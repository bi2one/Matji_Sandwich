package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.CommentHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.CommentInputBar;
import com.matji.sandwich.widget.CommentListView;

// TODO
// 댓글을 달거나, 라이크 했을 때 삭제 한 메모면.
// 1. 리스트뷰 리로드, 2. 리스트뷰 어댑터 item remove, notifyDataSetChanged

public class CommentActivity extends BaseActivity implements Requestable {
	private ArrayList<MatjiData> posts;
	private int position;
	
	private int intent_post_id;

	private Session session;
	private DBProvider dbProvider;
	
	private boolean showKeyboard;
	
	private HttpRequest request;
	private HttpRequestManager manager;

	private CommentListView commentListView;
	private CommentInputBar commentInputBar;
	
//	private Button likeButton;

//	public static final int POST_ID_IS_NULL = -1;
	
	private static final int COMMENT_WRITE_REQUEST = 10;

	public static final String SHOW_KEYBOARD = "show_keyboard"; 
	public static final String POSTS = "posts";
	public static final String POSITION = "position";
//	private static final int POST_REQUEST = 11;
//	private static final int LIKE_REQUEST = 12;
//	private static final int UN_LIKE_REQUEST = 13;
//	private static final int POST_DELETE_REQUEST = 14;
	
	
	
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
		setContentView(R.layout.activity_comment);

		session = Session.getInstance(this);
		dbProvider = DBProvider.getInstance(this);
		manager = HttpRequestManager.getInstance(this);

		posts = getIntent().getParcelableArrayListExtra(POSTS);
		position = getIntent().getIntExtra(POSITION, -1);
		showKeyboard = getIntent().getBooleanExtra(SHOW_KEYBOARD, false);
//		if (post.getId() == POST_ID_IS_NULL) postDoesNotExist();
		commentListView = (CommentListView) findViewById(R.id.comment_list);
//		likeButton = (Button) findViewById(R.id.post_main_like_btn);
		commentListView.setPost((Post) posts.get(position));
		commentListView.setActivity(this);
		commentListView.requestReload();
		
		commentInputBar = (CommentInputBar) findViewById(R.id.comment_input_bar);
		if (showKeyboard) {
			commentInputBar.requestFocusToTextField();
		}
	}

//	private void setInfo() {
//		if (session.isLogin()) {
//			if (post.getUserId() == session.getCurrentUser().getId()) {
//				findViewById(R.id.post_main_delete_btn).setVisibility(View.VISIBLE);
//			}
//			if (dbProvider.isExistLike(post.getId(), "Post")) {
//				likeButton.setText(getString(R.string.default_string_unlike));
//			} else {
//				likeButton.setText(getString(R.string.default_string_like));
//			}
//		} else {
//			findViewById(R.id.post_main_delete_btn).setVisibility(View.GONE);
//		}
//	}

	@Override
	public void onResume() {
		super.onResume();
//		if (intent_post_id == POST_ID_IS_NULL) {
//			commentListView.setCanRequestNext(true);
//			setInfo();
//		}
	}

//	public void postDoesNotExist() {
//		Toast.makeText(this, R.string.post_does_not_exist, Toast.LENGTH_SHORT).show(); 
//		super.finish();
//	}

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
//	
//	public void onDeleteButtonClicked(View v) {
//		if (!manager.isRunning(this)) {
//			AlertDialog.Builder alert = new AlertDialog.Builder(this);
//			alert.setTitle(R.string.default_string_delete);
//			alert.setMessage(R.string.default_string_check_delete);
//			alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface arg0, int arg1) {
//					deletePost();
//				}
//			}); 
//			alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface arg0, int arg1) {}
//			});
//			alert.show();
//		}
//	}
//
//	public void deletePost() {
//		PostHttpRequest postRequest = new PostHttpRequest(this);
//		postRequest.actionDelete(post.getId());
//		manager.request(this, postRequest, POST_DELETE_REQUEST, this);
//
//	}
//
//	public void onLikeButtonClicked(View view) {
//		if (loginRequired()) {
//			if (!manager.isRunning(this)) {
////				likeButton.setClickable(false);
//				if (dbProvider.isExistLike(post.getId(), "Post")){
//					// api request
//					unlikeRequest();
//				}else {
//					// api request
//					likeRequest();
//				}
//			}
//		}
//	}
//
//	private void likeRequest() {
//		if (request == null || !(request instanceof LikeHttpRequest)) {
//			request = new LikeHttpRequest(this);
//		}
//		((LikeHttpRequest) request).actionPostLike(post.getId());
//		manager.request(this, request, LIKE_REQUEST, this);
//		post.setLikeCount(post.getLikeCount() + 1);
//	}
//
//	private void unlikeRequest() {
//		if (request == null || !(request instanceof LikeHttpRequest)) {
//			request = new LikeHttpRequest(this);
//		}
//
//		((LikeHttpRequest) request).actionPostUnLike(post.getId());
//		manager.request(this, request, UN_LIKE_REQUEST, this);
//		post.setLikeCount(post.getLikeCount() - 1);
//	}


	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		// TODO Auto-generated method stub
		switch (tag) {
//		case LIKE_REQUEST:
//			Like like = new Like();
//			like.setForeignKey(post.getId());
//			like.setObject("Post");
//			dbProvider.insertLike(like);
//			setInfo();
//			break;
//		case UN_LIKE_REQUEST:
//			dbProvider.deleteLike(post.getId(), "Post");
//			setInfo();
//			break;
//		case POST_REQUEST:
//			if (data != null && data.size() > 0) {
//				Post post = (Post) data.get(0);
////				intent_post_id = POST_ID_IS_NULL;
//			} else {
//				postDoesNotExist();
//				return;
//			}
//			initInfo();
//			onResume();
//			break;
//		case POST_DELETE_REQUEST:
//			Intent intent = new Intent();
//			intent.putExtra("position", position);
//			setResult(RESULT_OK, intent);
//			finish();
//			break;
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
	

	public String getCount(int id, int count) {
		return getString(id) + ": " + count;
	}
	
	public String getCountNumberOf(int id, int count) {
		return getString(id) + ": " + count + getString(R.string.default_string_number_of);
	}
}