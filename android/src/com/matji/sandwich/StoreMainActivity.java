package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.Bookmark;
import com.matji.sandwich.data.Like;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.StoreFood;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.data.User;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.BookmarkHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.LikeHttpRequest;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.ModelType;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class StoreMainActivity extends MainActivity implements Requestable {
	private TabHost tabHost;
	private Store store;

	private HttpRequestManager manager;
	private HttpRequest request;
	private Session session;
	private DBProvider dbProvider;

	private ImageView storeImage;
	private Button likeButton;
	private TextView likeCountText;
	private TextView addressText;
	private TextView coverText;
	private TextView foodText;
	private TextView tagText;
	private TextView regUserText;
	private TextView ownerUserText;
	private Button scrapButton;
	private Button postButton;
	private Button imageButton;
	private Button tagButton;
	private Button urlButton;

	/* request tags */
	public final static int BOOKMARK_REQUEST = 10;
	public final static int UN_BOOKMARK_REQUEST = 11;
	public final static int LIKE_REQUEST = 12;
	public final static int UN_LIKE_REQUEST = 13;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_main);

		tabHost = ((TabActivity) getParent()).getTabHost();
		store = (Store) SharedMatjiData.getInstance().top();

		manager = HttpRequestManager.getInstance(this);
		session = Session.getInstance(this);
		dbProvider = DBProvider.getInstance(this);

		storeImage = (ImageView) findViewById(R.id.store_main_thumnail);
		likeButton = (Button) findViewById(R.id.store_main_like_btn);
		likeCountText = (TextView) findViewById(R.id.store_main_like_count);
		addressText = (TextView) findViewById(R.id.store_main_address);
		coverText = (TextView) findViewById(R.id.store_main_cover);
		foodText = (TextView) findViewById(R.id.store_main_popular_menu);
		tagText = (TextView) findViewById(R.id.store_main_tag);
		regUserText = (TextView) findViewById(R.id.store_main_reg_user);
		ownerUserText = (TextView) findViewById(R.id.store_main_owner_user);
		scrapButton = (Button) findViewById(R.id.store_main_scrap_btn);
		postButton = (Button) findViewById(R.id.store_main_memo_btn);
		imageButton = (Button) findViewById(R.id.store_main_image_btn);
		tagButton = (Button) findViewById(R.id.store_main_tag_btn);
		urlButton = (Button) findViewById(R.id.store_main_url_btn);

		/* Set RegUser */
		User regUser = store.getRegUser();
		String string;
		if (regUser == null) {
			string = getString(R.string.default_string_not_exist_reg_user);
			regUserText.setText(string);
		} else {
			string = getString(R.string.default_string_reg_user) + ": " + regUser.getNick();
			regUserText.setText(string);
		}		

		/* Set Owner User */
		//		User ownerUser = store.getOwnerUser();
		User ownerUser = null;
		if (ownerUser != null) {
			string = getString(R.string.default_string_owner_user) + ": " + regUser.getNick();
			ownerUserText.setText(string);
		} else {
			ownerUserText.setVisibility(TextView.GONE);
		}

		setInfo();
	}

	private void setInfo() {
		store = (Store) SharedMatjiData.getInstance().top();

		/* Set StoreImage */
		AttachFile file = store.getFile();
		if (file != null) {
			downloader.downloadAttachFileImage(file.getId(), MatjiImageDownloader.IMAGE_SMALL, storeImage);
		} else {
			Drawable defaultImage = getResources().getDrawable(R.drawable.thumnail_bg);
			storeImage.setImageDrawable(defaultImage);
		}

		addressText.setText(store.getAddress());
		coverText.setText(store.getCover());
		foodText.setText(foodListToCSV(store.getStoreFoods()));
		tagText.setText(tagListToCSV(store.getTags()));

		if (session.isLogin()) {
			if (dbProvider.isExistLike(store.getId(), "Store")) {
				likeButton.setText(R.string.store_main_unlike_store);
			} else {
				likeButton.setText(R.string.store_main_like_store);
			}

			if (dbProvider.isExistBookmark(store.getId(), "Store")) {
				scrapButton.setText(R.string.store_main_unbookmark);
			} else {
				scrapButton.setText(R.string.store_main_bookmark);
			}
		} else {
			likeButton.setText(R.string.store_main_like_store);
			scrapButton.setText(R.string.store_main_bookmark);
		}

		likeCountText.setText(store.getLikeCount()+"");

		/* Set ETC Button String */
		postButton.setText(getCountNumberOf(R.string.default_string_memo, store.getPostCount()));
		imageButton.setText(getCountNumberOf(R.string.default_string_image, store.getImageCount()));
		tagButton.setText(getCountNumberOf(R.string.default_string_tag, store.getTagCount()));	
		urlButton.setText(getCountNumberOf(R.string.default_string_url, store.getUrlCount()));
	}

	public void onResume() {
		super.onResume();

		setInfo();
	}

	private void bookmarkRequest() {
		if (request == null || !(request instanceof BookmarkHttpRequest)) {
			request = new BookmarkHttpRequest(this);
		}
		((BookmarkHttpRequest) request).actionBookmark(store.getId());
		manager.request(this, request, BOOKMARK_REQUEST, this);
		store.setBookmarkCount(store.getBookmarkCount() + 1);
	}

	private void unbookmarkReuqest() {
		if (request == null || !(request instanceof BookmarkHttpRequest)) {
			request = new BookmarkHttpRequest(this);
		}
		((BookmarkHttpRequest) request).actionUnBookmark(store.getId());
		manager.request(this, request, UN_BOOKMARK_REQUEST, this);
		store.setBookmarkCount(store.getBookmarkCount() - 1);
	}

	private void likeRequest() {
		if (request == null || !(request instanceof LikeHttpRequest)) {
			request = new LikeHttpRequest(this);
		}
		((LikeHttpRequest) request).actionStoreLike(store.getId());
		manager.request(this, request, LIKE_REQUEST, this);
	}

	private void unlikeRequest() {
		if (request == null || !(request instanceof LikeHttpRequest)) {
			request = new LikeHttpRequest(this);
		}

		((LikeHttpRequest) request).actionStoreUnLike(store.getId());
		manager.request(this, request, UN_LIKE_REQUEST, this);
	}

	private void postBookmarkRequest() {
		User me = session.getCurrentUser();
		
		Bookmark bookmark = new Bookmark();
		bookmark.setForeignKey(store.getId());
		bookmark.setObject("Store");
		dbProvider.insertBookmark(bookmark);		
		me.setStoreCount(me.getStoreCount() + 1);
		scrapButton.setClickable(true);
	}
	
	private void postUnBookmarkRequest() {
		User me = session.getCurrentUser();
		
		dbProvider.deleteBookmark(store.getId(), "Store");
		me.setStoreCount(me.getStoreCount() - 1);
		scrapButton.setClickable(true);
	}
	
	private void postLikeRequest() {
		Like like = new Like();
		like.setForeignKey(store.getId());
		like.setObject("Store");
		dbProvider.insertLike(like);
		store.setLikeCount(store.getLikeCount() + 1);
		likeButton.setClickable(true);
	}

	private void postUnLikeRequest() {
		dbProvider.deleteLike(store.getId(), "Store");
		store.setLikeCount(store.getLikeCount() - 1);
		likeButton.setClickable(true);
	}
	
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch (tag) {
		case BOOKMARK_REQUEST:
			postBookmarkRequest();
			break;
		case UN_BOOKMARK_REQUEST:
			postUnBookmarkRequest();
			break;
		case LIKE_REQUEST:
			postLikeRequest();
			break;
		case UN_LIKE_REQUEST:
			postUnLikeRequest();
			break;
		}

		setInfo();
	}


	public void requestExceptionCallBack(int tag, MatjiException e) {
		likeButton.setClickable(true);
		scrapButton.setClickable(true);
		e.showToastMsg(getApplicationContext());
	}

	public void onLikeButtonClicked(View view) {
		if (loginRequired()) {
			if (!manager.isRunning(this)) {
				likeButton.setClickable(false);
				if (dbProvider.isExistLike(store.getId(), "Store")){
					// api request
					unlikeRequest();
				}else {
					// api request
					likeRequest();
				}
			}
		}
	}

	public void onScrapButtonClicked(View view) {
		if (loginRequired()) {
			if (!manager.isRunning(this)) {
				scrapButton.setClickable(false);
				if (dbProvider.isExistBookmark(store.getId(), "Store")){
					// api request
					unbookmarkReuqest();
				}else {
					// api request
					bookmarkRequest();
				}
			}
		}
	}

	public void onMapButtonClicked(View view) {
	}

	public void onPhoneButtonClicked(View view) {
		if (store.getTel() != null) {
			try {
				startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + store.getTel())));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void onWebButtonClicked(View view) {
		String web = store.getWebsite();
		if (web != null && !web.equals("")) {
			if (!web.subSequence(0, 3).equals("http")) {
				web = "http://" + web;
			}

			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
			startActivity(intent);				
		}
	}

	public void onInfoViewButtonClicked(View view) {
		Intent intent = new Intent(this, StoreDetailInfoTabActivity.class);
		startActivity(intent);
	}

	public void onMenuViewButtonClicked(View view) {
		tabHost.setCurrentTab(StoreTabActivity.MENU_PAGE);
	}

	public void onMemoButtonClicked(View view) {
		tabHost.setCurrentTab(StoreTabActivity.MEMO_PAGE);
	}

	public void onImageButtonClicked(View view) {
		tabHost.setCurrentTab(StoreTabActivity.IMAGE_PAGE);
	}

	public void onTagButtonClicked(View view) {
		Intent intent = new Intent(this, TagListActivity.class);
		intent.putExtra("id", store.getId());
		intent.putExtra("type", ModelType.STORE);
		startActivity(intent);
	}

	public void onUrlButtonClicked(View view) {
		Intent intent = new Intent(this, StoreUrlListActivity.class);
		intent.putExtra("store_id", store.getId());
		startActivity(intent);
	}

	public String tagListToCSV(ArrayList<Tag> tags) {
		String result = "";
		if (tags.size() > 0) {
			int i;
			for (i = 0; i < tags.size(); i++) {
				if (tags.get(i) != null) {
					result += tags.get(i).getTag();
					break;
				}
			}

			for (i = i+1; i < tags.size(); i++) {
				if (tags.get(i) != null) {
					result += ", " + tags.get(i).getTag();
				}
			}
		}

		return result;
	}

	public String foodListToCSV(ArrayList<StoreFood> foods) {
		String result = "";
		if (foods.size() > 0) {
			int i;
			for (i = 0; i < foods.size(); i++) {
				if (foods.get(i).getFood() != null) {
					result += foods.get(i).getFood().getName();
					break;
				}
			}

			for (i = i+1; i < foods.size(); i++) {
				if (foods.get(i).getFood() != null) {
					result += ", " + foods.get(i).getFood().getName();
				}
			}
		}

		return result;
	}
}