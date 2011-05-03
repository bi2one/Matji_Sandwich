package com.matji.sandwich;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.adapter.StoreListAdapter;
import com.matji.sandwich.data.MatjiData;

import java.util.Hashtable;
import java.util.ArrayList;

public class StoreListActivity extends MatjiListActivity implements Requestable {
    // public constant
    public static final int TAG_FROM_NORMAL = 1;

    // private constant
    private final int REQ_BASIC_CONTENT = 1;
    private final int REQ_NEXT_CONTENT = 2;
    
    private HttpRequestManager manager;
    private int currentPage;
    private boolean hasMore;
    private int tag;
    private Intent intent;
    private HashTable<String, String> requestParams;
    private ArrayList<MatjiData> storeList;
    private StoreListAdapter adapter;
    private HttpRequest basicRequest;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_list);

	init();
	request(REQ_BASIC_CONTENT);
    }

    private void init() {
	super.init();
	manager = new HttpRequestManager();
	currentPage = 1;
	hasMore = true;
	intent  = getIntent();
	tag = intent.getIntExtra("tag", TAG_FROM_NORMAL);
	requestParams = new HashTable<String, String>();
	storeList = new HashTable<MatjiData>();
	basicRequest = new StoreHttpRequest();
	basicRequest.setHashTable(requestParams);
    }

    protected ListAdapter createListAdapter() {
	switch(tag) {
	case TAG_FROM_NORMAL:
	    adapter = new StoreListAdapter(mContext, storeList);
	    break;
	}
    }

    protected boolean hasMorePages() {
	return hasMore;
    }

    protected void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
	Log.d("LIST_CLICK", "position: " + position + ", id: " + id);
    }

    
    protected void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
	
    }

    private void request(int tag) {
	requestParams.clear();
	requestParams.put("type", "unlock");
	requestParams.put("order", "Store.count desc");
	requestParams.put("page", "" + currentPage);
	// test url: http://mapi.ygmaster.net/stores?type=unlock&order=Store.count%20desc

	switch(tag) {
	case REQ_BASIC_CONTENT:
	    manager.request(basicRequest, REQ_BASIC_CONTENT, this);
	    break;
	}
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	switch(tag) {
	case REQ_BASIC_CONTENT:
	    Log.d("CALLBACK", (Store)data.get(0));
	    currentPage++;
	    break;
	}
    }
}
