package com.matji.sandwich.adapter;

import java.text.*;
import java.util.*;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Post;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PostAdapter extends MBaseAdapter {
    public PostAdapter(Context context) {
    	super(context);
    }
	
	public View getView(int position, View convertView, ViewGroup parent) {
		PostElement postElement;
		Post post = (Post) data.get(position);
		
		if (convertView == null) {
			postElement = new PostElement();
			convertView = getLayoutInflater().inflate(R.layout.post_adapter, null);
		
			postElement.image = (ImageView) convertView.findViewById(R.id.thumnail);
			postElement.nick = (TextView) convertView.findViewById(R.id.post_adapter_nick);
			postElement.post = (TextView) convertView.findViewById(R.id.post_adapter_post);
			postElement.dateAgo = (TextView) convertView.findViewById(R.id.post_adapter_created_at);
			convertView.setTag(postElement);
		} else {
			postElement = (PostElement) convertView.getTag();
		}
		Log.d("Matji", post.getId() +"");
		postElement.nick.setText(post.getUpdatedAt());
		postElement.post.setText(post.getPost());
		postElement.dateAgo.setText(post.getCreatedAt());
		return convertView;
	}

    private class PostElement {
    	ImageView image;
    	TextView nick;
    	TextView post;
    	TextView dateAgo;
    }

}