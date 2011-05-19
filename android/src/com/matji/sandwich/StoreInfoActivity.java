package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.Tag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class StoreInfoActivity extends Activity {	
	Store store;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_info);		

		setStoreInfo();
	}

	private void setStoreInfo() {
		Intent intent = getIntent();
		store = intent.getParcelableExtra("store");
		ArrayList<Tag> tags = store.getTags(); 

		TextView likeCountText = (TextView) findViewById(R.id.store_info_like_store_count);
		TextView addressText = (TextView) findViewById(R.id.store_info_store_address);
		TextView coverText = (TextView) findViewById(R.id.store_info_cover);
		TextView popularMenuText = (TextView) findViewById(R.id.store_info_popular_menu);
		TextView tagsText = (TextView) findViewById(R.id.store_info_tag);
		TextView diggerText = (TextView) findViewById(R.id.store_info_digger);
		Button postCountButton = (Button)findViewById(R.id.store_info_memo_button);
		Button imageCountButton = (Button)findViewById(R.id.store_info_image_button);
		Button tagCountButton = (Button)findViewById(R.id.store_info_tag_button);
		Button urlButton = (Button)findViewById(R.id.store_info_url_button);
		
		likeCountText.setText(store.getLikeCount());
		addressText.setText(store.getAddress());
		coverText.setText(store.getCover());
		// TODO  테스트용
		popularMenuText.setText("인기메뉴: TODO");
		if (store.getTagCount() > 0) {
			tagsText.setText("태그: " + tags.get(0).getTag());
			for (int i = 0; i < ((tags.size() > 5) ? 5 : tags.size()); i++) {
				tagsText.setText(tagsText.getText() + ", " + tags.get(i).getTag());
			}
		}
		diggerText.setText("발굴자: " + store.getRegUserId() + "(일단 테스트를 위해 reg_user_id를 표시)");
		postCountButton.setText("메모: " + store.getPostCount() + "개");
		imageCountButton.setText("이미지: " + store.getImageCount() + "개");
		tagCountButton.setText("태그: " + store.getTagCount() + "개");
		urlButton.setText("URL: " + store.getWebsite() + "(테스트)");
	}
}