package com.matji.sandwich.data;

import java.io.Serializable;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Store extends MatjiData implements Serializable {
	private static final long serialVersionUID = 2258168334454587964L;
	private int id;
	private String name;
	private int reg_user_id;
	private String tel;
	private String address;
	private String add_address;
	private String website;
	private String text;
	private String cover;
	private int lat;
	private int lng;
	private int tag_count;
	private int post_count;
	private int image_count;
	private int like_count;
	private int bookmark_count;
	private AttachFile file; 
	private User reg_user;
	private ArrayList<Tag> tags;
	private ArrayList<StoreFood> store_foods;
	private ArrayList<Food> foods;
	
	public Store() {}
	
	public Store(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Store> CREATOR = new Parcelable.Creator<Store>() {
		public Store createFromParcel(Parcel in) {
			return new Store(in);
		}

		public Store[] newArray(int size) {
			return new Store[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeInt(reg_user_id);
		dest.writeString(tel);
		dest.writeString(address);
		dest.writeString(add_address);
		dest.writeString(website);
		dest.writeString(text);
		dest.writeString(cover);
		dest.writeInt(lat);
		dest.writeInt(lng);
		dest.writeInt(tag_count);
		dest.writeInt(post_count);
		dest.writeInt(image_count);
		dest.writeInt(like_count);
		dest.writeInt(bookmark_count);
		dest.writeValue(file);
		dest.writeValue(reg_user);
		dest.writeTypedList(tags);
		dest.writeTypedList(store_foods);
		dest.writeTypedList(foods);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		name = in.readString();
		reg_user_id = in.readInt();
		tel = in.readString();
		address = in.readString();
		add_address = in.readString();
		website = in.readString();
		text = in.readString();
		cover= in.readString();
		lat = in.readInt();
		lng = in.readInt();
		tag_count = in.readInt();
		post_count = in.readInt();
		image_count = in.readInt();
		like_count = in.readInt();
		bookmark_count = in.readInt();
		file = AttachFile.class.cast(in.readValue(AttachFile.class.getClassLoader()));
		reg_user = User.class.cast(in.readValue(User.class.getClassLoader()));
		tags = new ArrayList<Tag>();
		in.readTypedList(tags, Tag.CREATOR);
		store_foods = new ArrayList<StoreFood>();
		in.readTypedList(store_foods, StoreFood.CREATOR);
		foods = new ArrayList<Food>();
		in.readTypedList(foods, Food.CREATOR);
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getTel() {
		return tel;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return address;
	}
	public void setAddAddress(String add_address) {
		this.add_address = add_address;
	}
	public String getAddAddress() {
		return add_address;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getWebsite() {
		return website;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}
	public void setLat(double lat) {
	    this.lat = (int)(lat * 1E6);
	}
	public int getLat() {
		return lat;
	}
	public void setLng(double lng) {
		this.lng = (int)(lng * 1E6);
	}
	public int getLng() {
		return lng;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getCover() {
		return cover;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setRegUserId(int reg_user_id) {
		this.reg_user_id = reg_user_id;
	}
	public int getRegUserId() {
		return reg_user_id;
	}
	public void setTagCount(int tag_count) {
		this.tag_count = tag_count;
	}
	public int getTagCount() {
		return tag_count;
	}
	public void setPostCount(int post_count) {
		this.post_count = post_count;
	}
	public int getPostCount() {
		return post_count;
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
	public void setBookmarkCount(int bookmark_count) {
		this.bookmark_count = bookmark_count;
	}
	public int getBookmarkCount() {
		return bookmark_count;
	}
	public void setRegUser(User user) {
		this.reg_user = user;
	}
	public User getRegUser() {
		return reg_user;
	}
	public void setFile(AttachFile file) {
		this.file = file;
	}
	public AttachFile getFile() {
		return file;
	}
	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}
	public ArrayList<Tag> getTags() {
		return tags;
	}

	public void setStoreFoods(ArrayList<StoreFood> store_foods) {
		this.store_foods = store_foods;
	}

	public ArrayList<StoreFood> getStoreFoods() {
		return store_foods;
	}

	public void setFoods(ArrayList<Food> foods) {
		this.foods = foods;
	}

	public ArrayList<Food> getFoods() {
		return foods;
	}

    public boolean isLocked() {
	return reg_user_id == 0;
    }

    public String getTagString() {
	String tagText = "등록된 태그가 없습니다.";
	int index = 0;

	if (tags != null && tags.size() > 0){
	    tagText = tags.get(0).getTag();
	    for(index = 1; index < tags.size(); index++){
		tagText += "," + tags.get(index).getTag();
	    }
	}

	return tagText;
    }
}