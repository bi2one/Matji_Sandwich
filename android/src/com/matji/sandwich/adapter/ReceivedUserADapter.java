package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ReceivedUserADapter extends MBaseAdapter {
	private MatjiImageDownloader downloader;

	public ReceivedUserADapter(Context context) {
		super(context);
		downloader = new MatjiImageDownloader(context);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		ReceivedUserElement receivedUserElement;
		User following = (User) data.get(position);
		if (convertView == null) {
			receivedUserElement = new ReceivedUserElement();
			convertView = getLayoutInflater().inflate(R.layout.adapter_received_user, null);

			receivedUserElement.profile= (ImageView) convertView.findViewById(R.id.received_user_adapter_profile);
			receivedUserElement.nick = (TextView) convertView.findViewById(R.id.received_user_adapter_nick);
			convertView.setTag(receivedUserElement);
		} else {
			receivedUserElement = (ReceivedUserElement) convertView.getTag();
		}

		receivedUserElement.nick.setText(following.getNick());
		downloader.downloadUserImage(following.getId(), receivedUserElement.profile);
		
		return convertView;
	}

	private class ReceivedUserElement {
		ImageView profile;
		TextView nick;
	}
}