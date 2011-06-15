package com.matji.sandwich.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.StoreTabActivity;
import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.http.util.TimeStamp;

public class PostViewContainer extends ViewContainer implements OnClickListener {
	private MatjiImageDownloader downloader;

	private Context context;
	private Activity activity;
	private Post post;
	private User user;
	private Store store;

	private ImageView thumnail;
	private TextView nick;
	private TextView storeName;
	private TextView content;
	private TextView dateAgo;
	private SlideGalleryView gallery;	

	// TODO 태그도 추가
	public PostViewContainer(Context context, Activity activity, Post post, User user, Store store) {
		super(context, R.layout.header_post);
		this.context = context;
		this.activity = activity;
		this.post = post;
		this.user = user;
		this.store = store;

		downloader = new MatjiImageDownloader();

		initPostData();
	}

	private void initPostData() {		
		thumnail = (ImageView) getRootView().findViewById(R.id.post_adapter_thumnail);
		nick = (TextView) getRootView().findViewById(R.id.post_adapter_nick);
		storeName = (TextView) getRootView().findViewById(R.id.post_adapter_store_name);
		content = (TextView) getRootView().findViewById(R.id.post_adapter_post);
		dateAgo = (TextView) getRootView().findViewById(R.id.post_adapter_created_at);
		gallery = (SlideGalleryView) getRootView().findViewById(R.id.header_post_gallery);
		
		downloader.downloadUserImage(user.getId(), thumnail);
		nick.setText(user.getNick());

		if (store != null)
			storeName.setText(" @" + store.getName());
		else 
			storeName.setText("");
		content.setText(post.getPost());
		
		thumnail.setOnClickListener(this);
		nick.setOnClickListener(this);
		storeName.setOnClickListener(this);

		gallery.setActivity(activity);
		gallery.setPostId(post.getId());

		setPostData();
	}
	
	private void setPostData() {
		gallery.refresh();
		dateAgo.setText(TimeStamp.getAgoFromDate(post.getCreatedAt()));
	}

	
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.post_adapter_thumnail: case R.id.post_adapter_nick:
			gotoUserPage(post);
			break;
		case R.id.post_adapter_store_name:
			gotoStorePage(post);
			break;
		}	
	}

	protected void gotoUserPage(Post post) {
		Intent intent = new Intent(context, UserTabActivity.class);
		((BaseActivity) context).startActivityWithMatjiData(intent, post.getUser());
	}	

	protected void gotoStorePage(Post post) {
		Intent intent = new Intent(context, StoreTabActivity.class);
		((BaseActivity) context).startActivityWithMatjiData(intent, post.getStore());
	}
}