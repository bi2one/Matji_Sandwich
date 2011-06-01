package com.matji.sandwich;

import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.Bookmark;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.session.Session;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class StoreMainActivity extends Activity {
	private TabHost tabHost;
	private Intent intent;
	private Store store;
	private MatjiImageDownloader downloader;

	private Session session;
	private DBProvider dbProvider;
	
	private ImageView storeImage;
	private TextView likeCountText;
	private TextView addressText;
	private TextView coverText;
	private TextView regUserText;
	private TextView ownerUserText;
	private Button scrapButton;
	private Button postButton;
	private Button imageButton;
	private Button tagButton;
	private Button urlButton;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_main);

		tabHost = ((TabActivity) getParent()).getTabHost();
		intent = getIntent();
		store = (Store) intent.getParcelableExtra("store");
		downloader = new MatjiImageDownloader();

		session = Session.getInstance(this);
		dbProvider = DBProvider.getInstance(this);
		
		initStoreInfo();
	}

	private void initStoreInfo() {
		storeImage = (ImageView) findViewById(R.id.store_main_image);
		likeCountText = (TextView) findViewById(R.id.store_main_like_count);
		addressText = (TextView) findViewById(R.id.store_main_address);
		coverText = (TextView) findViewById(R.id.store_main_cover);
		regUserText = (TextView) findViewById(R.id.store_main_reg_user);
		ownerUserText = (TextView) findViewById(R.id.store_main_owner_user);
		scrapButton = (Button) findViewById(R.id.store_main_scrap_btn);
		postButton = (Button) findViewById(R.id.store_main_memo_btn);
		imageButton = (Button) findViewById(R.id.store_main_image_btn);
		tagButton = (Button) findViewById(R.id.store_main_tag_btn);
		urlButton = (Button) findViewById(R.id.store_main_url_btn);

		/* Set StoreImage */
		AttachFile file = store.getFile();
		Log.d("Matji", store.getName());
		if (file != null) {
			downloader.downloadAttachFileImage(file.getId(), MatjiImageDownloader.IMAGE_SMALL, storeImage);
		} else {
			Drawable defaultImage = getResources().getDrawable(R.drawable.img_profile_default);
			storeImage.setImageDrawable(defaultImage);
		}
		
		if (session.isLogin()) {
			if (dbProvider.isExistBookmark(store.getId(), "Store")) {
				scrapButton.setText("unBookmark");
			} else {
				scrapButton.setText("Bookmark");
			}
		}
		
		likeCountText.setText(store.getLikeCount()+"");
		addressText.setText(store.getAddress());
		coverText.setText(store.getCover());
		
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
		if (ownerUser == null) {
			string = getString(R.string.default_string_not_exist_owner_user);
			ownerUserText.setText(string);
		} else {
			string = getString(R.string.default_string_owner_user) + ": " + regUser.getNick();
			ownerUserText.setText(string);
		}

		/* Set ETC Button String */
		string = 
			getString(R.string.default_string_memo) 
			+ ": " + store.getPostCount() + "개";
		postButton.setText(string);
		
		string = 
			getString(R.string.default_string_image) 
			+ ": " + store.getPostCount() + "개";
		imageButton.setText(string);
		
		string = 
			getString(R.string.default_string_tag) 
			+ ": " + store.getTagCount() + "개";
		tagButton.setText(string);
		string = getString(R.string.default_string_url);
		
		urlButton.setText(string);
	}

	public void onLikeButtonClicked(View view) {
		
	}
	
	public void onScrapButtonClicked(View view) {
		if (session.isLogin()){
			if (dbProvider.isExistBookmark(store.getId(), "Store")){
				
				dbProvider.deleteBookmark(store.getId(), "Store");
				// api request
			}else {
				Bookmark bookmark = new Bookmark();
				bookmark.setForeignKey(store.getId());
				bookmark.setObject("Store");
				dbProvider.insertBookmark(bookmark);
				// api request
			}
			initStoreInfo();
		}
	}
	
	public void onMapButtonClicked(View view) {
		
	}
	
	public void onPhoneButtonClicked(View view) {
		
	}
	
	public void onWebButtonClicked(View view) {
		
	}
	
	public void onInfoViewButtonClicked(View view) {
		
	}
	
	public void onMenuViewButtonClicked(View view) {
		
	}
	
	public void onMemoButtonClicked(View view) {
		tabHost.setCurrentTab(StoreTabActivity.MEMO_PAGE);
	}
	
	public void onImageButtonClicked(View view) {
		tabHost.setCurrentTab(StoreTabActivity.IMAGE_PAGE);
	}
	
	public void onTagButtonClicked(View view) {
		
	}

	public void onUrlButtonClicked(View view) {
		
	}
}