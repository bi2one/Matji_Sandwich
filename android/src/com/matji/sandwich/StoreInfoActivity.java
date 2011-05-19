package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.StoreFood;
import com.matji.sandwich.data.StoreTag;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.FoodHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.TagHttpRequest;
import com.matji.sandwich.http.request.UserHttpRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class StoreInfoActivity extends Activity implements Requestable {
	private static final int STORE_FOOD_REQUEST_TAG = 1;
	private static final int STORE_TAG_REQUEST_TAG = 2;
	private static final int REG_USER_REQUEST= 3;
	private HttpRequestManager manager;
	private HttpRequest request;
	private Intent intent;
	private Store store;
	private ArrayList<Tag> tags;
	private ArrayList<StoreFood> storeFoods;

	private TextView likeCountText;
	private TextView addressText;
	private TextView coverText;
	private TextView popularMenuText;
	private TextView tagsText;
	private TextView regUserText;
	private Button postCountButton;
	private Button imageCountButton;
	private Button tagCountButton;
	private Button urlButton;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_info);		
		manager = new HttpRequestManager(getApplicationContext(), this);
		intent = getIntent();
		store = (Store) intent.getParcelableExtra("store");
		tags = new ArrayList<Tag>();
		storeFoods= new ArrayList<StoreFood>();

		tagsRequest();
		storeFoodsRequest();
		regUserRequest();

		setStoreInfo();
	}

	private void setStoreInfo() {
		likeCountText = (TextView) findViewById(R.id.store_info_like_store_count);
		addressText = (TextView) findViewById(R.id.store_info_store_address);
		coverText = (TextView) findViewById(R.id.store_info_cover);
		popularMenuText = (TextView) findViewById(R.id.store_info_popular_menu);
		tagsText = (TextView) findViewById(R.id.store_info_tag);
		regUserText = (TextView) findViewById(R.id.store_info_reg_user);
		postCountButton = (Button)findViewById(R.id.store_info_memo_button);
		imageCountButton = (Button)findViewById(R.id.store_info_image_button);
		tagCountButton = (Button)findViewById(R.id.store_info_tag_button);
		urlButton = (Button)findViewById(R.id.store_info_url_button);

		likeCountText.setText(String.valueOf(store.getLikeCount()));
		addressText.setText(store.getAddress());
		coverText.setText(store.getCover());		

		postCountButton.setText("메모: " + store.getPostCount() + "개");
		imageCountButton.setText("이미지: " + store.getImageCount() + "개");
		tagCountButton.setText("태그: " + store.getTagCount() + "개");
		urlButton.setText("URL: " + store.getWebsite() + "(테스트)");
	}

	private void storeFoodsRequest() {
		request = new FoodHttpRequest();
		((FoodHttpRequest) request).actionList(store.getId());
		manager.request(this, request, 1);
	}

	private void tagsRequest() {
		request = new TagHttpRequest();
		((TagHttpRequest) request).actionStoreTagList(store.getId());
		manager.request(this, request, 2);
	}


	private void regUserRequest() {
		request = new UserHttpRequest();
		((UserHttpRequest) request).actionShow(store.getRegUserId());
		manager.request(this, request, 3);
	}

	private String toStringFromFoods(ArrayList<StoreFood> storeFoods) {
		String result = "인기메뉴: ";
		for (int i = 0; i < storeFoods.size()-1; i++) {
			// TODO 임시
			result += storeFoods.get(i).getFood().getName() + ", ";
		}
		result += storeFoods.get(storeFoods.size()-1).getFood().getName() + ", ";

		return result;
	}

	private String toStringFromTags(ArrayList<Tag> tags) {
		String result = "태그: ";
		for (int i = 0; i < tags.size()-1; i++) {
			result += tags.get(i).getTag() + ", ";
		}
		result += tags.get(tags.size()-1).getTag();
		
		return result;
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		if (!data.isEmpty()) {
			switch (tag) {			
			case STORE_FOOD_REQUEST_TAG:
				for (int i = 0; i < data.size(); i++) {
					storeFoods.add((StoreFood) data.get(i));
				}
				popularMenuText.setText(toStringFromFoods(storeFoods));
				break;
			case STORE_TAG_REQUEST_TAG:
				for (int i = 0; i < data.size(); i++) {
					tags.add((Tag) data.get(i));
				}
				tagsText.setText(toStringFromTags(tags));
				break;
			case REG_USER_REQUEST:
				store.setRegUser((User)data.get(0));
				regUserText.setText("발굴자: " + ((User)data.get(0)).getNick());
				break;

			}
		}
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.printStackTrace();
	}
}