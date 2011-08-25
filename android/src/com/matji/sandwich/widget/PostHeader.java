package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.SimpleTag;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.listener.GotoImageSliderAction;
import com.matji.sandwich.listener.GotoStoreMainAction;
import com.matji.sandwich.listener.GotoUserMainAction;
import com.matji.sandwich.util.TimeUtil;

public class PostHeader extends ViewContainer {

	private int[] previewWrapperIds = {
			R.id.header_post_preview1,
			R.id.header_post_preview2,
			R.id.header_post_preview3
	};

	private MatjiImageDownloader downloader;

	private Post post;
	private PostElement holder;

	public PostHeader(Context context) {
		super(context, R.layout.header_post);
		init();
	}
	
	public PostHeader(Context context, Post post) {
		this(context);
		init(post);
	}
	
	protected void init(Post post) {
	    init();
	    setPost(post);
	}
	
	protected void init() {
		downloader = new MatjiImageDownloader(getRootView().getContext());
		holder = new PostElement();
		
		holder.profile = (ProfileImageView) getRootView().findViewById(R.id.profile);
		holder.nickText = (TextView) getRootView().findViewById(R.id.header_post_nick);
		holder.atText = (TextView) getRootView().findViewById(R.id.header_post_at);
		holder.storeNameText = (TextView)getRootView().findViewById(R.id.header_post_store_name);
		holder.postText = (TextView) getRootView().findViewById(R.id.header_post_post);
		holder.previews = getRootView().findViewById(R.id.header_post_previews);
		holder.tagText = (TextView) getRootView().findViewById(R.id.header_post_tag);
		holder.dateAgoText = (TextView) getRootView().findViewById(R.id.header_post_created_at);
		holder.commentCountText = (TextView) getRootView().findViewById(R.id.header_post_comment_count);
		holder.likeCountText = (TextView) getRootView().findViewById(R.id.header_post_like_count);
//		postElement.menu = (Button) convertView.findViewById(R.id.header_post_menu);
		
		holder.profile.showInsetBackground();
		
		holder.previewWrapper = new RelativeLayout[previewWrapperIds.length];
		holder.preview = new ImageView[previewWrapperIds.length];

		for (int i = 0; i < previewWrapperIds.length; i++) {
		    holder.preview[i] = new ImageView(getRootView().getContext());
		    holder.previewWrapper[i] = (RelativeLayout) getRootView().findViewById(previewWrapperIds[i]);
            holder.previewWrapper[i].addView(holder.preview[i]);
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
		
		holder.profile.setOnClickListener(action1);
		holder.nickText.setOnClickListener(action1);
		holder.storeNameText.setOnClickListener(action2);
//		holder.menu.setOnClickListener(this);
//		postText.setLinksClickable(false);
		
		for (int i = 0; i < previewWrapperIds.length; i++) {
			holder.preview[i].setOnClickListener(action3);
		}
	}
	

	private void setViewData() {
		setPreviews(holder, post);

		Store store = post.getStore();
		User user = post.getUser();


		if (store != null) {
			holder.atText.setVisibility(View.VISIBLE);
			holder.storeNameText.setText(" "+store.getName());
		}
		else { 
			holder.atText.setVisibility(View.GONE);
			holder.storeNameText.setText("");
		}

		ArrayList<SimpleTag> tags = post.getTags();
		String tagResult = "";

		if (tags.size() > 0) {
			tagResult += tags.get(0).getTag();
			for (int i = 1; i < tags.size(); i++) {
				tagResult += ", " + tags.get(i).getTag();
			}
			holder.tagText.setText(tagResult);
			holder.tagText.setVisibility(View.VISIBLE);
		} else {
		    holder.tagText.setVisibility(View.GONE);
		}

		holder.profile.setUserId(user.getId());
		holder.nickText.setText(user.getNick()+" ");
		holder.postText.setText(post.getPost().trim());
		holder.dateAgoText.setText(TimeUtil.getAgoFromSecond(post.getAgo()));
		holder.commentCountText.setText(post.getCommentCount() + "");
		holder.likeCountText.setText(post.getLikeCount() + "");
	}

	public void setPreviews(PostElement holder, Post post) {
        holder.previews.setVisibility(View.GONE);
        
        for (int i = 0; i < holder.preview.length; i++) {
            holder.preview[i].setVisibility(View.GONE);
        }
        
        for (int i = 0; (i < post.getAttachFiles().size()) && (i < holder.preview.length); i++) {
            holder.previews.setVisibility(View.VISIBLE);
            holder.preview[i].setVisibility(View.VISIBLE);
            holder.preview[i].setTag(i+"");
            downloader.downloadAttachFileImage(post.getAttachFiles().get(i).getId(), MatjiImageDownloader.IMAGE_SMALL, holder.preview[i]);
        }
	}
	
    private class PostElement {
        private ProfileImageView profile;
        private TextView nickText; 
        private TextView atText;
        private TextView storeNameText;
        private TextView postText;
        private TextView tagText;
        private TextView dateAgoText;
        private TextView commentCountText;
        private TextView likeCountText;
        private View previews;
        private RelativeLayout[] previewWrapper;
        private ImageView[] preview;
    }
}
