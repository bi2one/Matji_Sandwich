package com.matji.sandwich;

import java.util.ArrayList;

import android.view.View;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.util.Log;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.widget.RequestableMListView;
import com.matji.sandwich.widget.SearchBar;
import com.matji.sandwich.widget.PagerControl;
import com.matji.sandwich.widget.SwipeView;
import com.matji.sandwich.widget.HorizontalPager.OnScrollListener;
import com.matji.sandwich.session.Session;

public abstract class SliderActivity extends BaseActivity implements OnScrollListener {
	protected abstract int getLayoutId();
	protected abstract int getDefaultPageCount();
	protected abstract String getSliderIndex();
	protected abstract int[] getViewIds();
	protected abstract int[] getTitleIds();
	protected abstract View getPrivateView();

	private static final int mDefaultPage = 1;

	private int[] viewIds;
	private int[] titleIds;

	private ArrayList<View> mContentViews;
	private SwipeView swipeView;
	private PagerControl control;
	private int mCurrentPage;

	private Session session;
	private boolean privateMode;
	
	private View privateView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());

		viewIds = getViewIds();
		titleIds = getTitleIds();
		
		session = Session.getInstance(this);
		mContentViews = new ArrayList<View>();
		privateMode = false;

		control = (PagerControl) findViewById(R.id.PagerControl);
		control.setContentViews(mContentViews);

		swipeView = (SwipeView) findViewById(R.id.SwipeView);

		for (int i = 0; i < viewIds.length; i++) {
			RequestableMListView v = (RequestableMListView) findViewById(viewIds[i]);
			v.setActivity(this);
			v.setCanRepeat(true);
			v.setTag(R.string.title, getResources().getText(titleIds[i]));
			mContentViews.add(v);
		}

		LinearLayout searchWrap = (LinearLayout) findViewById(R.id.SearchWrap);
		searchWrap.addView(new SearchBar(this, (Searchable) mContentViews.get(0)), 0);
		
		swipeView.addOnScrollListener(this);
	}

	public void onResume(){
		super.onResume();
		mCurrentPage = session.getPreferenceProvider().getInt(getSliderIndex(), mDefaultPage);
		//		session.getPreferenceProvider().setInt(Session.STORE_SLIDER_INDEX, mDefaultPage);

		if (session.getToken() == null && privateMode == true){
			// remove private lists
			removePrivateStoreList();
			privateMode = false;
		} else if (session.getToken() != null && privateMode == false) {
			// add private lists
			addPrivateStoreList();
			privateMode = true;
		} else {
			initPages();
		}
		
		RequestableMListView currentView = ((RequestableMListView) mContentViews.get(mCurrentPage));
		currentView.dataRefresh();
	}

	private void initPages(){
		control.start(this);
		control.setNumPages(mContentViews.size());
		control.setCurrentPage(mCurrentPage);
		swipeView.setCurrentPage(mCurrentPage);
		RequestableMListView currentView = (RequestableMListView) mContentViews.get(mCurrentPage);
		if (!(currentView instanceof Searchable)) {
			currentView.requestConditionally();
		}
	}

	private void addPrivateStoreList () {
		if (mContentViews.size() == getDefaultPageCount()) {
			privateView = getPrivateView();
		}
		swipeView.addView(privateView);
		mContentViews.add(privateView);
		initPages();
	}

	private void removePrivateStoreList () {
		swipeView.removeViewInLayout(privateView);
		mContentViews.remove(privateMode);
		initPages();
	}

	public void onScroll(int scrollX) {
	}

	public void onViewScrollFinished(int currentPage) {
		if (mCurrentPage != currentPage) {
			Log.d("refresh", "pageChanged!");
			try {
				KeyboardUtil.hideKeyboard(this);
				mCurrentPage = currentPage;
				session.getPreferenceProvider().setInt(getSliderIndex(), mCurrentPage);
				control.setCurrentPage(currentPage);
				View view = mContentViews.get(currentPage);

				if (view instanceof RequestableMListView && !(view instanceof Searchable)) {
					RequestableMListView listView = (RequestableMListView) view;
					listView.requestConditionally();
				}
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected RequestableMListView getCurrentPage() {
		return (RequestableMListView) mContentViews.get(mCurrentPage);
	}
}