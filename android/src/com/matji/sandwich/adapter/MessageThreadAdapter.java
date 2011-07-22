package com.matji.sandwich.adapter;

import com.matji.sandwich.ChatActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.TimeUtil;
import com.matji.sandwich.widget.MessageThreadListView;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageThreadAdapter extends MBaseAdapter {
	private Context context;
	private MatjiImageDownloader downloader;
		
	public MessageThreadAdapter(Context context) {
		super(context);
		this.context = context;
		downloader = new MatjiImageDownloader(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		MessageElement messageElement;
		Message message = (Message) data.get(position);

		if (convertView == null) {
			messageElement = new MessageElement();
			convertView = getLayoutInflater().inflate(R.layout.adapter_message, null);

			messageElement.profile = (ImageView) convertView.findViewById(R.id.message_adapter_profile);
			messageElement.nick = (TextView) convertView.findViewById(R.id.message_adapter_nick);
			messageElement.message = (TextView) convertView.findViewById(R.id.message_adapter_message);
			messageElement.dateAgo = (TextView) convertView.findViewById(R.id.message_adapter_created_at);
			messageElement.delete = (Button) convertView.findViewById(R.id.delete_btn);

			MessageThreadListView threadListView = (MessageThreadListView) parent;
			messageElement.profile.setOnClickListener(threadListView);
			messageElement.nick.setOnClickListener(threadListView);
			messageElement.delete.setOnClickListener(threadListView);
			
			convertView.setTag(messageElement);
			
			messageElement.message.setLinksClickable(false);
			messageElement.message.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int position = Integer.parseInt((String) v.getTag());
					Message message = (Message) data.get(position);
					((BaseActivity) context).startActivityWithMatjiData(new Intent(context, ChatActivity.class), message);					
				}
			});
		} else {
			messageElement = (MessageElement) convertView.getTag();
		}

		/* Set User */
		User user = message.getSentUser();
		if (user.getId() == Session.getInstance(context).getCurrentUser().getId()) {
			user = message.getReceivedUser();
		}

		messageElement.profile.setTag(position+"");
		messageElement.message.setTag(position+"");
		messageElement.nick.setTag(position+"");
		messageElement.delete.setTag(position+"");

		downloader.downloadUserImage(user.getId(), MatjiImageDownloader.IMAGE_SSMALL, messageElement.profile);
		convertView.findViewById(R.id.message_adapter_thread).setVisibility(View.VISIBLE);
		convertView.findViewById(R.id.adapter_swipe_rear).setVisibility(View.GONE);
		
		messageElement.nick.setText(user.getNick());
		messageElement.message.setText(message.getMessage());
		messageElement.dateAgo.setText(TimeUtil.getAgoFromSecond(message.getAgo()));

		return convertView;
	}
	
	private class MessageElement {
		ImageView profile;
		TextView nick;
		TextView message;
		TextView dateAgo;
		Button delete;
	}
}