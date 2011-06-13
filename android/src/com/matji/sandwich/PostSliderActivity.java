package com.matji.sandwich;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.view.Menu;
import android.view.MenuInflater;
import android.content.Intent;

import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.PostViewContainer;
import com.matji.sandwich.widget.SwipeView;
import com.matji.sandwich.widget.PostListView;
import com.matji.sandwich.widget.PostNearListView;
import com.matji.sandwich.widget.PagerControl;
import com.matji.sandwich.widget.RequestableMListView;
import com.matji.sandwich.widget.HorizontalPager.OnScrollListener;
import com.matji.sandwich.widget.ViewContainer;

import java.util.ArrayList;

public class PostSliderActivity extends Activity implements OnScrollListener {
	private int[] pagerControlStringRef;
	private SwipeView swipeView;
	private PostListView view1;
	private PostNearListView view2;
	private Context mContext;
	private ArrayList<View> mContentViews;
	private int mCurrentPage;
	private PagerControl control;
	public static final int LOGIN_ACTIVITY = 1;
	public static final int WRITE_POST_ACTIVITY = 2;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_slider);
		mContext = getApplicationContext();
		mContentViews = new ArrayList<View>();
		mCurrentPage = 0;
		
		pagerControlStringRef = new int[] { R.string.all_store, R.string.near_store, R.string.all_memo };

		control = (PagerControl) findViewById(R.id.PagerControl);
		swipeView = (SwipeView) findViewById(R.id.SwipeView);
		view1 = (PostListView) findViewById(R.id.ListView1);
		view2 = (PostNearListView) findViewById(R.id.ListView2);
		
		mContentViews.add(view1);
		mContentViews.add(view2);
		
		view1.setActivity(this);
		view1.requestReload();
		view2.setActivity(this);
		
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
				view1.onRefresh();
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
