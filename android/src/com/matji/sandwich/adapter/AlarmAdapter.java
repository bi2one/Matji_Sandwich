package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Alarm;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.widget.AlarmListView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AlarmAdapter extends MBaseAdapter {
	private MatjiImageDownloader downloader;

	public AlarmAdapter(Context context) {
		super(context);
		downloader = new MatjiImageDownloader();
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		AlarmElement alarmElement;
		Alarm alarm = (Alarm) data.get(position);
		if (convertView == null) {
			alarmElement = new AlarmElement();
			convertView = getLayoutInflater().inflate(R.layout.adapter_alarm, null);

			alarmElement.image = (ImageView) convertView.findViewById(R.id.alarm_adapter_thumnail);
			alarmElement.nick = (TextView) convertView.findViewById(R.id.alarm_adapter_nick);
			alarmElement.message = (TextView) convertView.findViewById(R.id.alarm_adapter_message);
			convertView.setTag(alarmElement);

			AlarmListView alarmListView = (AlarmListView)parent;
			alarmElement.image.setOnClickListener(alarmListView);
			alarmElement.nick.setOnClickListener(alarmListView);
		} else {
			alarmElement = (AlarmElement) convertView.getTag();
		}

		alarmElement.image.setTag(position+"");
		alarmElement.nick.setTag(position+"");

		/* Set User */
		User sentUser = alarm.getSentUser();
		String type = alarm.getAlarmType();

		alarmElement.nick.setText(sentUser.getNick() + " ");

		downloader.downloadUserImage(sentUser.getId(), alarmElement.image);
		
		if (type.equals("Following")) {
			alarmElement.message.setText(R.string.alarm_following_you);
		} else if (type.equals("Comment")) {
			alarmElement.message.setText(R.string.alarm_commented_post);
		} else if (type.equals("PostLike")) {
			alarmElement.message.setText(R.string.alarm_like_post);
		}

		return convertView;
	}

	private class AlarmElement {
		ImageView image;
		TextView nick;
		TextView message;
	}
}