	package com.matji.sandwich.data;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Post extends MatjiData {
	private int id;
	private int user_id;
	private int store_id;
	private int activity_id;
	private String post;
	private int image_count;
	private int like_count;
	private int comment_count;
	private int tag_count;
	private int lat;
	private int lng;
	private String from_where;	
	private String created_at;
	private String updated_at;
	private User user;
	private Store store;
	private Activity activity;
	private ArrayList<SimpleTag> tags;
	private ArrayList<AttachFile> attach_files;
	private long ago;

	public Post() {}
	
	public Post(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
		public Post createFromParcel(Parcel in) {
			return new Post(in);
		}

		public Post[] newArray(int size) {
			return new Post[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeInt(user_id);
		dest.writeInt(store_id);
		dest.writeInt(activity_id);
		dest.writeString(post);
		dest.writeInt(image_count);
		dest.writeInt(like_count);
		dest.writeInt(comment_count);
		dest.writeInt(tag_count);
		dest.writeInt(lat);
		dest.writeInt(lng);
		dest.writeString(from_where);
		dest.writeString(created_at);
		dest.writeString(updated_at);
		dest.writeValue(user);
		dest.writeValue(store);
		dest.writeValue(activity);
		dest.writeTypedList(tags);
		dest.writeTypedList(attach_files);
		dest.writeLong(ago);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		user_id = in.readInt();
		store_id = in.readInt();
		activity_id = in.readInt();
		post = in.readString();
		image_count = in.readInt();
		like_count = in.readInt();
		comment_count = in.readInt();
		tag_count = in.readInt();
		lat = in.readInt();
		lng = in.readInt();
		from_where = in.readString();
		created_at = in.readString();
		updated_at = in.readString();
		user = User.class.cast(in.readValue(User.class.getClassLoader()));
		store = Store.class.cast(in.readValue(Store.class.getClassLoader()));
		activity = Activity.class.cast(in.readValue(Activity.class.getClassLoader()));
		tags = new ArrayList<SimpleTag>();
		in.readTypedList(tags, SimpleTag.CREATOR);
		attach_files = new ArrayList<AttachFile>();
		in.readTypedList(attach_files, AttachFile.CREATOR);
		ago = in.readLong();
	}
	
	public void setPost(String post) {
		this.post = post;
	}
	public String getPost() {
		return post;
	}
	public void setLat(int lat) {
		this.lat = lat;
	}
	public double getLat() {
		return lat;
	}
	public void setLng(int lng) {
		this.lng = lng;
	}
	public double getLng() {
		return lng;
	}
	public void setFromWhere(String from_where) {
		this.from_where = from_where;
	}
	public String getFromWhere() {
		return from_where;
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
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	public int getUserId() {
		return user_id;
	}
	public void setStoreId(int store_id) {
		this.store_id = store_id;
	}
	public int getStoreId() {
		return store_id;
	}
	public void setActivityId(int activity_id) {
		this.activity_id = activity_id;
	}
	public int getActivityId() {
		return activity_id;
	}
	public void setImageCount(int image_count) {
		this.image_count = image_count;
	}
	public int getImageCount() {
		return image_count;
	}
	public void setLikeCount(int like_count) {
		this.like_count = like_count;
	}
	public int getLikeCount() {
		return like_count;
	}
	public void setCommentCount(int comment_count) {
		this.comment_count = comment_count;
	}
	public int getCommentCount() {
		return comment_count;
	}
	public void setTagCount(int tag_count) {
		this.tag_count = tag_count;
	}
	public int getTagCount() {
		return tag_count;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public Store getStore() {
		return store;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public Activity getActivity() {
		return activity;
	}

	public void setTags(ArrayList<SimpleTag> tags) {
		this.tags = tags;
	}

	public ArrayList<SimpleTag> getTags() {
		return tags;
	}

	public void setAttachFiles(ArrayList<AttachFile> attach_files) {
		this.attach_files = attach_files;
	}

	public ArrayList<AttachFile> getAttachFiles() {
		return attach_files;
	}

	public void setAgo(long ago) {
		this.ago = ago;
	}

	public long getAgo() {
		return ago;
	}
}