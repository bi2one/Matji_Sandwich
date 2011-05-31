package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.matji.sandwich.widget.RequestableMListView;
import com.matji.sandwich.widget.StoreListView;
import com.matji.sandwich.widget.PagerControl;
import com.matji.sandwich.widget.StoreNearListView;
import com.matji.sandwich.widget.PostListView;
import com.matji.sandwich.widget.SwipeView;
import com.matji.sandwich.widget.HorizontalPager.OnScrollListener;

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
    
    
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_store_slider);
	mContext = getApplicationContext();
	mContentViews = new ArrayList<View>();
	mCurrentPage = 0;
	
	pagerControlStringRef = new int[] { R.string.all_store,
					    R.string.near_store,
					    R.string.all_memo};
	


	control = (PagerControl)findViewById(R.id.PagerControl);
	swipeView = (SwipeView)findViewById(R.id.SwipeView);
	view1 = (StoreListView)findViewById(R.id.ListView1);
	view2 = (StoreNearListView)findViewById(R.id.ListView2);
	view3 = (PostListView)findViewById(R.id.ListView3);
	
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

    public void onScroll(int scrollX) { }

    public void onViewScrollFinished(int currentPage) {
    	if (mCurrentPage != currentPage){
    		Log.d("refresh", "pageChanged!");
    		
    		try {
	    		mCurrentPage = currentPage;
	    		control.setCurrentPage(currentPage);
	        	View view = mContentViews.get(currentPage);
	        	if (view instanceof RequestableMListView){
	        		RequestableMListView listView = (RequestableMListView)view;
	        		listView.requestConditionally();
	        	}
	        	
    		}catch (IndexOutOfBoundsException e){ e.printStackTrace(); }
    	}
    	
    }
}