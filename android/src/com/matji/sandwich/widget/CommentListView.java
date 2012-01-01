package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
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
import com.matji.sandwich.widget.PostHeader.PostDeleteListener;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;
import com.matji.sandwich.widget.dialog.SimpleDialog;

public class CommentListView extends RequestableMListView {
	private HttpRequest request;
    private Session session;
    private Post post;
    private PostHeader header;
    private SimpleAlertDialog deleteDialog;

    private int curDeletePos;
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

    public void setPostDeleteListener(PostDeleteListener listener) {
        header.setPostDeleteListener(listener);
    }
    
    @Override
    public void setActivity(Activity activity) {
        deleteDialog = new SimpleAlertDialog(activity, R.string.default_string_check_delete);
        deleteDialog.setOnClickListener(new SimpleAlertDialog.OnClickListener() {
            @Override
            public void onConfirmClick(SimpleDialog dialog) {
                int comment_id= ((Comment) getAdapterData().get(curDeletePos)).getId();
                deleteComment(comment_id);
            }
        });
        super.setActivity(activity);
        header.setActivity(activity);
    }

    public void setPost(Post post) {
        this.post = post;
        header.setPost(post);
    }

    public void addComment(Comment comment) {
    	getAdapterData().add(getAdapterData().size(), comment);
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
        if (session.isLogin() && !getHttpRequestManager().isRunning()) {
            curDeletePos = Integer.parseInt((String) v.getTag());
            deleteDialog.show();
        }
    }

    public void deleteComment(int comment_id) {
        getHttpRequestManager().request(getContext(), getLoadingFooterView(), deleteRequest(comment_id), COMMENT_DELETE_REQUEST, this);
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