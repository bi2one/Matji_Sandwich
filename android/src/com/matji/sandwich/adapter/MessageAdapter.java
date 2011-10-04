package com.matji.sandwich.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.MessageHttpRequest;
import com.matji.sandwich.listener.GotoUserMainAction;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.SessionPrivateUtil;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.TimeUtil;

public class MessageAdapter extends MBaseAdapter implements Requestable {

	private SessionPrivateUtil privateUtil;

	public enum MessageType {
		SENT, 
		RECEIVED,
	}

	private HashMap<Integer, Boolean> hasFoldedMap;
	private MessageType type;

	private Drawable iconNew;
	private HttpRequest request;
	private HttpRequestManager manager;

	public MessageAdapter(Context context) {
		super(context);
		this.context = context;
		hasFoldedMap = new HashMap<Integer, Boolean>();
		iconNew = MatjiConstants.drawable(R.drawable.icon_new);
		iconNew.setBounds(0, 0, iconNew.getIntrinsicWidth(), iconNew.getIntrinsicHeight());
		manager = HttpRequestManager.getInstance();
		privateUtil = Session.getInstance(context).getPrivateUtil();
	}

	public void setMessageType(MessageType type) {
		this.type = type;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final MessageElement messageElement;
		Message message = (Message) data.get(position);

		if (convertView == null) {
			messageElement = new MessageElement();
			convertView = getLayoutInflater().inflate(R.layout.row_message, null);

			messageElement.subjectList = (TextView) convertView.findViewById(R.id.row_message_subject_list);
			messageElement.nick = (TextView) convertView.findViewById(R.id.row_message_nick);
			messageElement.createdAtList = (TextView) convertView.findViewById(R.id.row_message_created_at_list);
			messageElement.subject = (TextView) convertView.findViewById(R.id.row_message_subject);
			messageElement.createdAt = (TextView) convertView.findViewById(R.id.row_message_created_at);
			messageElement.messageContent = (TextView) convertView.findViewById(R.id.row_message_message);
			messageElement.subjectWrapper = (View) convertView.findViewById(R.id.row_message_subject_wrapper).getParent();
			messageElement.flow = (ImageView) messageElement.subjectWrapper.findViewById(R.id.row_message_flow); 
			messageElement.messageWrapper = messageElement.subjectWrapper.findViewById(R.id.row_message_message_wrapper);

			convertView.setTag(messageElement);

		} else {
			messageElement = (MessageElement) convertView.getTag();
		}

		User user = null;
		switch (type) {
		case SENT:
			user = message.getReceivedUser();
			break;
		case RECEIVED:
			user = message.getSentUser();
			break;
		}

		messageElement.subjectWrapper.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				folding(position, messageElement);
			}
		});

		messageElement.message = message;
		messageElement.subjectList.setText(message.getMessage());
		messageElement.nick.setText(user.getNick());
		messageElement.nick.setOnClickListener(new GotoUserMainAction(context, user));

		String createdAt = TimeUtil.parseString(
				"yyyy-MM-dd hh:mm", 
				TimeUtil.getDateFromCreatedAt(message.getCreatedAt()));

		messageElement.createdAtList.setText(createdAt);
		if (type == MessageType.RECEIVED
				&& !message.isMsgRead()) {
			messageElement.createdAtList.setCompoundDrawables(null, null, iconNew, null);
		} else {
			messageElement.createdAtList.setCompoundDrawables(null, null, null, null);
		}
		messageElement.subject.setVisibility(View.GONE);
		messageElement.createdAt.setText(createdAt);
		messageElement.messageContent.setText(message.getMessage());
		if (hasFolded(position)) {
			fold(messageElement);
		} else {
			unfold(messageElement);
		}

		return convertView;
	}

	/**
	 * 접혀 있으면 펼치고, 펼쳐져 있으면 접고...
	 * 
	 * @param position 접고 펼칠 아이템의 위치
	 * @param holder 조작할 뷰홀더
	 */
	public void folding(int position, MessageElement holder) {
		if (hasFolded(position)) {
			unfold(position, holder);
		} else {
			fold(position, holder);
		}
	}

	/**
	 * 해당 위치의 아이템이 접혀져 있는지 맵에서 찾아본 후 결과 값 리턴
	 * 
	 * @param position 접혀져 있는 지 확인 할 아이템의 위치
	 * @return 졉혀져 있을 때 TRUE
	 */
	public boolean hasFolded(int position) {
		if (hasFoldedMap.get(position) == null) {
			hasFoldedMap.put(position, true);
		}

		return hasFoldedMap.get(position);
	}

	public void unfold(int position, MessageElement holder) {
		hasFoldedMap.put(position, false);
		unfold(holder);
	}

	public void unfold(MessageElement holder) {
		holder.flow.setImageResource(R.drawable.icon_flow_bottom);
		holder.messageWrapper.setVisibility(View.VISIBLE);
		manager.request(context, readRequest(holder.message), HttpRequestManager.MESSAGE_READ_REQUEST, this);
		holder.message.setMsgRead(true);
		holder.createdAtList.setCompoundDrawables(null, null, null, null);
	}

	public void fold(int position, MessageElement holder) {
		hasFoldedMap.put(position, true);
		fold(holder);
	}

	public void fold(MessageElement holder) {
		holder.flow.setImageResource(R.drawable.icon_flow);
		holder.messageWrapper.setVisibility(View.GONE);
	}

	private class MessageElement {
		Message message;
		TextView subjectList;
		TextView nick; 
		TextView createdAtList;
		TextView subject;
		TextView createdAt;
		TextView messageContent;
		ImageView flow;
		View messageWrapper;
		View subjectWrapper;
	}

	public HttpRequest readRequest(Message message) {
		if (request == null || !(request instanceof MessageHttpRequest)) { 
			request = new MessageHttpRequest(context);
		}

		((MessageHttpRequest) request).actionRead(message.getId());
		return request;
	}

	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch (tag) {
		case HttpRequestManager.MESSAGE_READ_REQUEST:
			if (!(privateUtil.getNewNoticeCount() < 0)) {
				privateUtil.setNewNoticeCount(privateUtil.getNewNoticeCount()-1);
			}
			break;
		}
	}

	@Override
	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(context);
	}
}