package com.matji.sandwich.adapter;

import java.util.ArrayList;
import java.util.Date;

import com.matji.sandwich.CommentActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.base.Base;
import com.matji.sandwich.data.Like;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.SimpleTag;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.LikeHttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.listener.GotoImageSliderAction;
import com.matji.sandwich.listener.GotoStoreMainAction;
import com.matji.sandwich.listener.GotoUserMainAction;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.TimeUtil;
import com.matji.sandwich.widget.ProfileImageView;
import com.matji.sandwich.widget.dialog.ActionItem;
import com.matji.sandwich.widget.dialog.QuickActionDialog;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

	private void setActions(PostElement holder, final int position) {
		action1 = new GotoUserMainAction(context, ((Post) data.get(position)).getUser());
		action2 = new GotoStoreMainAction(context, ((Post) data.get(position)).getStore());
		action3 = new GotoImageSliderAction(context, ((Post) data.get(position)).getAttachFiles());

		holder.profile.setOnClickListener(action1);
		holder.nick.setOnClickListener(action1);
		holder.storeName.setOnClickListener(action2);
		holder.post.setLinksClickable(false);
		holder.menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new PostQuickActionDialog(position).show(v);
			}
		});

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
			Intent intent = new Intent(context, CommentActivity.class);
			intent.putExtra(CommentActivity.POSTS, data);
			intent.putExtra(CommentActivity.POSITION, position);
			context.startActivity(intent);
		}
	}

	// 넣어야하나...
	public void refresh() {
		notifyDataSetChanged();
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

	/**
	 * QuickAction 버튼을 클릭했을 때, 다이얼로그를 보여주고
	 * 다이얼로그 내의 버튼을 클릭했을 때 각 버튼에 대한 행동을 취한다.
	 *  
	 * @author mozziluv
	 *
	 */
	class PostQuickActionDialog implements Requestable {
		private QuickActionDialog quickaction;
		private Session session;
		private HttpRequest request;
		private HttpRequestManager manager;
		private DBProvider dbProvider;

		private int position;

		private static final int LIKE_REQUEST = 10;
		private static final int UN_LIKE_REQUEST = 11;
		private static final int DELETE_REQUEST = 12;
		
		/**
		 * 기본 생성자.
		 * 
		 * @param position 현재 QuickAction 버튼이 눌린 position
		 */
		public PostQuickActionDialog(int position) {
			this.quickaction = new QuickActionDialog(context);
			this.session = Session.getInstance(context);
			this.manager = HttpRequestManager.getInstance(context);
			this.dbProvider = DBProvider.getInstance(context);

			this.position = position;

			// 아이템(버튼) 추가.
			ActionItem commentAction = new ActionItem();
			commentAction.setIcon(MatjiConstants.drawable(R.drawable.btn_home));
			quickaction.addActionItem(commentAction);

			ActionItem likeAction = new ActionItem();
			likeAction.setIcon(MatjiConstants.drawable(R.drawable.btn_like));
			quickaction.addActionItem(likeAction);

			// 자신이 작성한 이야기일 경우 수정, 삭제 버튼도 추가.
			if (isMine((Post) data.get(position))) {
				ActionItem editAction = new ActionItem();
				editAction.setIcon(MatjiConstants.drawable(R.drawable.btn_notification));
				quickaction.addActionItem(editAction);

				ActionItem deleteAction = new ActionItem();
				deleteAction.setIcon(MatjiConstants.drawable(R.drawable.btn_delete));
				quickaction.addActionItem(deleteAction);
			}

			// 클릭 리스너 등록.
			quickaction.setOnActionItemClickListener(new QuickActionDialog.OnActionItemClickListener() {

				@Override
				public void onItemClick(int pos) {
					if (((Base) context).loginRequired()) {
						if (pos == 0) {
							Log.d("Matji", "comment button click");
							gotoCommentActivity();
						} else if (pos == 1) {
							Log.d("Matji", "like button click");
							likePost();
						} else if (pos == 2) {
							Log.d("Matji", "edit button click");

						} else if (pos == 3) {
							Log.d("Matji", "delete button click");
							deletePost();
						}
					}
				}
			});
		}

		/**
		 * 댓글 작성 버튼을 클릭했을 때,
		 * 댓글 페이지로 이동하고 키보드가 바로 올라오도록 한다.
		 */
		public void gotoCommentActivity() {
			Intent intent = new Intent(context, CommentActivity.class);
			intent.putExtra(CommentActivity.POSTS, data);
			intent.putExtra(CommentActivity.POSITION, position);
			intent.putExtra(CommentActivity.SHOW_KEYBOARD, true);
			context.startActivity(intent);
		}

		/**
		 * 좋아요 버튼을 클릭했을 때,
		 * 현재 이 이야기를 좋아요 했는지를 판단한 후 그에 따라 처리한다.
		 */
		public void likePost() {
			if (!manager.isRunning(context)) {
				Post post = (Post) data.get(position);
				if (dbProvider.isExistLike(post.getId(), DBProvider.POST)){
					unlikeRequest(post);
				} else {
					likeRequest(post);
				}
			}
		}
		
		/**
		 * Like 요청
		 * @param post Like할 이야기
		 */
		private void likeRequest(Post post) {
			if (request == null || !(request instanceof LikeHttpRequest)) {
				request = new LikeHttpRequest(context);
			}

			((LikeHttpRequest) request).actionPostLike(post.getId());
			manager.request(context, request, LIKE_REQUEST, this);
			((Post) data.get(position)).setLikeCount(post.getLikeCount() + 1);
		}

		/**
		 * Unlike 요청
		 * @param post UnLike할 이야기
		 */
		private void unlikeRequest(Post post) {
			if (request == null || !(request instanceof LikeHttpRequest)) {
				request = new LikeHttpRequest(context);
			}

			((LikeHttpRequest) request).actionPostUnLike(post.getId());
			manager.request(context, request, UN_LIKE_REQUEST, this);
			((Post) data.get(position)).setLikeCount(post.getLikeCount() - 1);
		}

		/**
		 * 이야기 삭제 버튼을 클릭했을 때,
		 * 현재 이 이야기를 삭제할 것인지를 묻는 Alert 윈도우를 띄우고,
		 * 확인 했을 때 삭제요청을 한다.
		 */
		public void deletePost() {
			// Alert 창 띄우기.
			if (!manager.isRunning(context)) {
				Post post = (Post) data.get(position);
				if (request == null || !(request instanceof PostHttpRequest)) {
					request = new PostHttpRequest(context);
				}
				
				((PostHttpRequest) request).actionDelete(post.getId());
				manager.request(context, request, DELETE_REQUEST, this);
			}
		}		

		public boolean isMine(Post post) {
			return session.isLogin() && session.getCurrentUser().getId() == post.getUserId();
		}

		public void show(View v) {
			quickaction.show(v);
		}

		@Override
		public void requestCallBack(int tag, ArrayList<MatjiData> data) {

			switch (tag) {
			// TODO Auto-generated method stub
			case LIKE_REQUEST:
				Like like = new Like();
				like.setForeignKey(((Post) PostSectionedAdapter.this.data.get(position)).getId());
				like.setObject(DBProvider.POST);
				dbProvider.insertLike(like);
				break;
			case UN_LIKE_REQUEST:
				dbProvider.deleteLike(((Post) PostSectionedAdapter.this.data.get(position)).getId(), DBProvider.POST);
				break;
			case DELETE_REQUEST:
				PostSectionedAdapter.this.data.remove(position);
				break;
			}
			notifyDataSetChanged();
		}

		@Override
		public void requestExceptionCallBack(int tag, MatjiException e) {
			// TODO Auto-generated method stub
			e.performExceptionHandling(context);
		}
	}
}