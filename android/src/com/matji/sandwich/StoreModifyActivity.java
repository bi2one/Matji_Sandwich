package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.request.StoreModifyHttpRequest;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;
import com.matji.sandwich.widget.title.CompletableTitle;
import com.matji.sandwich.widget.title.CompletableTitle.Completable;
import com.matji.sandwich.widget.title.StoreTitle;

public class StoreModifyActivity extends BaseActivity implements Completable, Requestable {
	private CompletableTitle titleBar;
	private SimpleAlertDialog successDialog;
	
    public int setMainViewId() {
	return R.id.activity_main;
    }
    
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		
		setContentView(R.layout.activity_store_modify);
		
		createDialogs();

		
		titleBar = (CompletableTitle) findViewById(R.id.activity_store_modify_title);
		titleBar.setTitle(R.string.store_modify_activity_title);
		titleBar.setCompletable(this);
        
	}

	private void createDialogs() {
		successDialog = new SimpleAlertDialog(this, R.string.register_success);
	}

	@Override
	public void complete() {
		
		// TODO Auto-generated method stub
		StoreModifyHttpRequest request = new StoreModifyHttpRequest(StoreModifyActivity.this);
		request.actionModify("test","test",1.1,1.1,1);
		
	}

	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		successDialog.show();
		
	}

	@Override
	public void requestExceptionCallBack(int tag, MatjiException e) {
		// TODO Auto-generated method stub
		
	}
}
