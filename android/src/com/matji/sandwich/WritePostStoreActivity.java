package com.matji.sandwich;

import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.widget.WritePostStoreListView;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.adapter.WritePostStoreAdapter.StoreElement;
import com.matji.sandwich.session.SessionWritePostUtil;

public class WritePostStoreActivity extends BaseActivity implements OnItemClickListener {
    private Context context;
    private WritePostStoreListView listView;
    private TextView selectedText;
    private WritePostTabActivity parentActivity;
    private SessionWritePostUtil sessionUtil;
    
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	context = getApplicationContext();
	setContentView(R.layout.activity_write_post_store);
	selectedText = (TextView)findViewById(R.id.activity_write_post_store_selected); 
	listView = (WritePostStoreListView)findViewById(R.id.activity_write_post_store_listview);
	listView.init(this);
	listView.setOnItemClickListener(this);
	parentActivity = (WritePostTabActivity)getParent();
	sessionUtil = new SessionWritePostUtil(context);
    }

    protected void onNotFlowResume() {
	listView.requestReload();
    }

    protected void onResume() {
	super.onResume();
	KeyboardUtil.hideKeyboard(this);
    }

    public void onItemClick(AdapterView parent, View view, int position, long id) {
	StoreElement element = (StoreElement)view.getTag();
	Store store = element.getStore();
	
	selectedText.setText(store.getName());
	sessionUtil.setStoreId(store.getId());
	parentActivity.onChecked(WritePostTabActivity.TAB_ID_STORE);
    }
}