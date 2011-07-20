package com.matji.sandwich.adapter;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.util.TimeUtil;
import com.matji.sandwich.widget.SectionListView;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapter for sections.
 * 
 * @author mozziluv
 *
 */
public class SectionAdapter extends MBaseAdapter {
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

	private final Map<Integer, String> sectionPositions = new LinkedHashMap<Integer, String>();
	private final Map<Integer, Integer> itemPositions = new LinkedHashMap<Integer, Integer>();
	private final Map<View, String> currentViewSections = new HashMap<View, String>();

	private static final int TYPE_POST = 0;
	private static final int TYPE_SECTION = 1;
	private static final int VIEW_TYPE_COUNT = TYPE_SECTION + 1;
	
	private MatjiImageDownloader downloader;

	public SectionAdapter(Context context) {
		super(context);
		registerDataSetObserver(dataSetObserver);
		downloader = new MatjiImageDownloader();
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

	protected Integer getLinkedPosition(final int position) {
		return itemPositions.get(position);
	}

	@Override
	public int getItemViewType(final int position) {
		if (isSection(position)) {
			return VIEW_TYPE_COUNT - 1;
		}
		return super.getItemViewType(getLinkedPosition(position));
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (isSection(position)) {
			return getSectionView(convertView, sectionPositions.get(position));
		}
		return getItemView(getLinkedPosition(position), convertView, parent);
	}

	public View getItemView(int position, View convertView, ViewGroup parent) {
		PostElement postElement;
		Post post = (Post) data.get(position);
		
		if (convertView == null) {
			postElement = new PostElement();
			final SectionListView sectionListView = (SectionListView) parent;
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

		Store store = post.getStore();
		User user = post.getUser();

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
		postElement.imageCount.setText(post.getImageCount() + "");
		postElement.tagCount.setText(post.getTagCount() + "");

		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT;
	}

	@Override
	public boolean isEnabled(final int position) {
		if (isSection(position)) {
			return true;
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