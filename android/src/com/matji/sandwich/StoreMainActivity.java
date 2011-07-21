package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.base.BaseTabActivity;
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
import com.matji.sandwich.widget.StorePostListView;
import com.matji.sandwich.widget.RoundTabHost;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StoreMainActivity extends BaseTabActivity implements Requestable {
	private RoundTabHost tabHost;
    private Store store;

    private HttpRequestManager manager;
    private HttpRequest request;
    private Session session;
    private DBProvider dbProvider;

    private ImageView storeImage;
    private Button likeButton;
    private TextView nameText;
    private TextView telText;
    private TextView addressText;
    private TextView tagText;
    private TextView foodText;
    private TextView coverText;
    private TextView regUserText;
    private TextView ownerUserText;

    private MatjiImageDownloader downloader;
    private StorePostListView listView;
    
    /* request tags */
    public final static int BOOKMARK_REQUEST = 10;
    public final static int UN_BOOKMARK_REQUEST = 11;
    public final static int LIKE_REQUEST = 12;
    public final static int UN_LIKE_REQUEST = 13;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_store_main);

    	tabHost = (RoundTabHost)getTabHost();
    	store = (Store) SharedMatjiData.getInstance().top();

    	manager = HttpRequestManager.getInstance(this);
    	session = Session.getInstance(this);
    	dbProvider = DBProvider.getInstance(this);

    	storeImage = (ImageView) findViewById(R.id.store_main_thumnail);
    	likeButton = (Button) findViewById(R.id.store_main_like_btn);
    	nameText = (TextView) findViewById(R.id.store_main_name);
    	telText = (TextView) findViewById(R.id.store_main_tel);
    	addressText = (TextView) findViewById(R.id.store_main_address);
    	tagText = (TextView) findViewById(R.id.store_main_tag);
    	foodText = (TextView) findViewById(R.id.store_main_food);

    	tabHost.addLeftTab("tab1",
    			R.string.store_main_post_list_view,
    			new Intent(this, StoreSliderActivity.class));
    	tabHost.addCenterTab("tab2",
    			R.string.store_main_img,
    			new Intent(this, StorePostListActivity.class));
    	tabHost.addRightTab("tab3",
    			R.string.store_main_review,
    			new Intent(this, StoreSliderActivity.class));

//	listView = (StorePostListView) findViewById(R.id.store_post_list_view);
//	listView.setActivity(this);
//	listView.requestReload();

	
 
//	/* Set RegUser */
//	User regUser = store.getRegUser();
//	String string;
//	if (regUser == null) {
//	    string = getString(R.string.default_string_not_exist_reg_user);
//	    regUserText.setText(string);
//	} else {
//	    string = getString(R.string.default_string_reg_user) + ": " + regUser.getNick();
//	    regUserText.setText(string);
//	}		
//
//	/* Set Owner User */
//	//User ownerUser = store.getOwnerUser();
//	User ownerUser = null;
//	if (ownerUser != null) {
//	    string = getString(R.string.default_string_owner_user) + ": " + regUser.getNick();
//	    ownerUserText.setText(string);
//	} else {
//	    ownerUserText.setVisibility(TextView.GONE);
//	}

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

    	nameText.setText(store.getName());
    	    	telText.setText(store.getTel());
    	addressText.setText(store.getAddress());
    	tagText.setText(tagListToCSV(store.getTags()));
    	foodText.setText(foodListToCSV(store.getStoreFoods()));
	
    	if (session.isLogin()) {
    		if (dbProvider.isExistLike(store.getId(), "Store")) {
    			likeButton.setText(store.getLikeCount());
    		} else {
    			likeButton.setText(store.getLikeCount());
    		}
    	} else {
    		likeButton.setText(store.getLikeCount());
    	}
  	}

    public void onResume() {
    	super.onResume();
//	listView.dataRefresh();
    	setInfo();
    }

    // private void bookmarkRequest() {
    // 	if (request == null || !(request instanceof BookmarkHttpRequest)) {
    // 	    request = new BookmarkHttpRequest(this);
    // 	}
    // 	((BookmarkHttpRequest) request).actionBookmark(store.getId());
    // 	manager.request(this, request, BOOKMARK_REQUEST, this);
    // 	store.setBookmarkCount(store.getBookmarkCount() + 1);
    // }

    // private void unbookmarkReuqest() {
    // 	if (request == null || !(request instanceof BookmarkHttpRequest)) {
    // 	    request = new BookmarkHttpRequest(this);
    // 	}
    // 	((BookmarkHttpRequest) request).actionUnBookmark(store.getId());
    // 	manager.request(this, request, UN_BOOKMARK_REQUEST, this);
    // 	store.setBookmarkCount(store.getBookmarkCount() - 1);
    // }

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

    // private void postBookmarkRequest() {
    // 	User me = session.getCurrentUser();
		
    // 	Bookmark bookmark = new Bookmark();
    // 	bookmark.setForeignKey(store.getId());
    // 	bookmark.setObject("Store");
    // 	dbProvider.insertBookmark(bookmark);		
    // 	me.setStoreCount(me.getStoreCount() + 1);
    // 	scrapButton.setClickable(true);
    // }
	
    // private void postUnBookmarkRequest() {
    // 	User me = session.getCurrentUser();
		
    // 	dbProvider.deleteBookmark(store.getId(), "Store");
    // 	me.setStoreCount(me.getStoreCount() - 1);
    // 	scrapButton.setClickable(true);
    // }
	
    // private void postLikeRequest() {
    // 	Like like = new Like();
    // 	like.setForeignKey(store.getId());
    // 	like.setObject("Store");
    // 	dbProvider.insertLike(like);
    // 	store.setLikeCount(store.getLikeCount() + 1);
    // 	likeButton.setClickable(true);
    // }

    // private void postUnLikeRequest() {
    // 	dbProvider.deleteLike(store.getId(), "Store");
    // 	store.setLikeCount(store.getLikeCount() - 1);
    // 	likeButton.setClickable(true);
    // }
	
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	switch (tag) {
	case BOOKMARK_REQUEST:
	    //	    postBookmarkRequest();
	    break;
	case UN_BOOKMARK_REQUEST:
	    //	    postUnBookmarkRequest();
	    break;
	case LIKE_REQUEST:
	    //	    postLikeRequest();
	    break;
	case UN_LIKE_REQUEST:
	    //	    postUnLikeRequest();
	    break;
	}

	setInfo();
    }


    public void requestExceptionCallBack(int tag, MatjiException e) {
	// likeButton.setClickable(true);
	// scrapButton.setClickable(true);
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

    // public void onScrapButtonClicked(View view) {
    // 	if (loginRequired()) {
    // 	    if (!manager.isRunning(this)) {
    // 		scrapButton.setClickable(false);
    // 		if (dbProvider.isExistBookmark(store.getId(), "Store")){
    // 		    // api request
    // 		    unbookmarkReuqest();
    // 		}else {
    // 		    // api request
    // 		    bookmarkRequest();
    // 		}
    // 	    }
    // 	}
    // }

    // public void onMapButtonClicked(View view) {
    // }

    // public void onPhoneButtonClicked(View view) {
    // 	if (store.getTel() != null) {
    // 	    try {
    // 		startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + store.getTel())));
    // 	    } catch (Exception e) {
    // 		e.printStackTrace();
    // 	    }
    // 	}
    // }

    // public void onWebButtonClicked(View view) {
    // 	String web = store.getWebsite();
    // 	if (web != null && !web.equals("")) {
    // 	    if (!web.subSequence(0, 3).equals("http")) {
    // 		web = "http://" + web;
    // 	    }

    // 	    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
    // 	    startActivity(intent);				
    // 	}
    // }

    // public void onInfoViewButtonClicked(View view) {
    // 	Intent intent = new Intent(this, StoreDetailInfoTabActivity.class);
    // 	startActivity(intent);
    // }

    // public void onMenuViewButtonClicked(View view) {
    // 	tabHost.setCurrentTab(StoreTabActivity.MENU_PAGE);
    // }

    // public void onMemoButtonClicked(View view) {
    // 	tabHost.setCurrentTab(StoreTabActivity.MEMO_PAGE);
    // }

    // public void onImageButtonClicked(View view) {
    // 	tabHost.setCurrentTab(StoreTabActivity.IMAGE_PAGE);
    // }

    // public void onTagButtonClicked(View view) {
    // 	Intent intent = new Intent(this, TagListActivity.class);
    // 	intent.putExtra("id", store.getId());
    // 	intent.putExtra("type", ModelType.STORE);
    // 	startActivity(intent);
    // }

    // public void onUrlButtonClicked(View view) {
    // 	Intent intent = new Intent(this, StoreUrlListActivity.class);
    // 	intent.putExtra("store_id", store.getId());
    // 	startActivity(intent);
    // }

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