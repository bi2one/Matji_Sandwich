package com.matji.sandwich.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.WriteMessageActivity;
import com.matji.sandwich.base.Identifiable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.MessageHttpRequest;
import com.matji.sandwich.http.spinner.SpinnerFactory.SpinnerType;
import com.matji.sandwich.listener.GotoUserMainAction;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.SessionPrivateUtil;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.TimeUtil;
import com.matji.sandwich.widget.dialog.ActionItem;
import com.matji.sandwich.widget.dialog.QuickActionDialog;
import com.matji.sandwich.widget.dialog.SimpleConfirmDialog;
import com.matji.sandwich.widget.dialog.SimpleDialog;

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
	private Activity activity;

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
			messageElement.sentUserNick = (TextView) convertView.findViewById(R.id.row_message_sent_user_nick);
			messageElement.receivedUserNick = (TextView) convertView.findViewById(R.id.row_message_received_user_nick);
			messageElement.createdAt = (TextView) convertView.findViewById(R.id.row_message_created_at);
			messageElement.messageContent = (TextView) convertView.findViewById(R.id.row_message_message);
			messageElement.subjectWrapper = (View) convertView.findViewById(R.id.row_message_subject_wrapper).getParent();
			messageElement.flow = (ImageView) messageElement.subjectWrapper.findViewById(R.id.row_message_flow); 
			messageElement.messageWrapper = messageElement.subjectWrapper.findViewById(R.id.row_message_message_wrapper);
			messageElement.menu = (ImageButton) convertView.findViewById(R.id.row_message_menu_btn);
			messageElement.spinnerContainer = (ViewGroup) convertView.findViewById(R.id.SpinnerContainer);
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
		messageElement.subject.setText(message.getMessage());
		
		if (type == MessageType.RECEIVED) {
		    messageElement.sentUserNick.setText(message.getSentUser().getNick());
		    messageElement.sentUserNick.setOnClickListener(new GotoUserMainAction(context, message.getSentUser()));
		}
		messageElement.receivedUserNick.setText(message.getReceivedUser().getNick());
		messageElement.receivedUserNick.setOnClickListener(new GotoUserMainAction(context, message.getReceivedUser()));
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
		messageElement.createdAt.setText(createdAt);
		messageElement.messageContent.setText(message.getMessage());
		messageElement.menu.setOnClickListener(new MessageAdapterQuickActionDialog(position, messageElement.spinnerContainer));
        messageElement.menu.setTag(messageElement.menu);
        
		if (hasFolded(position)) {
			fold(messageElement);
		} else {
			unfold(messageElement);
		}

		return convertView;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
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
		TextView sentUserNick;
		TextView receivedUserNick;
		TextView createdAt;
		TextView messageContent;
		ImageView flow;
		View messageWrapper;
		View subjectWrapper;
		ImageButton menu;
		ViewGroup spinnerContainer;
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


	/**
	 * QuickAction 버튼을 클릭했을 때, 다이얼로그를 보여주고
	 * 다이얼로그 내의 버튼을 클릭했을 때 각 버튼에 대한 행동을 취한다.
	 *  
	 * @author mozziluv
	 *
	 */
	class MessageAdapterQuickActionDialog implements Requestable, OnClickListener {

		private int position;
		private Message curMsg;
		private QuickActionDialog quickaction;
		private Session session;
		private HttpRequest request;
		private HttpRequestManager manager;
		private ViewGroup spinnerContainer;
		
		private int REPLY_POS;
		private int DELETE_POS;

		/**
		 * 기본 생성자.
		 * 
		 * @param position 현재 QuickAction 버튼이 눌린 position
		 */
		public MessageAdapterQuickActionDialog(int position, ViewGroup spinnerContainer) {
			this.position = position;
			this.curMsg = (Message) data.get(position);
			this.quickaction = new QuickActionDialog(context);
			this.session = Session.getInstance(context);
			this.manager = HttpRequestManager.getInstance();
			this.spinnerContainer = spinnerContainer;

			// 자신이 작성한 이야기일 경우 수정, 삭제 버튼도 추가.
			if (isReceivedMessage(curMsg)) {
				ActionItem replyAction = new ActionItem();				replyAction.setIcon(MatjiConstants.drawable(R.drawable.icon_memo_reply));				quickaction.addActionItem(replyAction);
				REPLY_POS = 0;
				DELETE_POS = REPLY_POS + 1;			} else {
				REPLY_POS = -1;
			}

			ActionItem deleteAction = new ActionItem();
			deleteAction.setIcon(MatjiConstants.drawable(R.drawable.icon_memo_del));
			quickaction.addActionItem(deleteAction);
						// 클릭 리스너 등록.			quickaction.setOnActionItemClickListener(new QuickActionDialog.OnActionItemClickListener() {
				@Override				public void onItemClick(int pos) {					if (((Identifiable) context).loginRequired()) {						if (pos == REPLY_POS) {							Log.d("Matji", "reply button click");
							Intent intent = new Intent(context, WriteMessageActivity.class);
							intent.putExtra(WriteMessageActivity.USER, (Parcelable) curMsg.getSentUser());
							context.startActivity(intent);						} else if (pos == DELETE_POS) {							Log.d("Matji", "delete button click");							deleteMessage();						}					}				}			});		}
		/**		 * 이야기 삭제 버튼을 클릭했을 때,		 * 현재 이 이야기를 삭제할 것인지를 묻는 Alert 윈도우를 띄우고,		 * 확인 했을 때 삭제요청을 한다.		 */		public void deleteMessage() {			if (activity.getParent() != null) {				activity = activity.getParent();			}
			SimpleConfirmDialog dialog = new SimpleConfirmDialog(activity, R.string.default_string_check_delete);			dialog.setOnClickListener(new SimpleConfirmDialog.OnClickListener() {
				@Override				public void onConfirmClick(SimpleDialog dialog) {					deleteRequest(curMsg);				}
				@Override				public void onCancelClick(SimpleDialog dialog) {}			});			dialog.show();		}
		/**		 * Delete 요청		 * @param message Delete할 Message		 */		public void deleteRequest(Message message) {			// Alert 창 띄우기.			if (!manager.isRunning()) {				if (request == null || !(request instanceof MessageHttpRequest)) {					request = new MessageHttpRequest(context);				}
				((MessageHttpRequest) request).actionDelete(message.getId());				manager.request(context, spinnerContainer, SpinnerType.SSMALL, request, HttpRequestManager.POST_DELETE_REQUEST, this);			}		}
		/**		 * 파라미터로 전달받은 {@link Message}가 현재 로그인 된 {@link User}의 {@link Message}인지 확인한다.		 * 		 * @param post 확인 할 {@link Message}		 * @return 전달받은 {@link Message}의 ReceivedUser가 로그인 된 {@link User}의 {@link Message}일 때 true		 */		public boolean isReceivedMessage(Message message) {			return session.isLogin() && session.getCurrentUser().getId() == message.getReceivedUserId();		}
		/**		 * @see Requestable#requestCallBack(int, ArrayList)		 */		@Override		public void requestCallBack(int tag, ArrayList<MatjiData> data) {
			switch (tag) {			// TODO Auto-generated method stub			case HttpRequestManager.POST_DELETE_REQUEST:				postDelete();				break;			}		}
		/**		 * @see Requestable#requestExceptionCallBack(int, MatjiException)		 */		@Override		public void requestExceptionCallBack(int tag, MatjiException e) {			// TODO Auto-generated method stub			e.performExceptionHandling(context);		}
		/**		 * Quick Action Dialog를 보여준다.		 */		@Override		public void onClick(View v) {			quickaction.show((View) v.getTag());		}
		
		private void postDelete() {
			hasFoldedMap.remove(position);
			data.remove(position);			
			notifyDataSetChanged();
		}	}
}