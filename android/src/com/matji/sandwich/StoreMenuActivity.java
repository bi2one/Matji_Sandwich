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
import com.matji.sandwich.widget.title.TitleButton;
import com.matji.sandwich.widget.title.TitleText;

import android.os.Bundle;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_menu);

		manager = HttpRequestManager.getInstance(this);

		store = (Store) SharedMatjiData.getInstance().top();

		addWrapper = (LinearLayout) findViewById(R.id.store_menu_add_wrapper);
		menuField = (EditText) findViewById(R.id.store_menu_menu_field);

		listView = (StoreMenuListView) findViewById(R.id.store_menu_list);
		listView.setActivity(this);
		listView.requestReload();
	}

	private HttpRequest addMenuRequest(String name) {
		if (request == null || !(request instanceof StoreFoodHttpRequest)) {
			request = new StoreFoodHttpRequest(this);
		}

		((StoreFoodHttpRequest) request).actionNew(store.getId(), name);

		return request;
	}

	@Override
	protected View setCenterTitleView() {
		return new TitleText(this, "StoreMenuActivity");
	}
	
	@Override
	protected View setRightTitleView() {
		return new TitleButton(this, "New") {
			
			@Override
			public void onClick(View v) {
				onNewButtonClicked();
			}
		};
	}

	private void onNewButtonClicked() {
		if (loginRequired()) {
			addWrapper.setVisibility(View.VISIBLE);
			menuField.requestFocus();
			KeyboardUtil.showKeyboard(this, menuField);
		}
	}

	public void onConfirmButtonClicked(View veiw) {
		String name = menuField.getText().toString().trim();
		if (name.equals("")) {
			Toast.makeText(getApplicationContext(), R.string.writing_content_menu, Toast.LENGTH_SHORT).show();
		} else if (!manager.isRunning(this)) {
			manager.request(this, addMenuRequest(name), ADD_MENU_REQUEST, this);
		}
	}

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