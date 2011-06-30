package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.R;
import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.adapter.CommentAdapter;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.http.request.CommentHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.listener.ListItemSwipeListener;
import com.matji.sandwich.session.Session;

public class CommentListView extends RequestableMListView implements View.OnClickListener {
	private HttpRequest request;
	private Session session;

	private int post_id;
	private int curDeletePos;

	private ListItemSwipeListener listener;

	private static final int COMMENT_DELETE_REQUEST = 11;

	public CommentListView(Context context, AttributeSet attrs) {
		super(context, attrs, new CommentAdapter(context), 10);
		request = new CommentHttpRequest(context);
		session = Session.getInstance(context); 

		setPage(1);

		listener = new ListItemSwipeListener(context, this, R.id.comment_adapter_wrap, R.id.adapter_swipe_rear, 1) {
			@Override
			public void onListItemClicked(int position) {}

			@Override
			public boolean isMyItem(int position) {
				return session.isLogin() && ((Comment) getAdapterData().get(position)).getUserId() == session.getCurrentUser().getId();
			}
		};

		setOnTouchListener(listener);
		setCanRepeat(true);
	}

	public void setPostId(int post_id) {
		this.post_id = post_id;
	}

	public void addComment(Comment comment) {
		getAdapterData().add(0, comment);
		((CommentAdapter) getMBaseAdapter()).notifyDataSetChanged();
	}

	public HttpRequest request() {
		((CommentHttpRequest) request).actionList(post_id, getPage(), getLimit());
		return request;
	}

	public HttpRequest deleteRequest(int comment_id) {
		((CommentHttpRequest) request).actionDelete(comment_id);
		return request;
	}

	public void onListItemClick(int position) {}

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.comment_adapter_thumnail: case R.id.comment_adapter_nick:
			gotoUserPage(Integer.parseInt((String)v.getTag()));
			break;
		case R.id.delete_btn:
			if (session.isLogin() && !getHttpRequestManager().isRunning(getActivity())) {
				curDeletePos = Integer.parseInt((String) v.getTag());
				Comment comment = (Comment) getAdapterData().get(curDeletePos);
				getHttpRequestManager().request(getActivity(), deleteRequest(comment.getId()), COMMENT_DELETE_REQUEST, this);
			}
			break;
		}	
	}

	protected void gotoUserPage(int position) { 
		Comment comment = (Comment)getAdapterData().get(position);
		Intent intent = new Intent(getActivity(), UserTabActivity.class);
		((BaseActivity) getActivity()).startActivityWithMatjiData(intent, comment.getUser());
	}

	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch (tag) {
		case COMMENT_DELETE_REQUEST:
			initItemVisible();
			getAdapterData().remove(curDeletePos);
			((CommentAdapter) getMBaseAdapter()).notifyDataSetChanged();
		}

		super.requestCallBack(tag, data);
	}
	
	public void initItemVisible() {
		listener.initItemVisible();
	}
}
