package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.RequestableMListView;
import com.matji.sandwich.widget.StoreListView;
import com.matji.sandwich.widget.StoreNearListView;
import com.matji.sandwich.widget.StoreSearchView;
import com.matji.sandwich.widget.StoreBookmarkedListView;
import com.matji.sandwich.widget.PagerControl;
import com.matji.sandwich.widget.SwipeView;
import com.matji.sandwich.widget.HorizontalPager.OnScrollListener;
import com.matji.sandwich.session.Session;

public class StoreSliderActivity extends BaseActivity implements OnScrollListener {

	private SwipeView swipeView;
	private StoreSearchView view1;
	private StoreListView view2;
	private StoreNearListView view3;
	private StoreBookmarkedListView view4;
	private PagerControl control;
	private Context mContext;
	private int mCurrentPage;
	private ArrayList<View> mContentViews;

	private Session session;
	private boolean privateMode;
	private final static int mDefaultPage = 1;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_slider);
		session = Session.getInstance(this);
		mContext = getApplicationContext();
		mContentViews = new ArrayList<View>();
		privateMode = false;
		
		//pagerControlStringRef = new int[] {R.string.search_store, R.string.all_store, R.string.near_store}; 

		control = (PagerControl) findViewById(R.id.PagerControl);
		control.setContentViews(mContentViews);
		
		swipeView = (SwipeView) findViewById(R.id.SwipeView);
		view1 = (StoreSearchView) findViewById(R.id.ListView1);
		view2 = (StoreListView) findViewById(R.id.ListView2);
		view3 = (StoreNearListView) findViewById(R.id.ListView3);
		
		
		view1.setTag(R.string.title, getResources().getText(R.string.search_store).toString());
		view2.setTag(R.string.title, getResources().getText(R.string.all_store).toString());
		view3.setTag(R.string.title, getResources().getText(R.string.near_store).toString());
		
		mContentViews.add(view1);
		mContentViews.add(view2);
		mContentViews.add(view3);
		
		view1.setActivity(this);
		view2.setActivity(this);
		view3.setActivity(this);
		
		initPages();
		
		swipeView.addOnScrollListener(this);		
		
	}

	public void onResume(){
		super.onResume();
		if(session.getToken() == null && privateMode == true){
			// remove private lists
			removePrivateStoreList();
			privateMode = false;
		}else if (session.getToken() != null && privateMode == false) {
			// add private lists
			addPrivateStoreList();
			privateMode = true;
		}
	}
	
	
	private void initPages(){
		mCurrentPage = mDefaultPage;
		
		control.start(this);
		control.setNumPages(mContentViews.size());
		control.setCurrentPage(mCurrentPage);
		swipeView.setCurrentPage(mCurrentPage);
		((RequestableMListView)mContentViews.get(mCurrentPage)).requestConditionally();
	}

	private void addPrivateStoreList () {
		if (view4 == null) {
			view4 = new StoreBookmarkedListView(this, null);
			Session session = Session.getInstance(mContext);
			view4.setUserId(session.getCurrentUser().getId());
			view4.setTag(R.string.title, getResources().getString(R.string.bookmarked_store).toString());
			view4.setActivity(this);
		}
		swipeView.addView(view4);
		mContentViews.add(view4);

		initPages();
	}
	
	
	private void removePrivateStoreList () {
		swipeView.removeViewInLayout(view4);
		mContentViews.remove(view4);
		initPages();
	}

	public void onScroll(int scrollX) {
	}

	public void onViewScrollFinished(int currentPage) {
		if (mCurrentPage != currentPage) {
			Log.d("refresh", "pageChanged!");

			try {
				mCurrentPage = currentPage;
				control.setCurrentPage(currentPage);
				View view = mContentViews.get(currentPage);

				if (view instanceof RequestableMListView) {
					RequestableMListView listView = (RequestableMListView) view;
					listView.requestConditionally();
				}
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}

	}
}