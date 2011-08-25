package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Notice;
import com.matji.sandwich.util.TimeUtil;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MessageAdapter extends MBaseAdapter {
	private int selectedNoticeId;	
	
	public MessageAdapter(Context context) {
		super(context);
		this.context = context;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		NoticeElement noticeElement;
		Notice notice = (Notice) data.get(position);

		if (convertView == null) {
			noticeElement = new NoticeElement();
			convertView = getLayoutInflater().inflate(R.layout.adapter_notice, null);

			noticeElement.subject = (TextView) convertView.findViewById(R.id.notice_adapter_subject);
			noticeElement.notice = (TextView) convertView.findViewById(R.id.notice_adapter_notice);
			noticeElement.dateAgo = (TextView) convertView.findViewById(R.id.notice_adapter_date_ago);
			
			convertView.setTag(noticeElement);
			
			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (v.findViewById(R.id.notice_adapter_notice).getVisibility() == View.GONE)
						v.findViewById(R.id.notice_adapter_notice).setVisibility(View.VISIBLE);
					else
						v.findViewById(R.id.notice_adapter_notice).setVisibility(View.GONE);
				}
			});
		} else {
			noticeElement = (NoticeElement) convertView.getTag();
		}

		noticeElement.subject.setText(notice.getSubject());
		noticeElement.notice.setText(notice.getContent());
		noticeElement.dateAgo.setText(TimeUtil.getAgoFromSecond(notice.getAgo()));
		
		if (notice.getId() == selectedNoticeId) {
			noticeElement.notice.setVisibility(TextView.VISIBLE);
		} else {
			noticeElement.notice.setVisibility(TextView.GONE);
		}
		
		return convertView;
	}

	public void extendNotice(int position) {
		Notice notice = (Notice) data.get(position);
		if (notice != null) {
			Log.d("Matji", "A");
			selectedNoticeId = notice.getId();
		}
	}
	
	private class NoticeElement {
		TextView subject;
		TextView notice;
		TextView dateAgo;
	}
}