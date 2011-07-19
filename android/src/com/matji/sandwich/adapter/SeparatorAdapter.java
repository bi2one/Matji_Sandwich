package com.matji.sandwich.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.PostForSeparator;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.util.TimeUtil;
import com.matji.sandwich.widget.PostListView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Adapter for Separator.
 * 아직 PostForSeparator 클래스를 사용하는 것에 대해 고려해 봐야 할 듯.
 * 개선 할 수 있다면 개선.
 * 
 * @author mozziluv
 * {@link http://vikaskanani.wordpress.com/2011/02/01/listview-like-android-market-application-with-separator-and-coldfusion-as-web-server/}
 */
public class SeparatorAdapter extends MBaseAdapter {
	private Date lastDate;
	private long totalRowCount = 0;

	private static final int TYPE_POST = 0;	
	private static final int TYPE_SEPARATOR = 1;
	private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;
	private TreeSet mSeparatorsSet = new TreeSet();
	private int count = 0;
	private boolean isCleared = true;
	private MatjiImageDownloader downloader;

	public SeparatorAdapter(Context context) {
		super(context);
		initialize();
	}

	@Override
	public void addAll(ArrayList<MatjiData> items) {
		parsePostList(items);
	}
	
	public void parsePostList(ArrayList posts) {
		int len = posts.size();

		try {
			for (int i = 0; i < len; i++) {
				Post post = (Post) posts.get(i);
				Date tempDate = TimeUtil.getDateFromCreatedAt(post.getCreatedAt());
				if (lastDate == null) {
					PostForSeparator postForSeparator = new PostForSeparator();
					postForSeparator.isHeader = true;
					postForSeparator.postCreatedAt = tempDate;
					addSeparatorItem(postForSeparator);

					postForSeparator = new PostForSeparator();
					postForSeparator.post = post;
					lastDate = tempDate;
					addItem(postForSeparator);
				} else if (lastDate.getDate() == tempDate.getDate()
						&& lastDate.getMonth() == tempDate.getMonth()
						&& lastDate.getYear() == tempDate.getYear()) {
					PostForSeparator postForSeparator = new PostForSeparator();
					postForSeparator.post = post;
					addItem(postForSeparator);
				} else {
					PostForSeparator postForSeparator = new PostForSeparator();
					postForSeparator.isHeader = true;
					postForSeparator.postCreatedAt = tempDate;
					addSeparatorItem(postForSeparator);
					lastDate = tempDate;
					
					postForSeparator = new PostForSeparator();
					postForSeparator.post = post;
					addItem(postForSeparator);
				}

			}
		} catch (Exception e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
			Log.e(e.getClass().getName(), e.getMessage(), e);
			totalRowCount = 0;
		}
	}
	
	
	
	
	public void initialize() {
		downloader = new MatjiImageDownloader();
		isCleared = false;
		count = 0;
		lastDate = null;
		mSeparatorsSet.clear();
		isCleared = true;
	}

	public void addItem(MatjiData item) {
		data.add(item);
		count += 1;
		notifyDataSetChanged();
	}
	
	public void addSeparatorItem(MatjiData item) {
		data.add(item);
		mSeparatorsSet.add(data.size() - 1);
		notifyDataSetChanged();
	}

	public int getItemViewType(int position) {
		return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_POST;
	}

	public int getViewTypeCount() {
		return TYPE_MAX_COUNT;
	}

	public int getCount() {
		return data.size();
	}

	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public boolean isEnabled(int position) {
		return mSeparatorsSet.contains(position) ? false : true;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		PostElement postElement;
		PostForSeparator postForSeparator = (PostForSeparator) data.get(position);
		Post post = postForSeparator.post;
		int type = getItemViewType(position);
		
		if (convertView == null) {
			postElement = new PostElement();
			final PostListView postListView = (PostListView)parent;

			switch (type) {
			case TYPE_POST:				
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

			case TYPE_SEPARATOR:
				convertView = inflater.inflate(R.layout.date_separator, null);
				postElement.dateAgo = (TextView) convertView.findViewById(R.id.date_separator);
				break;
			}

			convertView.setTag(postElement);
		} else {
			postElement = (PostElement) convertView.getTag();
		}
		switch (type) {
		case TYPE_POST:			
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
			break;
		case TYPE_SEPARATOR:
			try {
				postElement.dateAgo.setText(
						android.text.format.DateFormat.format("MMM dd, yyyy", postForSeparator.postCreatedAt));
			} catch (Exception e) {
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
				Log.e(e.getClass().getName(), e.getMessage(), e);
			}
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
		TextView imageCount;
		TextView tagCount;
	}
}