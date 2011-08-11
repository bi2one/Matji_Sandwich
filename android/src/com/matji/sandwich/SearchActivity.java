package com.matji.sandwich;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.widget.MainSearchInputBar;
import com.matji.sandwich.widget.RelativeLayoutThatDetectsSoftKeyboard;
import com.matji.sandwich.widget.RoundTabHost;
import com.matji.sandwich.widget.SearchInputBar.Searchable;
import com.matji.sandwich.widget.title.HomeTitle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost.OnTabChangeListener;

public class SearchActivity extends BaseTabActivity implements RelativeLayoutThatDetectsSoftKeyboard.Listener {
	private HomeTitle title;
	private MainSearchInputBar searchInputBar;
	private RoundTabHost tabHost;
	private RelativeLayoutThatDetectsSoftKeyboard contentWrapper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void init() {
		super.init();

		setContentView(R.layout.activity_search);
		
		title = (HomeTitle) findViewById(R.id.Titlebar);
		title.setTitle(R.string.default_string_search);

		searchInputBar = (MainSearchInputBar) findViewById(R.id.main_search_input_bar);

		tabHost = (RoundTabHost) getTabHost();
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String str) {
				int index = getTabHost().getCurrentTab();
				searchInputBar.setText("");
				
				Searchable searchable = (Searchable) getCurrentActivity();

				if (index == 0) {
					searchInputBar.changeSearchType(MainSearchInputBar.STORE, searchable);
				} else if (index == 1) {
					searchInputBar.changeSearchType(MainSearchInputBar.POST, searchable);
				}
			}
		});
		
		Intent storeSearchIntent = new Intent(this, StoreSearchActivity.class);
		Intent postSearchIntent = new Intent(this, PostSearchActivity.class);

		tabHost.addLeftTab("tab1",
				R.string.default_string_store,
				storeSearchIntent);
		tabHost.addCenterTab("tab2",
				R.string.default_string_post,
				postSearchIntent);
		//		tabHost.addRightTab("tab3",
		//				R.string.store_main_review,
		//				storeUrlListIntent);
		
		searchInputBar.changeSearchType(MainSearchInputBar.STORE, (Searchable) getCurrentActivity());
		contentWrapper = (RelativeLayoutThatDetectsSoftKeyboard) findViewById(R.id.contentWrapper);
		contentWrapper.setListener(this);
		searchInputBar.setSearchView((FrameLayout) findViewById(R.id.search_wrapper));
		title.setVisibility(View.GONE);
	}

	@Override
	public void onSoftKeyboardShown(boolean isShowing) {
		searchInputBar.setKeyboardState(isShowing);
		if (isShowing) {
			title.setVisibility(View.GONE);
			tabHost.getTabWidget().setVisibility(View.VISIBLE);
			//TODO transparent background.
		} else {
			title.setVisibility(View.VISIBLE);
			tabHost.getTabWidget().setVisibility(View.GONE);
		}
	}
}