package com.matji.sandwich;

import android.view.View;
import android.widget.Button;

import com.matji.sandwich.widget.StoreBookmarkedListView;
import com.matji.sandwich.session.Session;

public class StoreSliderActivity extends SliderActivity {
	private int[] viewIds = {
			R.id.ListView1,
			R.id.ListView2,
			R.id.ListView3,
	};

	private int[] titleIds = {
			R.string.search_store,
			R.string.all_store,
			R.string.near_store,
	};
	
	public static final int INDEX_NEAR_STORE = 2;

	@Override
	protected String titleBarText() {
		return "StoreSliderActivity";
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

	@Override
	protected int getLayoutId() {
		return R.layout.activity_store_slider;
	}
	
	@Override
	protected int getDefaultPageCount() {
		return viewIds.length;
	}
	
	@Override
	protected int[] getViewIds() {
		return viewIds;
	}

	@Override
	protected int[] getTitleIds() {
		return titleIds;
	}

	@Override
	protected View getPrivateView() {
		StoreBookmarkedListView privateView = new StoreBookmarkedListView(this, null);
		privateView.setScrollContainer(false);
		privateView.setUserId(Session.getInstance(this).getCurrentUser().getId());
		privateView.setTag(R.string.title, getResources().getString(R.string.bookmarked_store).toString());
		privateView.setActivity(this);
		privateView.setCanRepeat(true);
		
		return privateView;
	}
}