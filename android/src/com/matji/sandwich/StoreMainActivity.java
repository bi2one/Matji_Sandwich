package com.matji.sandwich;

import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StoreMainActivity extends Activity{
	private Intent intent;
	private Store store;

	private ImageView storeImage;
	private TextView likeCountText;
	private TextView addressText;
	private TextView coverText;
	private TextView regUserText;

	private Button postCountButton;
	private Button imageCountButton;
	private Button tagCountButton;
	private Button urlButton;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_main);		
		intent = getIntent();
		store = (Store) intent.getParcelableExtra("store");

		setStoreInfo();
	}

	private void setStoreInfo() {
		storeImage = (ImageView) findViewById(R.id.store_main_store_image);
		likeCountText = (TextView) findViewById(R.id.store_main_like_store_count);
		addressText = (TextView) findViewById(R.id.store_main_store_address);
		coverText = (TextView) findViewById(R.id.store_main_cover);
		regUserText = (TextView) findViewById(R.id.store_main_reg_user);
		postCountButton = (Button)findViewById(R.id.store_main_memo_view_button);
		imageCountButton = (Button)findViewById(R.id.store_main_image_view_button);
		tagCountButton = (Button)findViewById(R.id.store_main_tag_view_button);
		urlButton = (Button)findViewById(R.id.store_main_url_button);

		likeCountText.setText(String.valueOf(store.getLikeCount()));
		addressText.setText(store.getAddress());
		coverText.setText(store.getCover());
		User regUser = store.getRegUser();
		if (regUser == null)
			regUserText.setText(getString(R.string.default_string_not_reg_user)); 
		else
			regUserText.setText(getString(R.string.default_string_reg_user) + ": " + regUser.getNick());
		
		postCountButton.setText(getString(R.string.default_string_memo) + ": " + store.getPostCount() + "개");
		imageCountButton.setText(getString(R.string.default_string_image) + ": " + store.getImageCount() + "개");
		tagCountButton.setText(getString(R.string.default_string_tag) + ": " + store.getTagCount() + "개");
		urlButton.setText(getString(R.string.default_string_url));
	}
}