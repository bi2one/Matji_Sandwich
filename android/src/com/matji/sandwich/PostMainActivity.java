package com.matji.sandwich;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.http.util.TimeStamp;
import com.matji.sandwich.widget.CommentListView;

public class PostMainActivity extends Activity {
	private Intent intent;
	private Post post;
	private User user;
	private Store store;
	private MatjiImageDownloader downloader;

	private ImageView thumnail;
	private TextView nick;
	private TextView storeName;
	private TextView content;
	private TextView dateAgo;
	private TextView tags;
	private CommentListView commentListView;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_main);		
		intent = getIntent();
		post = (Post) intent.getParcelableExtra("post");
		user = post.getUser();
		store = post.getStore();
		downloader = new MatjiImageDownloader();
		
		setPostInfo();
	}

	private void setPostInfo() {
		thumnail = (ImageView) findViewById(R.id.thumnail);
		nick = (TextView) findViewById(R.id.post_adapter_nick);
		storeName = (TextView) findViewById(R.id.post_adapter_store_name);
		content = (TextView) findViewById(R.id.post_adapter_post);
		dateAgo = (TextView) findViewById(R.id.post_adapter_created_at);
		tags = (TextView) findViewById(R.id.post_main_tag_area);
		commentListView = (CommentListView) findViewById(R.id.post_main_comment_list_view);

		downloader.downloadUserImage(user.getId(), thumnail);
		nick.setText(user.getNick());
		
		if (store != null)
			storeName.setText(" @" + store.getName());
		else 
			storeName.setText("");
		content.setText(post.getPost());
		dateAgo.setText(TimeStamp.getAgoFromDate(post.getCreatedAt()));

		//		TODO
		commentListView.setPostId(post.getId());
		commentListView.setActivity(this);
		commentListView.requestReload();
	}
}