package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.util.TimeUtil;
import com.matji.sandwich.widget.PostListView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PostAdapter extends MBaseAdapter {
	private MatjiImageDownloader downloader;

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

			postElement.thumnail = (ImageView) convertView.findViewById(R.id.post_adapter_thumnail);
			postElement.nick = (TextView) convertView.findViewById(R.id.post_adapter_nick);
			postElement.storeName = (TextView)convertView.findViewById(R.id.post_adapter_store_name);
			postElement.post = (TextView) convertView.findViewById(R.id.post_adapter_post);
			postElement.dateAgo = (TextView) convertView.findViewById(R.id.post_adapter_created_at);
			postElement.commentCount = (TextView) convertView.findViewById(R.id.post_adapter_comment_count);
			postElement.imageCount = (TextView) convertView.findViewById(R.id.post_adapter_image_count);
			postElement.tagCount = (TextView) convertView.findViewById(R.id.post_adapter_tag_count);

			convertView.setTag(postElement);
			final PostListView postListView = (PostListView)parent;
			postElement.thumnail.setOnClickListener(postListView);
			postElement.nick.setOnClickListener(postListView);
			postElement.storeName.setOnClickListener(postListView);
			postElement.post.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int position = Integer.parseInt((String) v.getTag());
					postListView.onListItemClick(position);
				}
			});


		} else {
			postElement = (PostElement) convertView.getTag();
		}

		/* Set Store */
		Store store = post.getStore();
		if (store != null)
			postElement.storeName.setText(" @" + store.getName());
		else 
			postElement.storeName.setText("");

		postElement.thumnail.setTag(position+"");
		postElement.nick.setTag(position+"");
		postElement.storeName.setTag(position+"");
		postElement.post.setTag(position+"");

		/* Set User */
		User user = post.getUser();
		downloader.downloadUserImage(user.getId(), MatjiImageDownloader.IMAGE_SSMALL, postElement.thumnail);
		postElement.nick.setText(user.getNick());
		postElement.post.setText(post.getPost());
		//		postElement.dateAgo.setText(TimeUtil.getAgoFromDate(post.getCreatedAt()));
		postElement.dateAgo.setText(TimeUtil.getAgoFromSecond(post.getAgo()));
		postElement.commentCount.setText(post.getCommentCount() + "");
		postElement.imageCount.setText(post.getImageCount() + "");
		postElement.tagCount.setText(post.getTagCount() + "");

		return convertView;
	}

	private class PostElement {
		ImageView thumnail;
		TextView nick;
		TextView storeName;
		TextView post;
		TextView dateAgo;
		TextView commentCount;
		TextView imageCount;
		TextView tagCount;
	}
}