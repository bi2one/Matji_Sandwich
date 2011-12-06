package com.matji.sandwich.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.CommentHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.spinner.SpinnerFactory.SpinnerType;
import com.matji.sandwich.listener.GotoUserMainAction;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.TimeUtil;
import com.matji.sandwich.widget.ProfileImageView;
import com.matji.sandwich.widget.dialog.SimpleConfirmDialog;
import com.matji.sandwich.widget.dialog.SimpleDialog;

public class CommentAdapter extends MBaseAdapter implements Requestable {
    private Session session;
    private SimpleConfirmDialog checkDeleteDialog;
    private HttpRequestManager manager;
    private HttpRequest request;
    private static final int INVALID = -1;
    private int currDeletedPos;
    private ViewGroup currSpinner;
    
	public CommentAdapter(Context context) {
		super(context);
		session = Session.getInstance(context);
		manager = HttpRequestManager.getInstance();
		checkDeleteDialog = new SimpleConfirmDialog(context, R.string.default_string_check_delete);
		checkDeleteDialog.setOnClickListener(new SimpleConfirmDialog.OnClickListener() {
            
            @Override
            public void onConfirmClick(SimpleDialog dialog) {
                delete(currSpinner, (Comment) data.get(currDeletedPos));
            }
            
            @Override
            public void onCancelClick(SimpleDialog dialog) {
                currDeletedPos = INVALID;
                currSpinner = null;
            }
        });
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommentElement commentElement = null;

		if (convertView == null) {
			commentElement = new CommentElement();
			convertView = getLayoutInflater().inflate(R.layout.row_comment, null);

			commentElement.profile = (ProfileImageView) convertView.findViewById(R.id.profile);
			commentElement.nick = (TextView) convertView.findViewById(R.id.row_comment_nick);
			commentElement.dateAgo = (TextView) convertView.findViewById(R.id.row_comment_created_at);
			commentElement.comment = (TextView) convertView.findViewById(R.id.row_comment_comment);
            commentElement.spinner = (ViewGroup) convertView.findViewById(R.id.row_comment_delete_spinner);
            commentElement.delete = (ImageButton) convertView.findViewById(R.id.row_comment_delete_btn);
			commentElement.profile.showInsetBackground();
			
			convertView.setTag(commentElement);
		} else {
			commentElement = (CommentElement) convertView.getTag();
		}

        setOnClickListener(commentElement, position);
		setViewItemPosition(commentElement, position);
		setViewData(commentElement, position);

		return convertView;
	}

	private void setOnClickListener(final CommentElement holder, final int position) {
		GotoUserMainAction action1 = new GotoUserMainAction(context, ((Comment) data.get(position)).getUser());

		holder.profile.setOnClickListener(action1);
		holder.nick.setOnClickListener(action1);
		holder.delete.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                currDeletedPos = position;
                currSpinner = holder.spinner;
                checkDeleteDialog.show();
            }
        });
	}

	private void delete(ViewGroup spinner, Comment comment) {
	    if (request == null || !(request instanceof CommentHttpRequest)) {
	        request = new CommentHttpRequest(context);
	    }
	    ((CommentHttpRequest) request).actionDelete(comment.getId());
	    
	    manager.request(context, spinner, SpinnerType.SSMALL, request, HttpRequestManager.COMMENT_DELETE_REQUEST, this);
	}
	
	private void setViewItemPosition(CommentElement holder, int position) {
		holder.profile.setTag(position+"");
		holder.nick.setTag(position+"");
	}

	private void setViewData(final CommentElement holder, int position) {
		Comment comment = (Comment) data.get(position);
		User user = comment.getUser();
		holder.profile.setUserId(user.getId());
		holder.nick.setText(user.getNick());
		holder.dateAgo.setText(TimeUtil.getAgoFromSecond(comment.getAgo()));
		holder.comment.setText(comment.getComment());
		if (session.isLogin() && session.isCurrentUser(comment.getUser())) {
			holder.delete.setVisibility(View.VISIBLE);
        } else {
        	holder.delete.setVisibility(View.GONE);
        }
	}
	
	class CommentElement {
		ProfileImageView profile;
		TextView nick;
		TextView comment;
		TextView dateAgo;
		ViewGroup spinner;
		ImageButton delete;
	}

    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch (tag) {
        case HttpRequestManager.COMMENT_DELETE_REQUEST:
            if (currDeletedPos != INVALID) {
                this.data.remove(currDeletedPos);
                notifyDataSetChanged();
            }
            currDeletedPos = INVALID;
            currSpinner = null;
            break;
        }
    }

    @Override
    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.performExceptionHandling(context);
    }
}