package com.matji.sandwich.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.util.DisplayUtil;
import com.matji.sandwich.util.TimeUtil;
import com.matji.sandwich.widget.SectionListView;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * Adapter for sections.
 * 
 * @author mozziluv
 *
 */
public class PostAdapter extends MBaseAdapter {
	private final DataSetObserver dataSetObserver = new DataSetObserver() {
		@Override
		public void onChanged() {
			super.onChanged();
			updateSessionCache();
		}

		@Override
		public void onInvalidated() {
			super.onInvalidated();
			updateSessionCache();
		};
	};
	private int[] imageIds = {
			R.id.row_post_preview1,
			R.id.row_post_preview2,
			R.id.row_post_preview3
	};

	private final Map<Integer, String> sectionPositions = new LinkedHashMap<Integer, String>();
	private final Map<Integer, Integer> itemPositions = new LinkedHashMap<Integer, Integer>();
	private final Map<View, String> currentViewSections = new HashMap<View, String>();

	private static final int TYPE_POST = 0;
	private static final int TYPE_SECTION = 1;
	private static final int VIEW_TYPE_COUNT = TYPE_SECTION + 1;
	
	private int thumnailSize;
	private static final int MARGIN_THUMNAIL = DisplayUtil.PixelFromDP(11);
	private static final int MARGIN_PREVIEWS = DisplayUtil.PixelFromDP(5);

	private MatjiImageDownloader downloader;

	public PostAdapter(Context context) {
		super(context);
		registerDataSetObserver(dataSetObserver);
		downloader = new MatjiImageDownloader();
		
		thumnailSize = context.getResources().getDimensionPixelSize(R.dimen.thumnail_size);
		Log.d("Matji", thumnailSize+"");
	}

	private boolean isTheSame(final String previousSection, final String newSection) {
		if (previousSection == null) {
			return newSection == null;
		} else {
			return previousSection.equals(newSection);
		}
	}

	private synchronized void updateSessionCache() {
		int currentPosition = 0;
		sectionPositions.clear();
		itemPositions.clear();
		String currentSection = null;
		final int count = data.size();
		for (int i = 0; i < count; i++) {
			final Post post = (Post) data.get(i);
			Date date = TimeUtil.getDateFromCreatedAt(post.getCreatedAt());
			String time = TimeUtil.parseString(date);
			if (!isTheSame(currentSection, time)) {
				sectionPositions.put(currentPosition, time);
				currentSection = time;
				currentPosition++;
			}
			itemPositions.put(currentPosition, i);
			currentPosition++;
		}
	}

	@Override
	public synchronized int getCount() {
		return sectionPositions.size() + itemPositions.size();
	}

	@Override
	public synchronized Object getItem(final int position) {
		if (isSection(position)) {
			return sectionPositions.get(position);
		} else {
			final int linkedItemPosition = getLinkedPosition(position);
			return data.get(linkedItemPosition);
		}
	}

	public synchronized boolean isSection(final int position) {
		return sectionPositions.containsKey(position);
	}

	public synchronized String getSectionName(final int position) {
		if (isSection(position)) {
			return sectionPositions.get(position);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(final int position) {
		if (isSection(position)) {
			return sectionPositions.get(position).hashCode();
		} else {
			return super.getItemId(getLinkedPosition(position));
		}
	}


	@Override
	public int getItemViewType(final int position) {
		if (isSection(position)) {
			return VIEW_TYPE_COUNT - 1;
		}
		return super.getItemViewType(getLinkedPosition(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (isSection(position)) {
			return getSectionView(convertView, sectionPositions.get(position));
		}
		return getItemView(getLinkedPosition(position), convertView, parent);
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT;
	}

	@Override
	public boolean isEnabled(final int position) {
		if (isSection(position)) {
			return false;
		}
		return super.isEnabled(getLinkedPosition(position));
	}

	@Override
	public void clear() {
		super.clear();
		sectionPositions.clear();
		itemPositions.clear();
		currentViewSections.clear();
	}
	
	protected Integer getLinkedPosition(final int position) {
		return itemPositions.get(position);
	}

	private View getSectionView(final View convertView, final String section) {
		View theView = convertView;
		if (theView == null) {
			theView = createNewSectionView();
		}
		setSectionText(section, theView);
		replaceSectionViewsInMaps(section, theView);
		return theView;
	}

	protected void setSectionText(final String section, final View sectionView) {
		final TextView textView = (TextView) sectionView.findViewById(R.id.date_section);
		if (TimeUtil.isToday(section)) {
			textView.setText(context.getResources().getString(R.string.today));
			textView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.section_today));
		} else {
			textView.setText(section);
			textView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.section));
		}
	}

	protected synchronized void replaceSectionViewsInMaps(final String section, final View theView) {
		if (currentViewSections.containsKey(theView)) {
			currentViewSections.remove(theView);
		}
		currentViewSections.put(theView, section);
	}

	protected View createNewSectionView() {
		return inflater.inflate(R.layout.date_section, null);
	}
	
	public View getItemView(int position, View convertView, ViewGroup parent) {
		PostElement postElement;
		Post post = (Post) data.get(position);

		if (convertView == null) {
			postElement = new PostElement();
			final SectionListView sectionListView = (SectionListView) parent;
			convertView = getLayoutInflater().inflate(R.layout.row_post, null);
			postElement.thumnail = (ImageView) convertView.findViewById(R.id.row_post_thumnail);
			postElement.nick = (TextView) convertView.findViewById(R.id.row_post_nick);
			postElement.at = (TextView) convertView.findViewById(R.id.row_post_at);
			postElement.storeName = (TextView)convertView.findViewById(R.id.row_post_store_name);
			postElement.post = (TextView) convertView.findViewById(R.id.row_post_post);
			postElement.tag = (TextView) convertView.findViewById(R.id.row_post_tag);
			postElement.dateAgo = (TextView) convertView.findViewById(R.id.row_post_created_at);
			postElement.commentCount = (TextView) convertView.findViewById(R.id.row_post_comment_count);
			postElement.likeCount = (TextView) convertView.findViewById(R.id.row_post_like_count);
			

			postElement.preview = new ImageView[imageIds.length];
			for (int i = 0; i < postElement.preview.length; i++) {
				postElement.preview[i] = (ImageView) convertView.findViewById(imageIds[i]);
			}

			convertView.setTag(postElement);
			postElement.thumnail.setOnClickListener(sectionListView);
			postElement.nick.setOnClickListener(sectionListView);
			postElement.storeName.setOnClickListener(sectionListView);
			postElement.post.setLinksClickable(false);
			postElement.post.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int position = Integer.parseInt((String) v.getTag());
					sectionListView.onListItemClick(position);
				}
			});

			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int position = Integer.parseInt((String) ((PostElement) v.getTag()).post.getTag());
					sectionListView.onListItemClick(position);
				}
			});
		} else {
			postElement = (PostElement) convertView.getTag();
		}

		setViewItemPosition(postElement, position);
		setViewData(postElement, post);

		return convertView;
	}

	private void setViewItemPosition(PostElement holder, int position) {
		holder.thumnail.setTag(position+"");
		holder.nick.setTag(position+"");
		holder.storeName.setTag(position+"");
		holder.post.setTag(position+"");
	}

	private void setViewData(PostElement holder, Post post) {
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

		ArrayList<Tag> tags = post.getTags();
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
		
		downloader.downloadUserImage(user.getId(), MatjiImageDownloader.IMAGE_SSMALL, holder.thumnail);
		holder.nick.setText(user.getNick()+" ");
		holder.post.setText(post.getPost().trim());
		holder.dateAgo.setText(TimeUtil.getAgoFromSecond(post.getAgo()));
		holder.commentCount.setText(post.getCommentCount() + "");
		holder.likeCount.setText(post.getLikeCount() + "");








		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(MARGIN_PREVIEWS, MARGIN_PREVIEWS, MARGIN_PREVIEWS, MARGIN_PREVIEWS);

		int remainScreenWidth = context.getResources().getDisplayMetrics().widthPixels;

		for (int i = 0; i < holder.preview.length; i++) {
			holder.preview[i].setMaxWidth(remainScreenWidth/imageIds.length - MARGIN_PREVIEWS*2);
			holder.preview[i].setLayoutParams(params);
			//TODO set lisener
//			holder.preview[i].setOnClickListener(this);
		}
	}


	private class PostElement {
		ImageView thumnail;
		TextView nick;
		TextView at;
		TextView storeName;
		TextView post;
		TextView tag;
		TextView dateAgo;
		TextView commentCount;
		TextView likeCount;
		ImageView[] preview;
	}
}