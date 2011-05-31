package com.matji.sandwich;

import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class StoreMainActivity extends Activity{
	private TabHost tabHost;
	private Intent intent;
	private Store store;
	private MatjiImageDownloader downloader;

	private ImageView storeImage;
	private TextView likeCountText;
	private TextView addressText;
	private TextView coverText;
	private TextView regUserText;
	private Button postsViewButton;
	private Button imagesViewButton;
	private Button tagsViewButton;
	private Button urlsViewButton;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_main);		

		tabHost = ((TabActivity) getParent()).getTabHost();
		intent = getIntent();
		store = (Store) intent.getParcelableExtra("store");
		downloader = new MatjiImageDownloader();

		initStoreInfo();
		initListener();
	}

	private void initStoreInfo() {
		storeImage = (ImageView) findViewById(R.id.store_main_store_image);
		likeCountText = (TextView) findViewById(R.id.store_main_like_store_count);
		addressText = (TextView) findViewById(R.id.store_main_store_address);
		coverText = (TextView) findViewById(R.id.store_main_cover);
		regUserText = (TextView) findViewById(R.id.store_main_reg_user);
		postsViewButton = (Button)findViewById(R.id.store_main_memo_view_button);
		imagesViewButton = (Button)findViewById(R.id.store_main_image_view_button);
		tagsViewButton = (Button)findViewById(R.id.store_main_tag_view_button);
		urlsViewButton = (Button)findViewById(R.id.store_main_url_button);

		/* Set StoreImage */
		AttachFile file = store.getFile();
		Log.d("Matji", store.getName());
		if (file != null) {
			downloader.downloadAttachFileImage(file.getId(), MatjiImageDownloader.IMAGE_SMALL, storeImage);
		} else {
			Drawable defaultImage = getResources().getDrawable(R.drawable.img_profile_default);
			storeImage.setImageDrawable(defaultImage);
		}

		likeCountText.setText(String.valueOf(store.getLikeCount()));
		addressText.setText(store.getAddress());
		coverText.setText(store.getCover());

		/* Set RegUser */
		User regUser = store.getRegUser();
		if (regUser == null) {
			regUserText.setText(getString(R.string.default_string_not_reg_user));
		} else {
			regUserText.setText(getString(R.string.default_string_reg_user) + ": " + regUser.getNick());
		}

		postsViewButton.setText(getString(R.string.default_string_memo) + ": " + store.getPostCount() + "개");
		imagesViewButton.setText(getString(R.string.default_string_image) + ": " + store.getImageCount() + "개");
		tagsViewButton.setText(getString(R.string.default_string_tag) + ": " + store.getTagCount() + "개");
		urlsViewButton.setText(getString(R.string.default_string_url));
	}

	private void initListener() {
		ButtonClickListener btnListener = new ButtonClickListener();
		postsViewButton.setOnClickListener(btnListener);
		imagesViewButton.setOnClickListener(btnListener);
	}

	class ButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()) {
			case R.id.store_main_memo_view_button:
				tabHost.setCurrentTab(StoreTabActivity.MEMO_PAGE);
				break;
			case R.id.store_main_image_view_button:
				tabHost.setCurrentTab(StoreTabActivity.IMAGE_PAGE);
				break;
			}
		}
	}
}