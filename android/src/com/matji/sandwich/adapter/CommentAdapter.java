package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.util.TimeUtil;
import com.matji.sandwich.widget.CommentListView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

			commentElement.image = (ImageView) convertView.findViewById(R.id.comment_adapter_thumnail);
			commentElement.nick = (TextView) convertView.findViewById(R.id.comment_adapter_nick);
			commentElement.comment = (TextView) convertView.findViewById(R.id.comment_adapter_comment);
			commentElement.dateAgo = (TextView) convertView.findViewById(R.id.comment_adapter_created_at);
			commentElement.delete = (Button) convertView.findViewById(R.id.delete_btn);
			convertView.setTag(commentElement);

			CommentListView commentListView = (CommentListView)parent;
			commentElement.image.setOnClickListener(commentListView);
			commentElement.nick.setOnClickListener(commentListView);
			commentElement.delete.setOnClickListener(commentListView);
		} else {
			commentElement = (CommentElement) convertView.getTag();
		}

		commentElement.image.setTag(position+"");
		commentElement.nick.setTag(position+"");
		commentElement.delete.setTag(position+"");
		
		convertView.findViewById(R.id.comment_adapter_wrap).setVisibility(View.VISIBLE);
		convertView.findViewById(R.id.adapter_swipe_rear).setVisibility(View.GONE);
		
		/* Set User */
		User user = comment.getUser();
		downloader.downloadUserImage(user.getId(), MatjiImageDownloader.IMAGE_SSMALL, commentElement.image);
		commentElement.nick.setText(user.getNick());
		commentElement.comment.setText(comment.getComment());
		commentElement.dateAgo.setText(TimeUtil.getAgoFromSecond(comment.getAgo()));
	
		return convertView;
	}

	private class CommentElement {
		ImageView image;
		TextView nick;
		TextView comment;
		TextView dateAgo;
		Button delete;
	}
}
