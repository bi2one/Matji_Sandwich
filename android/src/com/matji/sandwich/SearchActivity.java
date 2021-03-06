package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost.OnTabChangeListener;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.MainSearchInputBar;
import com.matji.sandwich.widget.RelativeLayoutThatDetectsSoftKeyboard;
import com.matji.sandwich.widget.RoundTabHost;
import com.matji.sandwich.widget.search.SearchInputBar.Searchable;
import com.matji.sandwich.widget.title.HomeTitle;

/**
 * activity for search (store, post, user).
 * 
 * @author mozziluv
 *
 */
public class SearchActivity extends BaseTabActivity implements RelativeLayoutThatDetectsSoftKeyboard.Listener {
	private HomeTitle title;
	private MainSearchInputBar searchInputBar;
	private RoundTabHost tabHost;
	private RelativeLayoutThatDetectsSoftKeyboard contentWrapper;

    public static final String KEYWORD = "StoreMainActivity.keyword";
	public static final String PAGE = "page";
	public static final int STORE = 0;
	public static final int POST = 1;
	public static final int USER = 2;
	
	public int setMainViewId() {
		return R.id.activity_search;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		setContentView(R.layout.activity_search);

		title = (HomeTitle) findViewById(R.id.Titlebar);
		title.setTitle(R.string.default_string_search);

		searchInputBar = (MainSearchInputBar) findViewById(R.id.main_search_input_bar);
		String keyword = getIntent().getStringExtra(KEYWORD);
		if (keyword != null) {
			searchInputBar.setText(keyword);
			searchInputBar.showKeyboard();
		}

		tabHost = (RoundTabHost) getTabHost();
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String str) {
				int index = getTabHost().getCurrentTab();
				Searchable searchable = (Searchable)getCurrentActivity();

				if (index == STORE) {
					searchInputBar.changeSearchType(MainSearchInputBar.STORE, searchable);
				} else if (index == POST) {
					searchInputBar.changeSearchType(MainSearchInputBar.POST, searchable);
				} else if (index == USER) {
					searchInputBar.changeSearchType(MainSearchInputBar.USER, searchable);
				}
			}
		});

		Intent storeSearchIntent = new Intent(this, StoreSearchActivity.class);
		Intent postSearchIntent = new Intent(this, PostSearchActivity.class);
		Intent userSearchIntent = new Intent(this, UserSearchActivity.class);

		tabHost.addLeftTab("tab1",
				R.string.default_string_store,
				storeSearchIntent);
		tabHost.addCenterTab("tab2",
				R.string.default_string_post,
				postSearchIntent);
		tabHost.addRightTab("tab3",
				R.string.default_string_user,
				userSearchIntent);

		contentWrapper = (RelativeLayoutThatDetectsSoftKeyboard) findViewById(R.id.activity_search);
		contentWrapper.setBackgroundColor(MatjiConstants.color(R.color.matji_white));
		contentWrapper.setListener(this);
		searchInputBar.setSearchView((FrameLayout) findViewById(R.id.search_wrapper));
		tabHost.setCurrentTab(getIntent().getIntExtra(PAGE, STORE));

		title.setVisibility(View.GONE);
	}

	@Override
	public void onSoftKeyboardShown(boolean isShowing) {
		searchInputBar.setKeyboardState(isShowing);
		if (isShowing) {
			title.setVisibility(View.GONE);
			tabHost.getTabWidget().setVisibility(View.VISIBLE);
		} else {
			title.setVisibility(View.VISIBLE);
			tabHost.getTabWidget().setVisibility(View.GONE);
		}
	}
}