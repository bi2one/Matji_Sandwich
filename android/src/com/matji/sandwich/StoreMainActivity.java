package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.Bookmark;
import com.matji.sandwich.data.Like;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.data.User;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.BookmarkHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.LikeHttpRequest;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.http.util.ModelType;
import com.matji.sandwich.session.Session;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
	private TextView tagText;
	private TextView regUserText;
	private TextView ownerUserText;
	private Button scrapButton;
	private Button postButton;
	private Button imageButton;
	private Button tagButton;
	private Button urlButton;

	/* request tags */
	public final static int BOOKMARK_REQUEST = 1;
	public final static int UN_BOOKMARK_REQUEST = 2;
	public final static int LIKE_REQUEST = 3;
	public final static int UN_LIKE_REQUEST = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_main);
		
		tabHost = ((TabActivity) getParent()).getTabHost();
		store = (Store) SharedMatjiData.getInstance().top();

		manager = new HttpRequestManager(this, this);
		session = Session.getInstance(this);
		dbProvider = DBProvider.getInstance(this);

		storeImage = (ImageView) findViewById(R.id.store_main_thumnail);
		likeButton = (Button) findViewById(R.id.store_main_like_btn);
		likeCountText = (TextView) findViewById(R.id.store_main_like_count);
		addressText = (TextView) findViewById(R.id.store_main_address);
		coverText = (TextView) findViewById(R.id.store_main_cover);
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
		User ownerUser = store.getRegUser();
		if (ownerUser != null) {
			string = getString(R.string.default_string_owner_user) + ": " + regUser.getNick();
			ownerUserText.setText(string);
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
			Drawable defaultImage = getResources().getDrawable(R.drawable.img_matji_default);
			storeImage.setImageDrawable(defaultImage);
		}

		addressText.setText(store.getAddress());
		coverText.setText(store.getCover());
		tagText.setText(tagListToCSV(store.getTags()));
		
		if (session.isLogin()) {
			if (dbProvider.isExistLike(store.getId(), "Store")) {
				likeButton.setText(getString(R.string.store_main_unlike_store));
			} else {
				likeButton.setText(getString(R.string.store_main_like_store));
			}

			if (dbProvider.isExistBookmark(store.getId(), "Store")) {
				scrapButton.setText(getString(R.string.store_main_unbookmark));
			} else {
				scrapButton.setText(getString(R.string.store_main_bookmark));
			}
		} else {
			likeButton.setText(getString(R.string.store_main_like_store));
			scrapButton.setText(getString(R.string.store_main_bookmark));
		}

		likeCountText.setText(store.getLikeCount()+"");

		/* Set ETC Button String */
		postButton.setText(getCountNumberOf(R.string.default_string_memo, store.getPostCount()));
		imageButton.setText(getCountNumberOf(R.string.default_string_image, store.getImageCount()));
		tagButton.setText(getCountNumberOf(R.string.default_string_tag, store.getTagCount()));	
		urlButton.setText(getCountNumberOf(R.string.default_string_url, 0));
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
		manager.request(this, request, BOOKMARK_REQUEST);
		store.setBookmarkCount(store.getBookmarkCount() + 1);
		User me = session.getCurrentUser();
		me.setStoreCount(me.getStoreCount() + 1);
	}

	private void unbookmarkReuqest() {
		if (request == null || !(request instanceof BookmarkHttpRequest)) {
			request = new BookmarkHttpRequest(this);
		}
		((BookmarkHttpRequest) request).actionUnBookmark(store.getId());
		manager.request(this, request, UN_BOOKMARK_REQUEST);
		store.setBookmarkCount(store.getBookmarkCount() - 1);
		User me = session.getCurrentUser();
		me.setStoreCount(me.getStoreCount() - 1);
	}

	private void likeRequest() {
		if (request == null || !(request instanceof LikeHttpRequest)) {
			request = new LikeHttpRequest(this);
		}
		((LikeHttpRequest) request).actionStoreLike(store.getId());
		manager.request(this, request, LIKE_REQUEST);
		store.setLikeCount(store.getLikeCount() + 1);
	}

	private void unlikeRequest() {
		if (request == null || !(request instanceof LikeHttpRequest)) {
			request = new LikeHttpRequest(this);
		}

		((LikeHttpRequest) request).actionStoreUnLike(store.getId());
		manager.request(this, request, UN_LIKE_REQUEST);
		store.setLikeCount(store.getLikeCount() - 1);
	}


	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch (tag) {
		case BOOKMARK_REQUEST:
			scrapButton.setClickable(true);
		case UN_BOOKMARK_REQUEST:
			scrapButton.setClickable(true);
		case LIKE_REQUEST:
			likeButton.setClickable(true);
		case UN_LIKE_REQUEST:
			likeButton.setClickable(true);
		}

		setInfo();
	}


	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.showToastMsg(getApplicationContext());
	}

	public void onLikeButtonClicked(View view) {
		if (session.isLogin()){
			likeButton.setClickable(false);
			if (dbProvider.isExistLike(store.getId(), "Store")){
				dbProvider.deleteLike(store.getId(), "Store");
				// api request
				unlikeRequest();
			}else {
				Like like = new Like();
				like.setForeignKey(store.getId());
				like.setObject("Store");
				dbProvider.insertLike(like);
				// api request
				likeRequest();
			}
		}
	}

	public void onScrapButtonClicked(View view) {
		if (session.isLogin()){
			scrapButton.setClickable(false);
			if (dbProvider.isExistBookmark(store.getId(), "Store")){

				dbProvider.deleteBookmark(store.getId(), "Store");
				// api request
				unbookmarkReuqest();
			}else {
				Bookmark bookmark = new Bookmark();
				bookmark.setForeignKey(store.getId());
				bookmark.setObject("Store");
				dbProvider.insertBookmark(bookmark);
				// api request
				bookmarkRequest();
			}
		}
	}

	public void onMapButtonClicked(View view) {

	}

	public void onPhoneButtonClicked(View view) {

	}

	public void onWebButtonClicked(View view) {

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
			result += tags.get(0); 
			for (int i = 0; i < tags.size() - 1; i++) {
				result += ", " + tags.get(i).getTag();
			}
		}
		
		return result;
	}

	@Override
	protected String titleBarText() {
		return "StoreMainActivity";
	}

	@Override
	protected boolean setTitleBarButton(Button button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		// TODO Auto-generated method stub
		
	}
}