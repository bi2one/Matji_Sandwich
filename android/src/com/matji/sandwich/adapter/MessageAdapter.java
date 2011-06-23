package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.http.util.TimeUtil;
import com.matji.sandwich.session.Session;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageAdapter extends MBaseAdapter {
	MatjiImageDownloader downloader;

    public MessageAdapter(Context context) {
    	super(context);
    	downloader = new MatjiImageDownloader();
    }
	
	public View getView(int position, View convertView, ViewGroup parent) {
		MessageElement messageElement;
		Message message = (Message) data.get(position);
		
		if (convertView == null) {
			messageElement = new MessageElement();
			convertView = getLayoutInflater().inflate(R.layout.adapter_message, null);
			
			messageElement.thumnail = (ImageView) convertView.findViewById(R.id.message_adapter_thumnail);
			messageElement.nick = (TextView) convertView.findViewById(R.id.message_adapter_nick);
			messageElement.message = (TextView) convertView.findViewById(R.id.message_adapter_message);
			messageElement.dateAgo = (TextView) convertView.findViewById(R.id.message_adapter_created_at);
			convertView.setTag(messageElement);
			
//			PostListView postListView = (PostListView)parent;
//			postElement.thumnail.setOnClickListener(postListView);
//			postElement.nick.setOnClickListener(postListView);
		} else {
			messageElement = (MessageElement) convertView.getTag();
		}
		
		/* Set Store */
//		messageElement.thumnail.setTag(position+"");
//		messageElement.nick.setTag(position+"");
		
		/* Set User */
		User user = message.getSentUser();
		if (user.getId() == Session.getInstance(context).getCurrentUser().getId()) {
			user = message.getReceivedUser();
		}
		
		downloader.downloadUserImage(user.getId(), MatjiImageDownloader.IMAGE_SSMALL, messageElement.thumnail);
		messageElement.nick.setText(user.getNick());
		messageElement.message.setText(message.getMessage());
		messageElement.dateAgo.setText(TimeUtil.getAgoFromSecond(message.getAgo()));
		
		return convertView;
	}
	
    private class MessageElement {
    	ImageView thumnail;
    	TextView nick;
    	TextView message;
    	TextView dateAgo;
    }
}