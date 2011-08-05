package com.matji.sandwich.widget;

import java.util.ArrayList;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.SimpleTag;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.listener.GotoImageSliderAction;
import com.matji.sandwich.listener.GotoStoreMainAction;
import com.matji.sandwich.listener.GotoUserMainAction;
import com.matji.sandwich.util.DisplayUtil;
import com.matji.sandwich.util.TimeUtil;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class CommentHeader extends ViewContainer {

	private int[] imageIds = {
			R.id.header_comment_preview1,
			R.id.header_comment_preview2,
			R.id.header_comment_preview3
	};

	private int profileSize;
	private static final int MARGIN_PREVIEWS = DisplayUtil.PixelFromDP(5);

	private MatjiImageDownloader downloader;
	
	private Post post;
	private ProfileImageView profile;
	private TextView nickText; 
	private TextView atText;
	private TextView storeNameText;
	private TextView postText;
	private TextView tagText;
	private TextView dateAgoText;
	private TextView commentCountText;
	private Button likeListButton;
	private ImageView[] previews;

	public CommentHeader(Context context) {
		super(context, R.layout.header_comment);
		init();
	}
	
	public CommentHeader(Context context, Post post) {
		this(context);
		setPost(post);
	}
	
	protected void init() {
		downloader = new MatjiImageDownloader(getRootView().getContext());
		profileSize = getRootView().getResources().getDimensionPixelSize(R.dimen.profile_size);
		
		profile = (ProfileImageView) getRootView().findViewById(R.id.profile);
		nickText = (TextView) getRootView().findViewById(R.id.header_comment_nick);
		atText = (TextView) getRootView().findViewById(R.id.header_comment_at);
		storeNameText = (TextView)getRootView().findViewById(R.id.header_comment_store_name);
		postText = (TextView) getRootView().findViewById(R.id.header_comment_post);
		tagText = (TextView) getRootView().findViewById(R.id.header_comment_tag);
		dateAgoText = (TextView) getRootView().findViewById(R.id.header_comment_created_at);
		commentCountText = (TextView) getRootView().findViewById(R.id.header_comment_comment_count);
		likeListButton = (Button) getRootView().findViewById(R.id.header_comment_like_list);
//		postElement.menu = (Button) convertView.findViewById(R.id.header_comment_menu);
		
		previews = new ImageView[imageIds.length];

		for (int i = 0; i < previews.length; i++) {
			previews[i] = (ImageView) getRootView().findViewById(imageIds[i]);
		}

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(MARGIN_PREVIEWS, MARGIN_PREVIEWS, MARGIN_PREVIEWS, MARGIN_PREVIEWS);

		int remainScreenWidth = getRootView().getResources().getDisplayMetrics().widthPixels;

		for (int i = 0; i < previews.length; i++) {
			previews[i].setMaxWidth((remainScreenWidth-profileSize*2)/imageIds.length - MARGIN_PREVIEWS*2);
			previews[i].setLayoutParams(params);
			previews[i].setTag(i+"");
		}
	}

	public void setPost(Post post) {
		this.post = post;
		setOnClickListener();
		setViewData();
	}
	
	private void setOnClickListener() {
		GotoUserMainAction action1 = new GotoUserMainAction(getRootView().getContext(), post.getUser());
		GotoStoreMainAction action2 = new GotoStoreMainAction(getRootView().getContext(), post.getStore());
		GotoImageSliderAction action3 = new GotoImageSliderAction(getRootView().getContext(), post.getAttachFiles());
		
		profile.setOnClickListener(action1);
		nickText.setOnClickListener(action1);
		storeNameText.setOnClickListener(action2);
//		holder.menu.setOnClickListener(this);
//		postText.setLinksClickable(false);
		
		for (int i = 0; i < previews.length; i++) {
			previews[i].setOnClickListener(action3);
		}
	}
	

	private void setViewData() {
		setPreviews(post, previews);

		Store store = post.getStore();
		User user = post.getUser();


		if (store != null) {
			atText.setVisibility(View.VISIBLE);
			storeNameText.setText(" "+store.getName());
		}
		else { 
			atText.setVisibility(View.GONE);
			storeNameText.setText("");
		}

		ArrayList<SimpleTag> tags = post.getTags();
		String tagResult = "";

		if (tags.size() > 0) {
			tagResult += tags.get(0).getTag();
			for (int i = 1; i < tags.size(); i++) {
				tagResult += ", " + tags.get(i).getTag();
			}
			tagText.setText(tagResult);
			tagText.setVisibility(View.VISIBLE);
		} else {
			tagText.setVisibility(View.GONE);
		}

		profile.setUserId(user.getId());
		nickText.setText(user.getNick()+" ");
		postText.setText(post.getPost().trim());
		dateAgoText.setText(TimeUtil.getAgoFromSecond(post.getAgo()));
		commentCountText.setText(post.getCommentCount() + "");
		likeListButton.setText(post.getLikeCount() + "");
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
}
