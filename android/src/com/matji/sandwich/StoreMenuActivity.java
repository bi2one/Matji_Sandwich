package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.StoreFood;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreFoodHttpRequest;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.widget.StoreMenuListView;

import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class StoreMenuActivity extends BaseActivity implements Requestable {
	private HttpRequestManager manager;
	private HttpRequest request;

	private Store store;
	private StoreMenuListView listView;
	private LinearLayout addWrapper;
	private EditText menuField;

	private static final int ADD_MENU_REQUEST = 11;
	
	public static final String STORE = "store";

    public int setMainViewId() {
	return R.id.activity_store_menu;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_menu);
		
		manager = HttpRequestManager.getInstance(this);

		store = (Store) getIntent().getParcelableExtra(STORE);

		addWrapper = (LinearLayout) findViewById(R.id.store_menu_add_wrapper);
		menuField = (EditText) findViewById(R.id.store_menu_menu_field);

		listView = (StoreMenuListView) findViewById(R.id.store_menu_list);
		listView.setActivity(this);
		listView.requestReload();
//		HttpRequestManager.getInstance(this).request(this, request(), STORE_MENU_LIST_REQUEST, this);
		
	}
//
//	private HttpRequest request() {
//		if (request == null || !(request instanceof StoreFoodHttpRequest)) {
//			request = new StoreFoodHttpRequest(this);
//		}
//		
//		((StoreFoodHttpRequest) request).actionList(store.getId(), 1, 50);
//		
//		return request;
//	}
//	
//	@Override
//	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
//		// TODO Auto-generated method stub
//		ArrayList<StoreFood> menus = new ArrayList<StoreFood>();
//		for (MatjiData d : data) {
//			menus.add((StoreFood) d);
//		}
//		storeMenuListView.init(menus);
//	}
//
//	@Override
//	public void requestExceptionCallBack(int tag, MatjiException e) {
//		// TODO Auto-generated method stub
//		e.performExceptionHandling(this);
//	}
//}
//
//	private HttpRequest addMenuRequest(String name) {
//		if (request == null || !(request instanceof StoreFoodHttpRequest)) {
//			request = new StoreFoodHttpRequest(this);
//		}
//
//		((StoreFoodHttpRequest) request).actionNew(store.getId(), name);
//
//		return request;
//	}
//
//	private void onNewButtonClicked() {
//		if (loginRequired()) {
//			addWrapper.setVisibility(View.VISIBLE);
//			menuField.requestFocus();
//			KeyboardUtil.showKeyboard(this, menuField);
//		}
//	}
//
//	public void onConfirmButtonClicked(View veiw) {
//		String name = menuField.getText().toString().trim();
//		if (name.equals("")) {
//			Toast.makeText(getApplicationContext(), R.string.writing_content_menu, Toast.LENGTH_SHORT).show();
//		} else if (!manager.isRunning(this)) {
//			manager.request(this, addMenuRequest(name), ADD_MENU_REQUEST, this);
//		}
//	}
//
	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch (tag) {
		case ADD_MENU_REQUEST:
			if (data != null && data.get(0) != null) {
				addWrapper.setVisibility(View.GONE);
				KeyboardUtil.hideKeyboard(this);
				menuField.setText("");

				StoreFood newFood = (StoreFood) data.get(0);
				listView.addMenu(newFood);
			}
			break;
		}
	}

	@Override
	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(this);
	}
}