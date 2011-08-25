package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.CommentAdapter;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.http.request.CommentHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.DisplayUtil;

public class CommentListView extends RequestableMListView {
	private HttpRequest request;
	private Session session;

	private int curDeletePos;

	private Post post;
	
	private PostHeader header;

	private static final int COMMENT_DELETE_REQUEST = 11;

	public CommentListView(Context context, AttributeSet attrs) {
		super(context, attrs, new CommentAdapter(context), 10);
		init();
	}

	private void init() {
		request = new CommentHttpRequest(getContext());
		session = Session.getInstance(getContext());

		setPage(1);
		setDivider(new ColorDrawable(Color.WHITE));
		setDividerHeight(DisplayUtil.PixelFromDP(1));
		setCanRepeat(true);
		setFadingEdgeLength(0);
		setSelector(new ColorDrawable(Color.TRANSPARENT));
		setCacheColorHint(Color.TRANSPARENT);
		
		header = new PostHeader(getContext());
		addHeaderView(header);
	}
	
	public void setPost(Post post) {
		this.post = post;
		header.setPost(post);
	}

	public void addComment(Comment comment) {
		getAdapterData().add(0, comment);
		((CommentAdapter) getMBaseAdapter()).notifyDataSetChanged();
		setSelection(0);
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

	public void onDeleteButtonClicked(View v) {
		if (session.isLogin() && !getHttpRequestManager().isRunning(getActivity())) {
			curDeletePos = Integer.parseInt((String) v.getTag());

			AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
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

	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch (tag) {
		case COMMENT_DELETE_REQUEST:
			getAdapterData().remove(curDeletePos);
			getMBaseAdapter().notifyDataSetChanged();
		}

		super.requestCallBack(tag, data);
	}
}