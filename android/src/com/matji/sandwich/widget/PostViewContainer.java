package com.matji.sandwich.widget;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.http.util.TimeStamp;

public class PostViewContainer extends ViewContainer {
	private MatjiImageDownloader downloader;
	
	private Post post;
	private User user;
	private Store store;
	
	private ImageView thumnail;
	private TextView nick;
	private TextView storeName;
	private TextView content;
	private TextView dateAgo;

	// TODO 태그도 추가
	public PostViewContainer(Context context, Post post, User user, Store store) {
		super(context, R.layout.adapter_post);
		this.post = post;
		this.user = user;
		this.store = store;

		downloader = new MatjiImageDownloader();
		
		setPostData();
	}
	
	public void setPostData() {		
		thumnail = (ImageView) getRootView().findViewById(R.id.thumnail);
		nick = (TextView) getRootView().findViewById(R.id.post_adapter_nick);
		storeName = (TextView) getRootView().findViewById(R.id.post_adapter_store_name);
		content = (TextView) getRootView().findViewById(R.id.post_adapter_post);
		dateAgo = (TextView) getRootView().findViewById(R.id.post_adapter_created_at);
		downloader.downloadUserImage(user.getId(), thumnail);
		
		nick.setText(user.getNick());
		
		if (store != null)
			storeName.setText(" @" + store.getName());
		else 
			storeName.setText("");
		content.setText(post.getPost());
		dateAgo.setText(TimeStamp.getAgoFromDate(post.getCreatedAt()));
	}
}