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

public class DELETE_PostAdapter extends MBaseAdapter {
	private MatjiImageDownloader downloader;
	private static final int NORMAL_MEMO = 0;
	private static final int ACTIVITY_MEMO = 1;
	private static final int VIEW_TYPE_COUNT = 2;

	public DELETE_PostAdapter(Context context) {
		super(context);
		downloader = new MatjiImageDownloader(context);
	}

	@Override
	public int getItemViewType(int position) {
		Post post = (Post) data.get(position);
		return (post.getActivityId() > 0) ? ACTIVITY_MEMO : NORMAL_MEMO;
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT; // ME, OTHER
	};

	public View getView(int position, View convertView, ViewGroup parent) {
		PostElement postElement;
		Post post = (Post) data.get(position);

		if (convertView == null) {
			postElement = new PostElement();
			final PostListView postListView = (PostListView)parent;

			switch (getItemViewType(position)) {
			case NORMAL_MEMO:
				convertView = getLayoutInflater().inflate(R.layout.row_post, null);
				postElement.thumnail = (ImageView) convertView.findViewById(R.id.thumnail);
				postElement.nick = (TextView) convertView.findViewById(R.id.row_post_nick);
				postElement.storeName = (TextView)convertView.findViewById(R.id.row_post_store_name);
				postElement.post = (TextView) convertView.findViewById(R.id.row_post_post);
				postElement.dateAgo = (TextView) convertView.findViewById(R.id.row_post_created_at);
				postElement.commentCount = (TextView) convertView.findViewById(R.id.row_post_comment_count);
				postElement.likeCount = (TextView) convertView.findViewById(R.id.row_post_like_count);

				convertView.setTag(postElement);
				postElement.thumnail.setOnClickListener(postListView);
				postElement.nick.setOnClickListener(postListView);
				postElement.storeName.setOnClickListener(postListView);
				postElement.post.setLinksClickable(false);
				postElement.post.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int position = Integer.parseInt((String) v.getTag());
						postListView.onListItemClick(position);
					}
				});

				convertView.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						int position = Integer.parseInt((String) ((PostElement) v.getTag()).post.getTag());
						postListView.onListItemClick(position);
					}
				});
				break;
			case ACTIVITY_MEMO:
				convertView = getLayoutInflater().inflate(R.layout.adapter_activity, null);
				postElement.nick = (TextView) convertView.findViewById(R.id.activity_adapter_nick);
				postElement.post = (TextView) convertView.findViewById(R.id.activity_adapter_memo);
				postElement.storeName = (TextView) convertView.findViewById(R.id.activity_adapter_target);
				postElement.dateAgo = (TextView) convertView.findViewById(R.id.activity_adapter_created_at);

				convertView.setTag(postElement);
				postElement.nick.setOnClickListener(postListView);
				postElement.storeName.setOnClickListener(postListView);
				break;
			}
		} else {
			postElement = (PostElement) convertView.getTag();
		}

		Store store = post.getStore();
		User user = post.getUser();
		
		switch (getItemViewType(position)) {
		case NORMAL_MEMO:
			if (store != null)
				postElement.storeName.setText(" @" + store.getName());
			else 
				postElement.storeName.setText("");

			postElement.thumnail.setTag(position+"");
			postElement.nick.setTag(position+"");
			postElement.storeName.setTag(position+"");
			postElement.post.setTag(position+"");

			downloader.downloadUserImage(user.getId(), MatjiImageDownloader.IMAGE_SSMALL, postElement.thumnail);
			postElement.nick.setText(user.getNick());
			postElement.post.setText(post.getPost());
			postElement.dateAgo.setText(TimeUtil.getAgoFromSecond(post.getAgo()));
			postElement.commentCount.setText(post.getCommentCount() + "");
			postElement.likeCount.setText(post.getLikeCount() + "");
			break;
		case ACTIVITY_MEMO:
			postElement.nick.setTag(position+"");
			postElement.storeName.setTag(position+"");
			
			postElement.nick.setText(user.getNick()+" ");
			postElement.post.setText(R.string.activity_likes_the);
			postElement.storeName.setText(" "+store.getName());
			postElement.dateAgo.setText(TimeUtil.getAgoFromSecond(post.getAgo()));			
			break;
		}

		return convertView;
	}

	private class PostElement {
		ImageView thumnail;
		TextView nick;
		TextView storeName;
		TextView post;
		TextView dateAgo;
		TextView commentCount;
		TextView likeCount;
	}
}