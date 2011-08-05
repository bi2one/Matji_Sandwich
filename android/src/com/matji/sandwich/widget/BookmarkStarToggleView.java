package com.matji.sandwich.widget;

import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.BaseAdapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.LoginActivity;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.BookmarkHttpRequest;
import com.matji.sandwich.exception.MatjiException;

import java.util.ArrayList;

public class BookmarkStarToggleView extends LinearLayout implements OnClickListener,
								    Requestable {
    private static final int REQUEST_TAG_BOOKMARK = 1;
    private static final int REQUEST_TAG_UNBOOKMARK = 2;
    private Context context;
    private boolean isUnchecked;
    private ImageView uncheckedView;
    private ImageView checkedView;
    private Store store;
    private ArrayList<MatjiData> bookmarkedList;
    private BaseAdapter adapter;
    private Session session;
    private HttpRequestManager requestManager;
    private BookmarkHttpRequest bookmarkRequest;

    public BookmarkStarToggleView(Context context, AttributeSet attrs) {
	super(context, attrs);
	this.context = context;
	LayoutInflater.from(context).inflate(R.layout.bookmark_star_toggle, this, true);
	setOnClickListener(this);
	isUnchecked = true;
	uncheckedView = (ImageView)findViewById(R.id.bookmark_star_toggle_unchecked);
	checkedView = (ImageView)findViewById(R.id.bookmark_star_toggle_checked);
	session = Session.getInstance(context);
	requestManager = HttpRequestManager.getInstance(context);
	bookmarkRequest = new BookmarkHttpRequest(context);
    }

    public void init(BaseAdapter adapter, ArrayList<MatjiData> bookmarkedList, Store store) {
    	this.store = store;
	this.bookmarkedList = bookmarkedList;
	this.adapter = adapter;
    	if (isBookmarked(bookmarkedList, store)) {
    	    showCheckedView();
	    isUnchecked = false;
    	} else {
    	    showUncheckedView();
	    isUnchecked = true;
    	}
    }

    private boolean isBookmarked(ArrayList<MatjiData> bookmarkedList, Store store) {
	for (MatjiData bookmarkedStore : bookmarkedList) {
	    if (store.equals((Store) bookmarkedStore)) {
		return true;
	    }
	}
	return false;
    }

    private void removeBookmarkedList(Store store) {
	// Log.d("=====", "remove!!");
	// Log.d("=====", "bookmark store: " + bookmarkedList.size());
	for (MatjiData bookmarkedStore : bookmarkedList) {
	    if (store.equals((Store) bookmarkedStore)) {
		bookmarkedList.remove(bookmarkedStore);
		return ;
	    }
	}
	return ;
    }

    public void onClick(View v) {
	// Log.d("=====", "isUnchecked: " + isUnchecked);
	if (requestManager.isRunning()) {
	    return ;
	}
	
    	if (isUnchecked) {
	    if (session.isLogin()) {
		bookmarkedList.add(store);
		adapter.notifyDataSetChanged();
		requestBookmark(store);
		isUnchecked = false;
		// Log.d("=====", "isUnchecked2: " + isUnchecked);
	    } else {
		confirmLogin();
	    }
    	} else {
	    removeBookmarkedList(store);
	    adapter.notifyDataSetChanged();
	    requestUnBookmark(store);
	    isUnchecked = true;
    	}
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) { }

    public void showUncheckedView() {
	checkedView.setVisibility(View.GONE);
	uncheckedView.setVisibility(View.VISIBLE);
    }

    public void showCheckedView() {
	checkedView.setVisibility(View.VISIBLE);
	uncheckedView.setVisibility(View.GONE);
    }

    public void confirmLogin() {
	if (!session.isLogin()) {
	    context.startActivity(new Intent(context, LoginActivity.class));
	}
    }

    private void requestBookmark(Store store) {
	bookmarkRequest.actionBookmark(store.getId());
	requestManager.request(context, bookmarkRequest, REQUEST_TAG_BOOKMARK, this);
    }

    private void requestUnBookmark(Store store) {
	bookmarkRequest.actionUnBookmark(store.getId());
	requestManager.request(context, bookmarkRequest, REQUEST_TAG_UNBOOKMARK, this);
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.performExceptionHandling(context);
    }
}