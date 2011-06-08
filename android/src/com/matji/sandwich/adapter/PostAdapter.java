package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.http.util.TimeStamp;
import com.matji.sandwich.widget.PostListView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PostAdapter extends MBaseAdapter {
	MatjiImageDownloader downloader;

    public PostAdapter(Context context) {
    	super(context);
    	downloader = new MatjiImageDownloader();
    }
	
	public View getView(int position, View convertView, ViewGroup parent) {
		PostElement postElement;
		Post post = (Post) data.get(position);
		
		if (convertView == null) {
			postElement = new PostElement();
			convertView = getLayoutInflater().inflate(R.layout.adapter_post, null);
			
			postElement.image = (ImageView) convertView.findViewById(R.id.thumnail);
			postElement.nick = (TextView) convertView.findViewById(R.id.post_adapter_nick);
			postElement.storeName = (TextView)convertView.findViewById(R.id.post_adapter_store_name);
			postElement.post = (TextView) convertView.findViewById(R.id.post_adapter_post);
			postElement.dateAgo = (TextView) convertView.findViewById(R.id.post_adapter_created_at);
			convertView.setTag(postElement);
			
			PostListView postListView = (PostListView)parent;
			postElement.image.setOnClickListener(postListView);
			postElement.nick.setOnClickListener(postListView);
			postElement.storeName.setOnClickListener(postListView);
			
		} else {
			postElement = (PostElement) convertView.getTag();
		}
		
		/* Set Store */
		Store store = post.getStore();
		if (store != null)
			postElement.storeName.setText(" @" + store.getName());
		else 
			postElement.storeName.setText("");
		
		postElement.image.setTag(position+"");
		postElement.nick.setTag(position+"");
		postElement.storeName.setTag(position+"");
		
		/* Set User */
		User user = post.getUser();
		downloader.downloadUserImage(user.getId(), MatjiImageDownloader.IMAGE_SSMALL, postElement.image);
		postElement.nick.setText(user.getNick());
		postElement.post.setText(post.getPost());
		postElement.dateAgo.setText(TimeStamp.getAgoFromDate(post.getCreatedAt()));

		return convertView;
	}
	
    private class PostElement {
    	ImageView image;
    	TextView nick;
    	TextView storeName;
    	TextView post;
    	TextView dateAgo;
    }
}