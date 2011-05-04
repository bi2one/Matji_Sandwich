package com.matji.sandwich.data;

import java.util.ArrayList;

public class User extends MatjiData{
	private int id;
	private String userid;
	private String hashed_password;
	private String old_hashed_password;
	private String salt;
	private String nick;
	private String email;
	private String title;
	private String intro;
	private int post_count;
	private int tag_count;
	private int store_count;
	private int following_count;
	private int follower_count;
	private int sequence;
	private ArrayList<AccessGrant> access_grants;
	private ArrayList<Activity> activities;
	private ArrayList<Alarm> alarms;
	private ArrayList<AttachFile> attach_files;
	private ArrayList<Bookmark> bookmarks;
	private ArrayList<Client> clients;
	private ArrayList<Following> followings;
	private ArrayList<Like> likes;
	private ArrayList<Store> stores;
	private ArrayList<StoreTag> store_tags;
	private ArrayList<StoreDetailInfo> store_detail_infos;
	private ArrayList<Post> posts;
	private ArrayList<UserExternalAccount> user_external_accounts;
	private ArrayList<Tag> tags;
	
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
	public void setHashed_password(String hashed_password) {
		this.hashed_password = hashed_password;
	}
	public String getHashed_password() {
		return hashed_password;
	}
	public void setOld_hashed_password(String old_hashed_password) {
		this.old_hashed_password = old_hashed_password;
	}
	public String getOld_hashed_password() {
		return old_hashed_password;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getSalt() {
		return salt;
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
	public void setPost_count(int post_count) {
		this.post_count = post_count;
	}
	public int getPost_count() {
		return post_count;
	}
	public void setTag_count(int tag_count) {
		this.tag_count = tag_count;
	}
	public int getTag_count() {
		return tag_count;
	}
	public void setStore_count(int store_count) {
		this.store_count = store_count;
	}
	public int getStore_count() {
		return store_count;
	}
	public void setFollowing_count(int following_count) {
		this.following_count = following_count;
	}
	public int getFollowing_count() {
		return following_count;
	}
	public void setFollower_count(int follower_count) {
		this.follower_count = follower_count;
	}
	public int getFollower_count() {
		return follower_count;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getSequence() {
		return sequence;
	}
	public void setAccess_grants(ArrayList<AccessGrant> access_grants) {
		this.access_grants = access_grants;
	}
	public ArrayList<AccessGrant> getAccess_grants() {
		return access_grants;
	}
	public void addAccessGrant(AccessGrant access_grant){
		this.access_grants.add(access_grant);
	}
	public void setActivities(ArrayList<Activity> activities) {
		this.activities = activities;
	}
	public ArrayList<Activity> getActivities() {
		return activities;
	}
	public void addActivity(Activity activity){
		this.activities.add(activity);
	}
	public void setAlarms(ArrayList<Alarm> alarms) {
		this.alarms = alarms;
	}
	public ArrayList<Alarm> getAlarms() {
		return alarms;
	}
	public void addAlarm(Alarm alarm){
		this.alarms.add(alarm);
	}
	public void setAttach_files(ArrayList<AttachFile> attach_files) {
		this.attach_files = attach_files;
	}
	public ArrayList<AttachFile> getAttach_files() {
		return attach_files;
	}
	public void addAttachFile(AttachFile attach_file){
		this.attach_files.add(attach_file);
	}
	public void setBookmarks(ArrayList<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}
	public ArrayList<Bookmark> getBookmarks() {
		return bookmarks;
	}
	public void addBookmark(Bookmark bookmark){
		this.bookmarks.add(bookmark);
	}
	public void setClients(ArrayList<Client> clients) {
		this.clients = clients;
	}
	public ArrayList<Client> getClients() {
		return clients;
	}
	public void addClient(Client client){
		this.clients.add(client);
	}
	public void setFollowings(ArrayList<Following> followings) {
		this.followings = followings;
	}
	public ArrayList<Following> getFollowings() {
		return followings;
	}
	public void addFollowing(Following following){
		this.followings.add(following);
	}
	public void setLikes(ArrayList<Like> likes) {
		this.likes = likes;
	}
	public ArrayList<Like> getLikes() {
		return likes;
	}
	public void addLike(Like like){
		this.likes.add(like);
	}
	public void setStores(ArrayList<Store> stores) {
		this.stores = stores;
	}
	public ArrayList<Store> getStores() {
		return stores;
	}
	public void addStore(Store store){
		this.stores.add(store);
	}
	public void setStore_tags(ArrayList<StoreTag> store_tags) {
		this.store_tags = store_tags;
	}
	public ArrayList<StoreTag> getStore_tags() {
		return store_tags;
	}
	public void addStoreTag(StoreTag store_tag){
		this.store_tags.add(store_tag);
	}
	public void setStore_detail_infos(ArrayList<StoreDetailInfo> store_detail_infos) {
		this.store_detail_infos = store_detail_infos;
	}
	public ArrayList<StoreDetailInfo> getStore_detail_infos() {
		return store_detail_infos;
	}
	public void addStoreDetailInfo(StoreDetailInfo store_detail_info){
		this.store_detail_infos.add(store_detail_info);
	}
	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}
	public ArrayList<Post> getPosts() {
		return posts;
	}
	public void addPost(Post post){
		this.posts.add(post);
	}
	public void setUser_external_accounts(ArrayList<UserExternalAccount> user_external_accounts) {
		this.user_external_accounts = user_external_accounts;
	}
	public ArrayList<UserExternalAccount> getUser_external_accounts() {
		return user_external_accounts;
	}
	public void addUserExternalAccount(UserExternalAccount user_external_account){
		this.user_external_accounts.add(user_external_account);
	}
	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}
	public ArrayList<Tag> getTags() {
		return tags;
	}
	public void addTag(Tag tag){
		this.tags.add(tag);
	}
	
}
