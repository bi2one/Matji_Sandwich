package com.matji.sandwich.adapter;

import java.util.ArrayList;
import java.util.Date;

import com.matji.sandwich.PostMainActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.SimpleTag;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.listener.GotoImageSliderAction;
import com.matji.sandwich.listener.GotoStoreMainAction;
import com.matji.sandwich.listener.GotoUserMainAction;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.TimeUtil;
import com.matji.sandwich.widget.ProfileImageView;
import com.matji.sandwich.widget.dialog.PostQuickActionDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PostSectionedAdapter extends SectionedAdapter {
	
	private int[] imageIds = {
			R.id.row_post_preview1,
			R.id.row_post_preview2,
			R.id.row_post_preview3
	};

	private MatjiImageDownloader downloader;
	private int profileSize;
	private static final int MARGIN_PREVIEWS = (int) MatjiConstants.dimen(R.dimen.row_post_previews_margin);
	
	private GotoUserMainAction action1;
	private GotoStoreMainAction action2;
	private GotoImageSliderAction action3;
	private PostQuickActionDialog action4;

	public PostSectionedAdapter(Context context) {
		super(context);
		init();
	}

	protected void init() {
		downloader = new MatjiImageDownloader(context);
		profileSize = (int) MatjiConstants.dimen(R.dimen.profile_size);
	}

	@Override
	public View getSectionView(View convertView, String section) {
		PostSectionElement postSectionElement;

		if (convertView == null) {
			postSectionElement = new PostSectionElement();
			convertView = inflater.inflate(R.layout.row_date_section, null);

			postSectionElement.subject = (TextView)convertView.findViewById(R.id.row_date_section);
			convertView.setTag(postSectionElement);
		} else {
			postSectionElement = (PostSectionElement)convertView.getTag();
		}

		if (TimeUtil.isToday(section)) {
			postSectionElement.subject.setText(context.getResources().getString(R.string.today));
			postSectionElement.subject.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.section_today));
		} else {
			postSectionElement.subject.setText(section);
			postSectionElement.subject.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.section));
		}

		return convertView;
	}

	@Override
	public View getItemView(final int position, View convertView, final ViewGroup parent) {
		PostElement postElement;

		if (convertView == null) {
			postElement = new PostElement();
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
			postElement.menu = (ImageButton) convertView.findViewById(R.id.row_post_menu_btn);

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
			
		} else {
			postElement = (PostElement) convertView.getTag();
		}

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onListItemClick(position);
			}
		});
		
		setActions(postElement, position);
		setViewData(postElement, position);

		return convertView;
	}

	@Override
	public String putSectionName(int position) {
		Post post = (Post) data.get(position);
		Date date = TimeUtil.getDateFromCreatedAt(post.getCreatedAt());
		String section = TimeUtil.parseString(date);
		return section;
	}
	
	private void setActions(PostElement holder, int position) {
		action1 = new GotoUserMainAction(context, ((Post) data.get(position)).getUser());
		action2 = new GotoStoreMainAction(context, ((Post) data.get(position)).getStore());
		action3 = new GotoImageSliderAction(context, ((Post) data.get(position)).getAttachFiles());
		action4 = new PostQuickActionDialog(context, (Post) data.get(position));
		
		holder.profile.setOnClickListener(action1);
		holder.nick.setOnClickListener(action1);
		holder.storeName.setOnClickListener(action2);
		holder.post.setLinksClickable(false);
		holder.menu.setOnClickListener(action4);

		for (int i = 0; i < holder.previews.length; i++) {
			holder.previews[i].setOnClickListener(action3);
		}
	}
	
	private void setViewData(PostElement holder, int position) {
		Post post = (Post) data.get(position);
		
		setPreviews(post, holder.previews);

		Store store = post.getStore();
		User user = post.getUser();

		if (store != null) {
			holder.at.setVisibility(View.VISIBLE);
			holder.storeName.setText(" "+store.getName());
		}
		else {
			holder.at.setVisibility(View.GONE);
			holder.storeName.setText("");
		}

		ArrayList<SimpleTag> tags = post.getTags();
		String tagResult = "";

		if (tags.size() > 0) {
			tagResult += tags.get(0).getTag();
			for (int i = 1; i < tags.size(); i++) {
				tagResult += ", " + tags.get(i).getTag();
			}
			holder.tag.setText(tagResult);
			holder.tag.setVisibility(View.VISIBLE);
		} else {
			holder.tag.setVisibility(View.GONE);
		}

		holder.profile.setUserId(user.getId());
		holder.nick.setText(user.getNick()+" ");
		holder.post.setText(post.getPost().trim());
		holder.dateAgo.setText(TimeUtil.getAgoFromSecond(post.getAgo()));
		holder.commentCount.setText(post.getCommentCount() + "");
		holder.likeCount.setText(post.getLikeCount() + "");
	}

	public void setPreviews(Post post, ImageView[] previews) {
		int[] attachFileIds = new int[post.getAttachFiles().size()];
		for (int i = 0; i < attachFileIds.length; i++) {
			attachFileIds[i] = post.getAttachFiles().get(i).getId();
		}

		for (int i = 0; i < previews.length; i++) {
			previews[i].setVisibility(View.GONE);
			previews[i].setTag(i+"");
		}

		for (int i = 0; i < ((attachFileIds.length > previews.length) ? previews.length : attachFileIds.length); i++) {
			downloader.downloadAttachFileImage(attachFileIds[i], MatjiImageDownloader.IMAGE_MEDIUM, previews[i]);
			previews[i].setVisibility(View.VISIBLE);
		}		
	}
	
	private void onListItemClick(int position) {
		Post post = (Post) data.get(position);
		if (post.getActivityId() == 0) {
			Intent intent = new Intent(context, PostMainActivity.class);
			intent.putExtra(PostMainActivity.POSITION, position);
			intent.putExtra(PostMainActivity.POST, (Parcelable) post);
			context.startActivity(intent);
		}
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
		ImageButton menu;
	}

	private class PostSectionElement {
		TextView subject;
	}
}