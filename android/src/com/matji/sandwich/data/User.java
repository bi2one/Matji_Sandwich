package com.matji.sandwich.data;

import java.io.Serializable;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class User extends MatjiData implements Serializable {
	private static final long serialVersionUID = 3938226042478924177L;
	private int id;
	private String userid;
	private String nick;
	private String email;
	private String title;
	private String intro;
	private String website;
	private int post_count;
	private int tag_count;
	private int url_count;
	private int like_store_count;
	private int discover_store_count;
	private int bookmark_store_count;
	private int following_count;
	private int follower_count;
	private int received_message_count;
	private int image_count;
	private boolean following;
	private boolean followed;
	private ExternalAccount external_account;
	private UserMileage mileage;
	private Post post;
	private ArrayList<AttachFile> attach_files;
	private ArrayList<Store> stores;
    private AlarmSetting user_alarm_setting;
    private String country_code;

	public User() {}

	public User(Parcel in) {
		readFromParcel(in);
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		public User[] newArray(int size) {
			return new User[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeString(userid);
		dest.writeString(nick);
		dest.writeString(email);
		dest.writeString(title);
        dest.writeString(intro);
        dest.writeString(website);
		dest.writeInt(post_count);
		dest.writeInt(tag_count);
		dest.writeInt(url_count);		
        dest.writeInt(like_store_count);
        dest.writeInt(discover_store_count);
        dest.writeInt(bookmark_store_count);
		dest.writeInt(following_count);
		dest.writeInt(follower_count);
        dest.writeInt(image_count);
        dest.writeInt(received_message_count);
		dest.writeInt(following ? 1 : 0);
		dest.writeInt(followed ? 1 : 0);
		dest.writeValue(external_account);
		dest.writeValue(mileage);
		dest.writeValue(post);
		dest.writeTypedList(attach_files);
		dest.writeTypedList(stores);
        dest.writeValue(user_alarm_setting);
        dest.writeString(country_code);
	}

	public void readFromParcel(Parcel in) {
		id = in.readInt();
		userid = in.readString();
		nick = in.readString();
		email = in.readString();
		title = in.readString();
		intro = in.readString();
		website = in.readString();
		post_count = in.readInt();
		tag_count = in.readInt();
		url_count = in.readInt();
        like_store_count = in.readInt();
        discover_store_count = in.readInt();
        bookmark_store_count = in.readInt();
		following_count = in.readInt();
		follower_count = in.readInt();
		image_count = in.readInt();
		received_message_count = in.readInt();
		following = in.readInt() != 0;
		followed = in.readInt() != 0;
		external_account = ExternalAccount.class.cast(in.readValue(ExternalAccount.class.getClassLoader()));
		mileage = UserMileage.class.cast(in.readValue(UserMileage.class.getClassLoader()));
		post = Post.class.cast(in.readValue(Post.class.getClassLoader()));
		attach_files = new ArrayList<AttachFile>();
		in.readTypedList(attach_files, AttachFile.CREATOR);
		stores = new ArrayList<Store>();
		in.readTypedList(stores, Store.CREATOR);
		user_alarm_setting = AlarmSetting.class.cast(in.readValue(AlarmSetting.class.getClassLoader()));
		country_code = in.readString();
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserid() {
		return userid;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return nick;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getIntro() {
		return intro;
	}

	public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setPostCount(int post_count) {
		this.post_count = post_count;
	}

	public int getPostCount() {
		return post_count;
	}

	public void setTagCount(int tag_count) {
		this.tag_count = tag_count;
	}

	public int getTagCount() {
		return tag_count;
	}

	public void setUrlCount(int url_count) {
        this.url_count = url_count;
    }

    public int getUrlCount() {
        return url_count;
    }

    public void setLikeStoreCount(int store_count) {
		this.like_store_count = store_count;
	}

	public int getLikeStoreCount() {
		return like_store_count;
	}

	public void setDiscoverStoreCount(int discover_store_count) {
        this.discover_store_count = discover_store_count;
    }

    public int getDiscoverStoreCount() {
        return discover_store_count;
    }

    public void setBookmarkStoreCount(int bookmark_store_count) {
        this.bookmark_store_count = bookmark_store_count;
    }

    public int getBookmarkStoreCount() {
        return bookmark_store_count;
    }

    public void setFollowingCount(int following_count) {
		this.following_count = following_count;
	}

	public int getFollowingCount() {
		return following_count;
	}

	public void setFollowerCount(int follower_count) {
		this.follower_count = follower_count;
	}

	public int getFollowerCount() {
		return follower_count;
	}

    public void setImageCount(int image_count) {
        this.image_count = image_count;
    }

    public int getImageCount() {
        return image_count;
    }

    public void setReceivedMessageCount(int received_message_count) {
        this.received_message_count = received_message_count;
    }

    public int getReceivedMessageCount() {
        return received_message_count;
    }

	public void setFollowing(boolean following) {
		this.following = following;
	}

	public boolean isFollowing() {
		return following;
	}

	public void setFollowed(boolean followed) {
		this.followed = followed;
	}

	public boolean isFollowed() {
		return followed;
	}

	public void setExternalAccount(ExternalAccount external_account) {
		this.external_account = external_account;
	}

	public ExternalAccount getExternalAccount() {
		return external_account;
	}

	public void setMileage(UserMileage mileage) {
		this.mileage = mileage;
	}

	public UserMileage getMileage() {
		return mileage;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Post getPost() {
		return post;
	}
	
	public void setAttchFiles(ArrayList<AttachFile> attach_files) {
		this.attach_files = attach_files;
	}
	
	public ArrayList<AttachFile> getAttachFiles() {
		return attach_files;
	}

	public void setStores(ArrayList<Store> stores) {
		this.stores = stores;
	}
	
	public ArrayList<Store> getStores() {
		return stores;
	}	

    public void setAlarmSetting(AlarmSetting user_alarm_setting) {
        this.user_alarm_setting = user_alarm_setting;
    }
    
    public AlarmSetting getAlarmSetting() {
        return user_alarm_setting;
    }

    public void setCountryCode(String country_code) {
        this.country_code = country_code;
    }

    public String getCountryCode() {
        return country_code;
    }
}