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
		};

		setOnTouchListener(listener);
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
			// TODO !!TEST!!
			// 우선은 이곳에서 세션과 비교해 댓글 삭제 여부 결정. 
			// 후에 swipe를 자기 댓글만 가능하게 할 것인지 결정해서 수정.
			if (session.isLogin()) {
				curDeletePos = Integer.parseInt((String) v.getTag());
				Comment comment = (Comment) getAdapterData().get(curDeletePos);
				getHttpRequestManager().request(getActivity(), deleteRequest(comment.getId()), COMMENT_DELETE_REQUEST);
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
			// TODO !!TEST!!
			// 이 부분도 나중에 수정할게 있으면 같이.
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
