package com.matji.sandwich.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Message extends MatjiData implements Comparable<Message>{
	private int id;
	private int sent_user_id;
	private int received_user_id;
	private int thread_id;
	private String message;
	private User sent_user;
	private User received_user;
	private String created_at;
	private String updated_at;
	private long ago;
	private boolean msg_read;

	public Message() {}
	
	public Message(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
		public Message createFromParcel(Parcel in) {
			return new Message(in);
		}

		public Message[] newArray(int size) {
			return new Message[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeInt(sent_user_id);
		dest.writeInt(received_user_id);
		dest.writeInt(thread_id);
		dest.writeString(message);
		dest.writeValue(sent_user);
		dest.writeValue(received_user);
		dest.writeString(created_at);
		dest.writeString(updated_at);
		dest.writeLong(ago);
		dest.writeInt(msg_read ? 1 : 0);
	}

	public void readFromParcel(Parcel in) {
		id = in.readInt();
		sent_user_id = in.readInt();
		received_user_id = in.readInt();
		thread_id = in.readInt();
		message = in.readString();
		sent_user = User.class.cast(in.readValue(User.class.getClassLoader()));
		received_user = User.class.cast(in.readValue(User.class.getClassLoader()));
		created_at = in.readString();	
		updated_at = in.readString();
		ago = in.readLong();
		msg_read = in.readInt() != 0;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setSentUserId(int sent_user_id) {
		this.sent_user_id = sent_user_id;
	}
	public int getSentUserId() {
		return sent_user_id;
	}
	public void setReceivedUserId(int received_user_id) {
		this.received_user_id = received_user_id;
	}
	public int getReceivedUserId() {
		return received_user_id;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setSentUser(User sent_user) {
		this.sent_user = sent_user;
	}
	public User getSentUser() {
		return sent_user;
	}
	public void setReceivedUser(User received_user) {
		this.received_user = received_user;
	}
	public User getReceivedUser() {
		return received_user;
	}
	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}
	public String getCreatedAt() {
		return created_at;
	}
	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getUpdatedAt() {
		return updated_at;
	}
	public void setThreadId(int thread_id) {
		this.thread_id = thread_id;
	}
	public int getThreadId() {
		return thread_id;
	}

	public void setAgo(long ago) {
		this.ago = ago;
	}

	public long getAgo() {
		return ago;
	}

	public void setMsgRead(boolean msg_read) {
		this.msg_read = msg_read;
	}

	public boolean isMsgRead() {
		return msg_read;
	}

	@Override
	public int compareTo(Message m) {
		return new Long(ago).compareTo(new Long(m.getAgo()));
	}
}