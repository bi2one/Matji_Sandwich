package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.R;
import com.matji.sandwich.SharedMatjiData;
import com.matji.sandwich.StoreMainActivity;
import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.adapter.CommentAdapter;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.CommentHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.Session;

public class CommentListView extends RequestableMListView implements View.OnClickListener {
	private Context context;
	private HttpRequest request;
	private Session session;

	private int curDeletePos;

//	private ListItemSwipeListener listener;
	private Post post;

	private static final int COMMENT_DELETE_REQUEST = 11;

	public CommentListView(Context context, AttributeSet attrs) {
		super(context, attrs, new CommentAdapter(context), 10);
		this.context = context;
		request = new CommentHttpRequest(context);
		session = Session.getInstance(context);

		init();
	}

	private void init() {
		post = (Post) SharedMatjiData.getInstance().top();
		setPage(1);
		setDivider(null);
		setCanRepeat(true);
		setBackgroundDrawable(getResources().getDrawable(R.drawable.comment_bg));
	}

	public void addComment(Comment comment) {
		getAdapterData().add(0, comment);
		((CommentAdapter) getMBaseAdapter()).notifyDataSetChanged();
	}

	public HttpRequest request() {
		((CommentHttpRequest) request).actionList(post.getId(), getPage(), getLimit());
		return request;
	}

	public HttpRequest deleteRequest(int comment_id) {
		((CommentHttpRequest) request).actionDelete(comment_id);
		return request;
	}

	public void onListItemClick(int position) {}

	public void onClick(View v) {
		int position = Integer.parseInt((String) v.getTag());

		switch(v.getId()){
		case R.id.thumnail:
			if (position == 0) {
				Post post = (Post) getAdapterData().get(position);
				gotoUserPage(post.getUser());
				break;
			}
		case R.id.row_comment_nick:
			Comment comment = (Comment) getAdapterData().get(position);
			gotoUserPage(comment.getUser());
			break;
		case R.id.row_post_nick:
			Post post = (Post) getAdapterData().get(position);
			gotoUserPage(post.getUser());
			break;
		case R.id.row_post_store_name:
			post = (Post) getAdapterData().get(position);
			gotoStorePage(post.getStore());
			break;
		}
	}

	public void onDeleteButtonClicked(View v) {
		if (session.isLogin() && !getHttpRequestManager().isRunning(getActivity())) {
			curDeletePos = Integer.parseInt((String) v.getTag());

			AlertDialog.Builder alert = new AlertDialog.Builder(context);
			alert.setTitle(R.string.default_string_delete);
			alert.setMessage(R.string.default_string_check_delete);
			alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					int comment_id= ((Comment) getAdapterData().get(curDeletePos)).getId();
					deleteComment(comment_id);
				}
			}); 
			alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {}
			});
			alert.show();
		}
	}

	public void deleteComment(int comment_id) {
		getHttpRequestManager().request(getActivity(), deleteRequest(comment_id), COMMENT_DELETE_REQUEST, this);
	}

	protected void gotoUserPage(User user) { 
		Intent intent = new Intent(getActivity(), UserTabActivity.class);
		((BaseActivity) getActivity()).startActivityWithMatjiData(intent, user);
	}
	
	protected void gotoStorePage(Store store) { 
		Intent intent = new Intent(getActivity(), StoreMainActivity.class);
		((BaseActivity) getActivity()).startActivityWithMatjiData(intent, store);
	}

	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch (tag) {
		case COMMENT_DELETE_REQUEST:
			getAdapterData().remove(curDeletePos);
			getMBaseAdapter().notifyDataSetChanged();
			Post post = (Post) SharedMatjiData.getInstance().top();
			post.setCommentCount(post.getCommentCount() - 1);
		}

		if (tag == REQUEST_RELOAD && data != null) {
			data.add(0, post);
		}
		super.requestCallBack(tag, data);
	}
}