package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.matji.sandwich.LoginActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.Bookmark;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.BookmarkHttpRequest;
import com.matji.sandwich.http.spinner.SpinnerFactory.SpinnerType;
import com.matji.sandwich.session.Session;

public class BookmarkStarToggleView extends LinearLayout implements OnClickListener,
Requestable {
    private boolean isUnchecked;
    private ImageView uncheckedView;
    private ImageView checkedView;
    private Store store;
    private ArrayList<MatjiData> bookmarkedList;
//    private BaseAdapter adapter;
    private Session session;
    private HttpRequestManager requestManager;
    private BookmarkHttpRequest bookmarkRequest;
    private ViewGroup spinnerContainer;
    private DBProvider dbProvider;

    public BookmarkStarToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.bookmark_star_toggle, this, true);
        setOnClickListener(this);
        isUnchecked = true;
        uncheckedView = (ImageView)findViewById(R.id.bookmark_star_toggle_unchecked);
        checkedView = (ImageView)findViewById(R.id.bookmark_star_toggle_checked);
        session = Session.getInstance(context);
        requestManager = HttpRequestManager.getInstance();
        bookmarkRequest = new BookmarkHttpRequest(context);
        dbProvider = DBProvider.getInstance(context);
    }

    public void init(Store store, ViewGroup spinnerContainer) { 
        this.store = store;
        this.spinnerContainer = spinnerContainer;
        if (dbProvider.isExistBookmark(store.getId(), DBProvider.STORE)) {
            showCheckedView();
            isUnchecked = false;
        } else {
            showUncheckedView();
            isUnchecked = true;
        }
    }
    
    // public void init(BaseAdapter adapter, ArrayList<MatjiData> bookmarkedList, Store store, ViewGroup spinnerContainer) {
    //     this.store = store;
    //     this.bookmarkedList = bookmarkedList;
    //     this.adapter = adapter;
    //     this.spinnerContainer = spinnerContainer;
    //     if (isBookmarked(bookmarkedList, store)) {
    //         showCheckedView();
    //         isUnchecked = false;
    //     } else {
    //         showUncheckedView();
    //         isUnchecked = true;
    //     }
    // }

//    private boolean isBookmarked(ArrayList<MatjiData> bookmarkedList, Store store) {
//        for (MatjiData bookmarkedStore : bookmarkedList) {
//            if (store.equals((Store) bookmarkedStore)) {
//                return true;
//            }
//        }
//        return false;
//    }

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

        if (bookmarkedList != null) {
            bookmarkByBookmarkedList();
        } else {
            bookmarking();
        }
    }
    
    public void bookmarking() {
        setClickable(false);
        if (isUnchecked) {
            if (session.isLogin()) {
                // adapter.notifyDataSetChanged();
                requestBookmark(store);
                // Log.d("=====", "isUnchecked2: " + isUnchecked);
            } else {
                confirmLogin();
            }
        } else {
            // adapter.notifyDataSetChanged();
            requestUnBookmark(store);
        }
    }
    
    public void bookmarkByBookmarkedList() {
        if (isUnchecked) {
            if (session.isLogin()) {
                bookmarkedList.add(store);
                // adapter.notifyDataSetChanged();
                requestBookmark(store);
                showCheckedView();
                isUnchecked = false;
                // Log.d("=====", "isUnchecked2: " + isUnchecked);
            } else {
                confirmLogin();
            }
        } else {
            removeBookmarkedList(store);
            // adapter.notifyDataSetChanged();
            requestUnBookmark(store);
            showUncheckedView();
            isUnchecked = true;
        }
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        setClickable(true);
        switch (tag) {
        case HttpRequestManager.BOOKMAR_REQUEST:
            showCheckedView();
            isUnchecked = false;
            Bookmark bookmark = new Bookmark();
            bookmark.setForeignKey(store.getId());
            bookmark.setObject(DBProvider.STORE);
            dbProvider.insertBookmark(bookmark);
            session.getCurrentUser().setBookmarkStoreCount(session.getCurrentUser().getBookmarkStoreCount() + 1);
            break;
        case HttpRequestManager.UN_BOOKMARK_REQUEST:
            showUncheckedView();
            isUnchecked = true;
            dbProvider.deleteBookmark(store.getId(), DBProvider.STORE);
            session.getCurrentUser().setBookmarkStoreCount(session.getCurrentUser().getBookmarkStoreCount() - 1);
            break;
        }
    }

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
            getContext().startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }

    private void requestBookmark(Store store) {
        bookmarkRequest.actionBookmark(store.getId());
        requestManager.request(getContext(), spinnerContainer, SpinnerType.SMALL, bookmarkRequest, HttpRequestManager.BOOKMAR_REQUEST, this);
    }

    private void requestUnBookmark(Store store) {
        bookmarkRequest.actionUnBookmark(store.getId());
        requestManager.request(getContext(), spinnerContainer, SpinnerType.SMALL, bookmarkRequest, HttpRequestManager.UN_BOOKMARK_REQUEST, this);
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
        setClickable(true);
        e.performExceptionHandling(getContext());
    }
}