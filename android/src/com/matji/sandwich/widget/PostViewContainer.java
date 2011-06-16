package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.matji.sandwich.ImageSliderActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.StoreTabActivity;
import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.AttachFileHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.util.DisplayUtil;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.http.util.TimeStamp;

public class PostViewContainer extends ViewContainer implements OnClickListener, Requestable {
	private MatjiImageDownloader downloader;
	private HttpRequestManager manager;
	private HttpRequest request; 
	private int[] attachFileIds;

	private Context context;
	private Activity activity;
	private Post post;
	private User user;
	private Store store;

	private TextView dateAgo;
	private ImageView[] preview;

	private static final int ATTACH_FILE_IDS_REQUEST = 1;
	private static final int NUMBER_OF_PREVIEW = 3;
	
	private static final int THUMNAIL_SIZE = DisplayUtil.PixelFromDP(80);
	private static final int MARGIN_THUMNAIL = DisplayUtil.PixelFromDP(3);
	private static final int MARGIN_PREVIEWS = DisplayUtil.PixelFromDP(5);

	// TODO 태그도 추가
	public PostViewContainer(Context context, Activity activity, Post post, User user, Store store) {
		super(context, R.layout.header_post);
		this.context = context;
		this.activity = activity;
		this.post = post;
		this.user = user;
		this.store = store;

		initPostData();
	}

	private void initPostData() {
		downloader = new MatjiImageDownloader();
		manager = new HttpRequestManager(activity, this);
		request = new AttachFileHttpRequest(activity);
		
		dateAgo = (TextView) getRootView().findViewById(R.id.post_adapter_created_at);
		
		/* Set Previews */
		preview = new ImageView[NUMBER_OF_PREVIEW];
		preview[0] = (ImageView) getRootView().findViewById(R.id.header_post_preview1);
		preview[1] = (ImageView) getRootView().findViewById(R.id.header_post_preview2);
		preview[2] = (ImageView) getRootView().findViewById(R.id.header_post_preview3);

		ImageView thumnail = (ImageView) getRootView().findViewById(R.id.post_adapter_thumnail);
		TextView nick = (TextView) getRootView().findViewById(R.id.post_adapter_nick);
		TextView storeName = (TextView) getRootView().findViewById(R.id.post_adapter_store_name);
		TextView content = (TextView) getRootView().findViewById(R.id.post_adapter_post);

		downloader.downloadUserImage(user.getId(), thumnail);
		nick.setText(user.getNick());

		if (store != null)
			storeName.setText(" @" + store.getName());
		else 
			storeName.setText("");
		content.setText(post.getPost());

		thumnail.setOnClickListener(this);
		nick.setOnClickListener(this);
		storeName.setOnClickListener(this);

		int paddingLeft = THUMNAIL_SIZE + MARGIN_THUMNAIL;
		
		LinearLayout previews = (LinearLayout) getRootView().findViewById(R.id.header_post_previews);
		previews.setPadding(paddingLeft, 0, 0, 0);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(MARGIN_PREVIEWS, MARGIN_PREVIEWS, MARGIN_PREVIEWS, MARGIN_PREVIEWS);

		int remainScreenWidth = context.getResources().getDisplayMetrics().widthPixels - paddingLeft;

		Log.d("Matji", paddingLeft + "," + remainScreenWidth);
		for (int i = 0; i < preview.length; i++) {
			preview[i].setMaxWidth(remainScreenWidth/3 - MARGIN_PREVIEWS/2);
			preview[i].setLayoutParams(params);
			preview[i].setOnClickListener(this);
		}

		setPostData();
	}

	private void setPostData() {
		dateAgo.setText(TimeStamp.getAgoFromDate(post.getCreatedAt()));
		attachFileIdsRequest();
	}


	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.post_adapter_thumnail: case R.id.post_adapter_nick:
			gotoUserPage(post);
			break;
		case R.id.post_adapter_store_name:
			gotoStorePage(post);
			break;
		case R.id.header_post_preview1:
			Log.d("Matij", "AA");
			callImageViewer(0);
			break;
		case R.id.header_post_preview2:
			callImageViewer(1);
			break;
		case R.id.header_post_preview3:
			callImageViewer(2);
			break;
		}
	}

	protected void gotoUserPage(Post post) {
		Intent intent = new Intent(context, UserTabActivity.class);
		((BaseActivity) context).startActivityWithMatjiData(intent, post.getUser());
	}	

	protected void gotoStorePage(Post post) {
		Intent intent = new Intent(context, StoreTabActivity.class);
		((BaseActivity) context).startActivityWithMatjiData(intent, post.getStore());
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public void attachFileIdsRequest() {
		manager.request(activity, attachFileIdsRequestSet(), ATTACH_FILE_IDS_REQUEST);
	}

	public HttpRequest attachFileIdsRequestSet() {
		((AttachFileHttpRequest) request).actionPostList(post.getId());
		return request;
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch (tag) {
		case ATTACH_FILE_IDS_REQUEST:
			/* Set AttachFile ID */
			attachFileIds = new int[data.size()];
			for (int i = 0; i < data.size(); i++) {
				attachFileIds[i] = ((AttachFile) data.get(i)).getId();
			}
			
			for (int i = 0; i < ((data.size() > preview.length) ? preview.length : data.size()); i++) {
				downloader.downloadAttachFileImage(attachFileIds[i], MatjiImageDownloader.IMAGE_MEDIUM, preview[i]);
				preview[i].setVisibility(View.VISIBLE);
			}
			break;
		}
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(context);
	}

	public void callImageViewer(int position) {
		Intent viewerIntent = new Intent(activity, ImageSliderActivity.class);
		viewerIntent.putExtra("attach_file_ids", attachFileIds);
		viewerIntent.putExtra("position", position);
		activity.startActivity(viewerIntent);
	}
}