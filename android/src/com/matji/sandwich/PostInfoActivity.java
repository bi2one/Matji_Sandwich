package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Tag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class PostInfoActivity extends Activity {	
	Post post;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_main);		

		setStoreInfo();
	}

	private void setStoreInfo() {
		Intent intent = getIntent();
		post = intent.getParcelableExtra("store");

		TextView likeCountText = (TextView) findViewById(R.id.store_main_like_store_count);
		TextView addressText = (TextView) findViewById(R.id.store_main_store_address);
		TextView coverText = (TextView) findViewById(R.id.store_main_cover);
		TextView RegUser = (TextView) findViewById(R.id.store_main_reg_user);
		Button postCountButton = (Button)findViewById(R.id.store_main_memo_button);
		Button imageCountButton = (Button)findViewById(R.id.store_main_image_button);
		Button tagCountButton = (Button)findViewById(R.id.store_main_tag_button);
		Button urlButton = (Button)findViewById(R.id.store_main_url_button);

//		if (store.getTagCount() > 0) {
//			tagsText.setText("태그: " + tags.get(0).getTag());
//			for (int i = 1; i < ((tags.size() > 5) ? 5 : tags.size()); i++) {
//				tagsText.setText(tagsText.getText() + ", " + tags.get(i).getTag());
//			}
//		}
	}
}