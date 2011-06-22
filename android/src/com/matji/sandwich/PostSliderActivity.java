package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.SwipeView;
import com.matji.sandwich.widget.PostListView;
import com.matji.sandwich.widget.PostNearListView;
import com.matji.sandwich.widget.PostSearchView;
import com.matji.sandwich.widget.MyPostListView;
import com.matji.sandwich.widget.PagerControl;
import com.matji.sandwich.widget.RequestableMListView;
import com.matji.sandwich.widget.HorizontalPager.OnScrollListener;

public class PostSliderActivity extends BaseActivity implements OnScrollListener {
        public static final int INDEX_NEAR_POST = 2;
	public static final int LOGIN_ACTIVITY = 1;
	public static final int WRITE_POST_ACTIVITY = 2;
    
        private static final int mDefaultPage = 1;
    
	private SwipeView swipeView;
	private PostSearchView view1;
	private PostListView view2;
	private PostNearListView view3;
	private MyPostListView view4;
	private Context mContext;
	private ArrayList<View> mContentViews;
	private int mCurrentPage;
	private PagerControl control;
	private Session session;
	private boolean privateMode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_slider);
		
		session = Session.getInstance(this);
		mContext = getApplicationContext();
		mContentViews = new ArrayList<View>();
		privateMode = false;
			
		control = (PagerControl) findViewById(R.id.PagerControl);
		control.setContentViews(mContentViews);
		
		swipeView = (SwipeView) findViewById(R.id.SwipeView);
		view1 = (PostSearchView) findViewById(R.id.ListView1);
		view2 = (PostListView) findViewById(R.id.ListView2);
		view3 = (PostNearListView) findViewById(R.id.ListView3);
		
		view1.setTag(R.string.title, getResources().getText(R.string.search_post).toString());
		view2.setTag(R.string.title, getResources().getText(R.string.all_post).toString());
		view3.setTag(R.string.title, getResources().getText(R.string.near_post).toString());

		mContentViews.add(view1);
		mContentViews.add(view2);
		mContentViews.add(view3);
		
		view1.setActivity(this);
		view2.setActivity(this);
		view3.setActivity(this);
		
		swipeView.addOnScrollListener(this);
	}
	
	public void onResume() {
		super.onResume();
		mCurrentPage = session.getPreferenceProvider().getInt(Session.POST_SLIDER_INDEX, mDefaultPage);
		session.getPreferenceProvider().setInt(Session.POST_SLIDER_INDEX, mDefaultPage);
		
		if(session.getToken() == null && privateMode == true){
			// remove private lists
			removePrivateStoreList();
			privateMode = false;
		}else if (session.getToken() != null && privateMode == false) {
			// add private lists
			addPrivateStoreList();
			privateMode = true;
		} else {
			initPages();
		}
	}

	private void initPages(){		
		control.start(this);
		control.setNumPages(mContentViews.size());
		control.setCurrentPage(mCurrentPage);
		swipeView.setCurrentPage(mCurrentPage);
		((RequestableMListView)mContentViews.get(mCurrentPage)).requestConditionally();
	}
	
	private void addPrivateStoreList() {
		if (view4 == null) {
			view4 = new MyPostListView(this, null);
			Session session = Session.getInstance(mContext);
			view4.setUserId(session.getCurrentUser().getId());
			view4.setTag(R.string.title, getResources().getString(R.string.my_post).toString());
			view4.setActivity(this);
		}
		swipeView.addView(view4);
		mContentViews.add(view4);
		
		initPages();
	}

	private void removePrivateStoreList() {
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

//	public boolean onCreateOptionsMenu(Menu menu) {
//		super.onCreateOptionsMenu(menu);
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.menu, menu);
//		return true;
//	}

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


//	public boolean onOptionsItemSelected(MenuItem item) {
//		Session session = Session.getInstance(this);
//		switch (item.getItemId()) {
//		case R.id.posting:
//			if (session.getToken() == null) {
//				startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), LOGIN_ACTIVITY);
//			} else {
//				startActivityForResult(new Intent(getApplicationContext(), WritePostActivity.class), WRITE_POST_ACTIVITY);
//			}
//			return true;
//		}
//		return false;
//	}

	@Override
	protected String titleBarText() {
		return "PostSliderActivity";
	}

	@Override
	protected boolean setTitleBarButton(Button button) {
		// TODO Auto-generated method stub
		button.setText("Write");
		return true;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		// TODO Auto-generated method stub
		if (session.getToken() == null) {
			startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), LOGIN_ACTIVITY);
		} else {
			startActivityForResult(new Intent(getApplicationContext(), WritePostActivity.class), WRITE_POST_ACTIVITY);
		}
		
	}
}
