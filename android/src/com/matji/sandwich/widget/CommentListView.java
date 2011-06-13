package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.R;
import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.adapter.CommentAdapter;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.request.CommentHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

public class CommentListView extends RequestableMListView implements View.OnClickListener {
	private HttpRequest request;
	private int post_id;

	public CommentListView(Context context, AttributeSet attrs) {
		super(context, attrs, new CommentAdapter(context), 10);
		request = new CommentHttpRequest(context);
		setPage(1);
		setFadingEdgeLength(0);
	}

	public void setPostId(int post_id) {
		this.post_id = post_id;
	}

	public HttpRequest request() {
		((CommentHttpRequest) request).actionList(post_id, getPage(), getLimit());
		return request;
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		super.requestCallBack(tag, data);
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		super.requestExceptionCallBack(tag, e);
	}

	public void onListItemClick(int position) {}

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.comment_adapter_thumnail: case R.id.comment_adapter_nick:
			gotoUserPage(Integer.parseInt((String)v.getTag()));
			break;
		}	
	}

	protected void gotoUserPage(int position) { 
		Comment comment = (Comment)getAdapterData().get(position);
		Intent intent = new Intent(getActivity(), UserTabActivity.class);

		intent.putExtra("user", (Parcelable)comment.getUser());
		getActivity().startActivity(intent);
	}
}