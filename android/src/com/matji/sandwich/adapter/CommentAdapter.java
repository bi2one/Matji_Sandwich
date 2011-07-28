package com.matji.sandwich.adapter;

import java.util.ArrayList;

import com.matji.sandwich.ImageSliderActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.SimpleTag;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.util.DisplayUtil;
import com.matji.sandwich.util.TimeUtil;
import com.matji.sandwich.widget.CommentListView;
import com.matji.sandwich.widget.ProfileImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class CommentAdapter extends MBaseAdapter {  
	private MatjiImageDownloader downloader;
	private static final int TYPE_POST = 0;
	private static final int TYPE_COMMENT = 1;
	private static final int VIEW_TYPE_COUNT = TYPE_COMMENT + 1;

	private int[] imageIds = {
			R.id.row_post_preview1,
			R.id.row_post_preview2,
			R.id.row_post_preview3
	};

	private int profileSize;
	private static final int MARGIN_PREVIEWS = DisplayUtil.PixelFromDP(5);

	public CommentAdapter(Context context) {
		super(context);
		downloader = new MatjiImageDownloader(context);

		profileSize = context.getResources().getDimensionPixelSize(R.dimen.profile_size);
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) return TYPE_POST; // position 이 0 일 경우, POST
		else return TYPE_COMMENT;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d("Matji", "HERE");
		if (position == 0) return getPostView(position, convertView, parent);
		else return getCommentView(position, convertView, parent);
	}

	private View getPostView(int position, View convertView, ViewGroup parent) {
		PostElement postElement = null;
		Post post = (Post) data.get(position);

		if (convertView == null) {
			postElement = new PostElement();
			final CommentListView commentListView = (CommentListView) parent;

			convertView = getLayoutInflater().inflate(R.layout.row_post, null);

			postElement.profile = (ProfileImageView) convertView.findViewById(R.id.profile);
			postElement.nick = (TextView) convertView.findViewById(R.id.row_post_nick);
			postElement.at = (TextView) convertView.findViewById(R.id.row_post_at);
			postElement.storeName = (TextView)convertView.findViewById(R.id.row_post_store_name);
			postElement.post = (TextView) convertView.findViewById(R.id.row_post_post);
			postElement.tag = (TextView) convertView.findViewById(R.id.row_post_tag);
			postElement.dateAgo = (TextView) convertView.findViewById(R.id.row_post_created_at);
			postElement.commentCount = (TextView) convertView.findViewById(R.id.row_post_comment_count);
			postElement.likeCount = (TextView) convertView.findViewById(R.id.row_post_like_count);

			postElement.previews = new ImageView[imageIds.length];	
			for (int i = 0; i < postElement.previews.length; i++) {
				postElement.previews[i] = (ImageView) convertView.findViewById(imageIds[i]);
			}

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(MARGIN_PREVIEWS, MARGIN_PREVIEWS, MARGIN_PREVIEWS, MARGIN_PREVIEWS);

			int remainScreenWidth = context.getResources().getDisplayMetrics().widthPixels;

			for (int i = 0; i < postElement.previews.length; i++) {
				postElement.previews[i].setMaxWidth((remainScreenWidth-profileSize*2)/imageIds.length - MARGIN_PREVIEWS*2);
				postElement.previews[i].setLayoutParams(params);
			}
			convertView.setTag(postElement);

			convertView.findViewById(R.id.row_post_post_wrapper).setBackgroundColor(Color.TRANSPARENT);
			convertView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.comment_post_bg));
			postElement.profile.setOnClickListener(commentListView);
			postElement.nick.setOnClickListener(commentListView);
			postElement.storeName.setOnClickListener(commentListView);
			postElement.post.setLinksClickable(false);

			for (int i = 0; i < postElement.previews.length; i++) {
				postElement.previews[i].setOnClickListener(new PreviewOnClickListener(i));
			}

		} else {
			postElement = (PostElement) convertView.getTag();
		}

		postElement.profile.setTag(position+"");
		postElement.nick.setTag(position+"");
		postElement.storeName.setTag(position+"");
		postElement.post.setTag(position+"");
		for (int i = 0; i < postElement.previews.length; i++) {
			postElement.previews[i].setTag(position+"");
		}

		setPreviews(post, postElement.previews);

		Store store = post.getStore();
		User user = post.getUser();

		if (store != null) {
			postElement.at.setVisibility(View.VISIBLE);
			postElement.storeName.setText(" "+store.getName());
		}
		else { 
			postElement.at.setVisibility(View.GONE);
			postElement.storeName.setText("");
		}

		ArrayList<SimpleTag> tags = post.getTags();
		String tagResult = "";

		if (tags.size() > 0) {
			tagResult += tags.get(0).getTag();
			for (int i = 1; i < tags.size(); i++) {
				tagResult += ", " + tags.get(i).getTag();
			}
			postElement.tag.setText(tagResult);
			postElement.tag.setVisibility(View.VISIBLE);
		} else {
			postElement.tag.setVisibility(View.GONE);
		}

		postElement.profile.setUserId(user.getId());
		postElement.nick.setText(user.getNick()+" ");
		postElement.post.setText(post.getPost().trim());
		postElement.dateAgo.setText(TimeUtil.getAgoFromSecond(post.getAgo()));
		postElement.commentCount.setText(post.getCommentCount() + "");
		postElement.likeCount.setText(post.getLikeCount() + "");

		return convertView;
	}

	private View getCommentView(int position, View convertView, ViewGroup parent) {
		CommentElement commentElement = null;
		Comment comment = (Comment) data.get(position);

		if (convertView == null) {
			commentElement = new CommentElement();
			final CommentListView commentListView = (CommentListView) parent;
			convertView = getLayoutInflater().inflate(R.layout.row_comment, null);

			commentElement.profile = (ProfileImageView) convertView.findViewById(R.id.profile);
			commentElement.nick = (TextView) convertView.findViewById(R.id.row_comment_nick);
			commentElement.dateAgo = (TextView) convertView.findViewById(R.id.row_comment_created_at);
			commentElement.comment = (TextView) convertView.findViewById(R.id.row_comment_comment);
			convertView.setTag(commentElement);

			commentElement.profile.setOnClickListener(commentListView);
			commentElement.nick.setOnClickListener(commentListView);
		} else {
			commentElement = (CommentElement) convertView.getTag();
		}
		commentElement.profile.setTag(position+"");
		commentElement.nick.setTag(position+"");

		convertView.findViewById(R.id.row_comment_wrapper).setVisibility(View.VISIBLE);

		User user = comment.getUser();
		
		commentElement.profile.setUserId(user.getId());
		commentElement.nick.setText(user.getNick());
		commentElement.dateAgo.setText(TimeUtil.getAgoFromSecond(comment.getAgo()) + comment.getFromWhere() + "에서");
		commentElement.comment.setText(comment.getComment());

		return convertView;
	}

	public void setPreviews(Post post, ImageView[] previews) {
		int[] attachFileIds = new int[post.getAttachFiles().size()];
		for (int i = 0; i < attachFileIds.length; i++) {
			attachFileIds[i] = post.getAttachFiles().get(i).getId();
		}

		for (int i = 0; i < previews.length; i++) {
			previews[i].setVisibility(View.GONE);
		}

		for (int i = 0; i < ((attachFileIds.length > previews.length) ? previews.length : attachFileIds.length); i++) {
			downloader.downloadAttachFileImage(attachFileIds[i], MatjiImageDownloader.IMAGE_MEDIUM, previews[i]);
			previews[i].setVisibility(View.VISIBLE);
		}
	}

	private class PreviewOnClickListener implements OnClickListener {
		private int position = 0;

		public PreviewOnClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			int postPosition = Integer.parseInt((String) v.getTag());
			callImageViewer(postPosition, position);
		}		
	}

	public void callImageViewer(int postPosition, int position){
		ArrayList<AttachFile> attachFiles = ((Post) data.get(postPosition)).getAttachFiles();
		int[] attachFileIds = new int[attachFiles.size()];

		for (int i = 0; i < attachFiles.size(); i++) {
			attachFileIds[i] = attachFiles.get(i).getId();
		}

		Intent viewerIntent = new Intent(context, ImageSliderActivity.class);
		viewerIntent.putExtra("attach_file_ids", attachFileIds);
		viewerIntent.putExtra("position", position);
		context.startActivity(viewerIntent);
	}

	private class PostElement {
		ProfileImageView profile;
		TextView nick;
		TextView at;
		TextView storeName;
		TextView post;
		TextView tag;
		TextView dateAgo;
		TextView commentCount;
		TextView likeCount;
		ImageView[] previews;
	}

	private class CommentElement {
		ProfileImageView profile;
		TextView nick;
		TextView comment;
		TextView dateAgo;
	}
}