package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;
import android.content.*;
import android.util.Log;
import android.widget.ImageView;

import com.matji.sandwich.session.*;
import com.matji.sandwich.widget.RequestableMListView;
import com.matji.sandwich.widget.StoreListView;
import com.matji.sandwich.widget.PagerControl;
import com.matji.sandwich.widget.StoreNearListView;
import com.matji.sandwich.widget.PostListView;
import com.matji.sandwich.widget.SwipeView;
import com.matji.sandwich.widget.BaseViewContainer;
import com.matji.sandwich.widget.HorizontalPager.OnScrollListener;
import com.matji.sandwich.widget.PullToRefreshListView.*;

public class StoreSliderActivity extends Activity implements OnScrollListener {
	private int[] pagerControlStringRef;
	private SwipeView swipeView;
	private StoreListView view1;
	private StoreNearListView view2;
	private PostListView view3;
	private PagerControl control;
	private Context mContext;
	private int mCurrentPage;
	private ArrayList<View> mContentViews;
	
	public static final int LOGIN_ACTIVITY = 1;
	public static final int WRITE_POST_ACTIVITY = 2;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_slider);
		mContext = getApplicationContext();
		mContentViews = new ArrayList<View>();
		mCurrentPage = 0;

		pagerControlStringRef = new int[] { R.string.all_store, R.string.near_store, R.string.all_memo };

		control = (PagerControl) findViewById(R.id.PagerControl);
		swipeView = (SwipeView) findViewById(R.id.SwipeView);
		view1 = (StoreListView) findViewById(R.id.ListView1);
		view2 = (StoreNearListView) findViewById(R.id.ListView2);
		view3 = (PostListView) findViewById(R.id.ListView3);

		mContentViews.add(view1);
		mContentViews.add(view2);
		mContentViews.add(view3);

		view1.setActivity(this);
		view1.requestReload();
		view2.setActivity(this);
		view3.setActivity(this);

		control.start(this);
		control.setNumPages(swipeView.getChildCount());
		control.setViewNames(pagerControlStringRef);

		swipeView.addOnScrollListener(this);
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

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case LOGIN_ACTIVITY:
			if (resultCode == RESULT_OK) {
				startActivityForResult(new Intent(getApplicationContext(), WritePostActivity.class), WRITE_POST_ACTIVITY);
			}
			break;
		case WRITE_POST_ACTIVITY:
			if (resultCode == RESULT_OK) {
				view3.onRefresh();
			}
			break;
		}
	}


	public boolean onOptionsItemSelected(MenuItem item) {
		Session session = Session.getInstance(this);
		switch (item.getItemId()) {
		case R.id.posting:
			if (session.getToken() == null) {
				startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), LOGIN_ACTIVITY);
			} else {
				startActivityForResult(new Intent(getApplicationContext(), WritePostActivity.class), WRITE_POST_ACTIVITY);
			}
			return true;
		}
		return false;
	}
}