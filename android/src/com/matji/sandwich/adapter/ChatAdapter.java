package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.DisplayUtil;

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
	
	// used to keep selected position in ListView
	private int selectedPos = -1;	// init value for not-selected

	public ChatAdapter(Context context) {
		super(context);
		session = Session.getInstance(context);
		myId = session.getCurrentUser().getId();
		downloader = new MatjiImageDownloader(context);
	}
	
	public void initSelectedPosition() {
		selectedPos = -1;
	}
	
	public void setSelectedPosition(int pos){
		selectedPos = pos;
		// inform the view of this change
		notifyDataSetChanged();
	}

	public int getSelectedPosition(){
		return selectedPos;
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
				messageElement.profile = (ImageView) convertView.findViewById(R.id.chat_adapter_other_profile);
				messageElement.message = (TextView) convertView.findViewById(R.id.chat_adapter_other_message);
				messageElement.message.setMaxWidth(DisplayUtil.DPFromPixel(width));
				break;
			}
			convertView.setTag(messageElement);
			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int position = Integer.parseInt((String) ((MessageElement) v.getTag()).message.getTag());
					setSelectedPosition(position);
				}
			});
			messageElement.message.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int position = Integer.parseInt((String) v.getTag());
					setSelectedPosition(position);
				}
			});
		} else {
			messageElement = (MessageElement) convertView.getTag();
		}

		if (getItemViewType(position) == OTHER) {
			User sentUser = message.getSentUser();
			downloader.downloadUserImage(sentUser.getId(), MatjiImageDownloader.IMAGE_SSMALL, messageElement.profile);
		}

		// set position for OnClickListener
		messageElement.message.setTag(position+"");
        // change the row color based on selected state		
        if (selectedPos == position) {
        	if (getItemViewType(position) == OTHER) {
        		messageElement.message.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.msg_bubble_other_pressed));
        	} else {
        		messageElement.message.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.msg_bubble_me_pressed));
        	}
        } else {
        	if (getItemViewType(position) == OTHER) {
        		messageElement.message.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.msg_bubble_other_default));
        	} else {
        		messageElement.message.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.msg_bubble_me_default));
        	}
        }
        
		messageElement.message.setText(message.getMessage());
        
		return convertView;
	}

	private class MessageElement {
		ImageView profile;
		TextView message;
	}
}