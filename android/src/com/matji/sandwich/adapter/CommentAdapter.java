package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.data.User;
import com.matji.sandwich.listener.GotoUserMainAction;
import com.matji.sandwich.util.TimeUtil;
import com.matji.sandwich.widget.ProfileImageView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CommentAdapter extends MBaseAdapter {

	public CommentAdapter(Context context) {
		super(context);
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
			
			commentElement.profile.showInsetBackground();
			
			convertView.setTag(commentElement);

			setOnClickListener(commentElement, position);
		} else {
			commentElement = (CommentElement) convertView.getTag();
		}
		
		setViewItemPosition(commentElement, position);
//		convertView.findViewById(R.id.row_comment_wrapper).setVisibility(View.VISIBLE);

		setViewData(commentElement, position);

		return convertView;
	}

	private void setOnClickListener(CommentElement holder, int position) {
		GotoUserMainAction action1 = new GotoUserMainAction(context, ((Comment) data.get(position)).getUser());

		holder.profile.setOnClickListener(action1);
		holder.nick.setOnClickListener(action1);
	}

	private void setViewItemPosition(CommentElement holder, int position) {
		holder.profile.setTag(position+"");
		holder.nick.setTag(position+"");
	}

	private void setViewData(CommentElement holder, int position) {
		Comment comment = (Comment) data.get(position);
		User user = comment.getUser();
		holder.profile.setUserId(user.getId());
		holder.nick.setText(user.getNick());
		holder.dateAgo.setText(TimeUtil.getAgoFromSecond(comment.getAgo()));
		holder.comment.setText(comment.getComment());
	}
	
	class CommentElement {
		ProfileImageView profile;
		TextView nick;
		TextView comment;
		TextView dateAgo;
	}
}