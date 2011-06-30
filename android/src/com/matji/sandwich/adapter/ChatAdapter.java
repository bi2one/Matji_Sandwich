package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.DisplayUtil;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.session.Session;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatAdapter extends MBaseAdapter {
	private Session session;
	private MatjiImageDownloader downloader;

	private int myId;

	private final int VIEW_TYPE_COUNT = 2; 
	private final int ME = 0;
	private final int OTHER = 1;

	public ChatAdapter(Context context) {
		super(context);
		session = Session.getInstance(context);
		myId = session.getCurrentUser().getId();
		downloader = new MatjiImageDownloader();
	}
	
	@Override
	public int getItemViewType(int position) {
		Message message = (Message) data.get(position);
		return (message.getSentUser().getId() == myId) ? ME : OTHER;
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT; // ME, OTHER
	};

	public View getView(int position, View convertView, ViewGroup parent) {
		MessageElement messageElement;
		Message message = (Message) data.get(position);

		if (convertView == null) {
			messageElement = new MessageElement();
			int width = (context.getResources().getDisplayMetrics().widthPixels / 3) * 2;
			switch (getItemViewType(position)) {
			case ME:
				convertView = getLayoutInflater().inflate(R.layout.adapter_chat_me, null);
				messageElement.message = (TextView) convertView.findViewById(R.id.chat_adapter_me_message);
				messageElement.message.setMaxWidth(DisplayUtil.DPFromPixel(width));
				break;
			case OTHER:
				convertView = getLayoutInflater().inflate(R.layout.adapter_chat_other, null);
				messageElement.thumnail = (ImageView) convertView.findViewById(R.id.chat_adapter_other_thumnail);
				messageElement.message = (TextView) convertView.findViewById(R.id.chat_adapter_other_message);
				messageElement.message.setMaxWidth(DisplayUtil.DPFromPixel(width));
				break;
			}
			convertView.setTag(messageElement);
		} else {
			messageElement = (MessageElement) convertView.getTag();
		}

		if (getItemViewType(position) == OTHER) {
			User sentUser = message.getSentUser();
			downloader.downloadUserImage(sentUser.getId(), MatjiImageDownloader.IMAGE_SSMALL, messageElement.thumnail);
		}
		
		messageElement.message.setText(message.getMessage());

		return convertView;
	}

	private class MessageElement {
		ImageView thumnail;
		TextView message;
		//		TextView dateAgo;
	}
}