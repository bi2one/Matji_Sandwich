package com.matji.sandwich.http.request;

import android.content.Context;
import android.util.Log;

import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.session.Session;

import java.util.ArrayList;

public class NearBookmarkStoreRequest implements RequestCommand {
    private Context context;
    private HttpUtility httpUtility;
    private StoreHttpRequest storeRequest;
    private Session session;
    private int user_id;
    private double lat_sw;
    private double lat_ne;
    private double lng_sw;
    private double lng_ne;
    private int page;
    private int limit;
    private int nearByStoreIndex;
    private boolean isBookmarkListEnded;
    private int nearbyStoreStartingPage = 1;
    private ArrayList<MatjiData> bookmarkList;
    private ArrayList<MatjiData> storeList;
    private ArrayList<MatjiData> resultList;
    
    public NearBookmarkStoreRequest(Context context, int limit) {
	this.context = context;
	httpUtility = HttpUtility.getInstance();
	storeRequest = new StoreHttpRequest(context);
	session = Session.getInstance(context);
	isBookmarkListEnded = false;
	this.limit = limit;
	bookmarkList = new ArrayList<MatjiData>();
	storeList = new ArrayList<MatjiData>();
	resultList = new ArrayList<MatjiData>();
    }

    public void actionNearBookmarkedList(int user_id, double lat_sw, double lat_ne, double lng_sw, double lng_ne, int page) {
	this.user_id = user_id;
	this.lat_sw = lat_sw;
	this.lat_ne = lat_ne;
	this.lng_sw = lng_sw;
	this.lng_ne = lng_ne;
	this.page = page;

	if (page == 1) {
	    resultList.clear();
	    isBookmarkListEnded = false;
	}
    }

    public void actionCurrentUserNearBookmarkedList(double lat_sw, double lat_ne, double lng_sw, double lng_ne, int page) {
	if (session.isLogin()) {
	    actionNearBookmarkedList(session.getCurrentUser().getId(), lat_sw, lat_ne, lng_sw, lng_ne, page);
	} else {
	    actionNearBookmarkedList(0, lat_sw, lat_ne, lng_sw, lng_ne, page);
	}
    }

    public ArrayList<MatjiData> request() throws MatjiException {
	if (!isBookmarkListEnded && session.isLogin()) {
	    isBookmarkListEnded = true;
	    storeRequest.actionNearbyBookmarkedList(user_id, lat_sw, lat_ne, lng_sw, lng_ne, page);
	    // storeRequest.actionNearbyList(lat_sw, lat_ne, lng_sw, lng_ne, page, 100);
	    
	    bookmarkList = storeRequest.request();
	    nearByStoreIndex = bookmarkList.size();

	    storeRequest.actionNearbyList(lat_sw, lat_ne, lng_sw, lng_ne, page, limit);
	    storeList = storeRequest.request();
	    // resultList.addAll(bookmarkList);
	    resultList.addAll(storeList);

	    // Log.d("=====", "bookmarklist: " + bookmarkList.size());
	    return resultList;
	} else {
	    storeRequest.actionNearbyList(lat_sw, lat_ne, lng_sw, lng_ne, page, limit);
	    resultList = storeRequest.request();
	    // Log.d("=====", "storelist   : " + storeList.size());
	    return resultList;
	}
    }

    public void cancel() {
	httpUtility.disconnectConnection(storeRequest);
    }

    public int getFirstNearByStoreIndex() {
	return nearByStoreIndex;
    }

    public ArrayList<MatjiData> getBookmarkedList() {
	return bookmarkList;
    }
}