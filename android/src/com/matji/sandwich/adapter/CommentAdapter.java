package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.http.util.TimeStamp;
import com.matji.sandwich.widget.CommentListView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends MBaseAdapter {  
	MatjiImageDownloader downloader;
	
	public CommentAdapter(Context context) {
		super(context);
		downloader = new MatjiImageDownloader();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		CommentElement commentElement;
		Comment comment = (Comment) data.get(position);

		if (convertView == null) {
			commentElement = new CommentElement();
			convertView = getLayoutInflater().inflate(R.layout.adapter_comment, null);

			commentElement.image = (ImageView) convertView.findViewById(R.id.thumnail);
			commentElement.nick = (TextView) convertView.findViewById(R.id.comment_adapter_nick);
			commentElement.comment = (TextView) convertView.findViewById(R.id.comment_adapter_comment);
			commentElement.dateAgo = (TextView) convertView.findViewById(R.id.comment_adapter_created_at);
			convertView.setTag(commentElement);

			CommentListView commentListView = (CommentListView)parent;
			commentElement.nick.setOnClickListener(commentListView);
		} else {
			commentElement = (CommentElement) convertView.getTag();
		}

		commentElement.nick.setTag(position+"");

		/* Set User */
		User user = comment.getUser();
		downloader.downloadUserImage(user.getId(), MatjiImageDownloader.IMAGE_SSMALL, commentElement.image);
		commentElement.nick.setText(user.getNick());
		commentElement.comment.setText(comment.getComment());
		commentElement.dateAgo.setText(TimeStamp.getAgoFromDate(comment.getCreatedAt()));
	
		return convertView;
	}

	private class CommentElement {
		ImageView image;
		TextView nick;
		TextView comment;
		TextView dateAgo;
	}
}