package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.StoreModifyHttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;
import com.matji.sandwich.widget.dialog.SimpleDialog;
import com.matji.sandwich.widget.title.CompletableTitle;
import com.matji.sandwich.widget.title.CompletableTitle.Completable;
import com.matji.sandwich.widget.title.StoreTitle;

public class StoreModifyActivity extends BaseActivity implements Completable, Requestable {
	private CompletableTitle titleBar;
	private SimpleAlertDialog successDialog;
	
	private Store store;
	
	public static final String STORE = "StoreModifyActivity.store";
		
	public int setMainViewId() {
	return R.id.activity_main;
    }
    
	@Override
	protected void init() {
		setContentView(R.layout.activity_store_modify);

		store = (Store) getIntent().getParcelableExtra(STORE);
		
		createDialogs();
		setLiteners();
		titleBar = (CompletableTitle) findViewById(R.id.activity_store_modify_title);
		titleBar.setTitle(R.string.store_modify_activity_title);
		titleBar.setCompletable(this);
        
	}

	private void setLiteners() {
		successDialog.setOnClickListener(new SimpleAlertDialog.OnClickListener() {
			
			@Override
			public void onConfirmClick(SimpleDialog dialog) {
				finish();
			}
		});
	}

	private void createDialogs() {
		successDialog = new SimpleAlertDialog(this, R.string.register_success);
	}

	@Override
	public void complete() {
		HttpRequestManager manager = HttpRequestManager.getInstance();
		StoreModifyHttpRequest request = new StoreModifyHttpRequest(StoreModifyActivity.this);
		request.actionModify("test","test",1.1,1.1,1);
		manager.request(getApplicationContext(), getMainView(), request, 0, this);
	}

	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		successDialog.show();
	}

	@Override
	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(this);
	}

}
