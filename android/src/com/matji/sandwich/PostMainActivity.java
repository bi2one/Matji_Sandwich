package com.matji.sandwich;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.CommentListView;
import com.matji.sandwich.widget.PostViewContainer;

public class PostMainActivity extends Activity {
	private Intent intent;
	private Post post;
	private User user;
	private Store store;

	private PostViewContainer header;
	private TextView tags;
	private CommentListView commentListView;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_main);		
		intent = getIntent();
		post = (Post) intent.getParcelableExtra("post");
		user = post.getUser();
		store = post.getStore();
		
		setPostInfo();
	}

	private void setPostInfo() {
		header = new PostViewContainer(this, post, user, store);

		//TODO
//		tags = (TextView) findViewById(R.id.post_main_tag_area);
		commentListView = (CommentListView) findViewById(R.id.post_main_comment_list_view);
		commentListView.addHeaderView(header);
		//		TODO
		commentListView.setPostId(post.getId());
		commentListView.setActivity(this);
		commentListView.requestReload();
	}
	

}