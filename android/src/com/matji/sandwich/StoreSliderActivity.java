package com.matji.sandwich;

import android.os.Bundle;
import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.matji.sandwich.widget.StoreListView;
import com.matji.sandwich.widget.PagerControl;
import com.matji.sandwich.widget.StoreNearListView;
import com.matji.sandwich.widget.PostListView;
import com.matji.sandwich.widget.SwipeView;
import com.matji.sandwich.widget.HorizontalPager.OnScrollListener;
import com.matji.sandwich.http.util.MatjiImageDownloader;

public class StoreSliderActivity extends Activity implements OnScrollListener {
    private int[] pagerControlStringRef;
    private SwipeView swipeView;
    private StoreListView view1;
    private StoreNearListView view2;
    private PostListView view3;
    private PagerControl control;
    private ImageView image;
    private Context mContext;
    private MatjiImageDownloader downloader;
    
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.store_slider);
	
	mContext = getApplicationContext();
	pagerControlStringRef = new int[] { R.string.all_store,
					    R.string.near_store,
					    R.string.all_memo,
					    R.string.all_memo };
	downloader = new MatjiImageDownloader();
	
	control = (PagerControl)findViewById(R.id.PagerControl);
	swipeView = (SwipeView)findViewById(R.id.SwipeView);
	view1 = (StoreListView)findViewById(R.id.ListView1);
	view2 = (StoreNearListView)findViewById(R.id.ListView2);
	view3 = (PostListView)findViewById(R.id.ListView3);
	image = (ImageView)findViewById(R.id.ImageViewTest);
	
	downloader.downloadAttachFileImage(115, image);
	
	view1.start(this);
	view2.start(this);
	view3.start(this);
	control.start(this);

	control.setNumPages(swipeView.getChildCount());
	control.setViewNames(pagerControlStringRef);

	swipeView.addOnScrollListener(this);
    }

    public void onScroll(int scrollX) { }

    public void onViewScrollFinished(int currentPage) {
	// Log.d("CURRENT", currentPage + "");
	control.setCurrentPage(currentPage);
    }
}