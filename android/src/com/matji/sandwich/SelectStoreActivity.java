package com.matji.sandwich;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.widget.SelectStoreContents;
import com.matji.sandwich.widget.title.CompletableTitle;

public class SelectStoreActivity extends BaseActivity implements CompletableTitle.Completable,
SelectStoreContents.OnClickListener {
	public static final int INTENT_SEARCH_LOCATION = 0;
	public static final int INTENT_WRITE_STORE = 1;
	public static final String DATA_STORE = "SelectStoreActivity.store";

	private Context context;
	private CompletableTitle titleBar;
	private SelectStoreContents storeContents;
	private SessionMapUtil sessionMapUtil;
	private Intent passedIntent;
	private Store passedStore;

	public int setMainViewId() {
		return R.id.activity_select_store;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_store);

		context = getApplicationContext();
		sessionMapUtil = new SessionMapUtil(context);
		titleBar = (CompletableTitle)findViewById(R.id.activity_select_store_title);
		titleBar.setTitle(R.string.select_store_activity_title);
		titleBar.setCompletable(this);

		storeContents = (SelectStoreContents)findViewById(R.id.activity_write_post_store_contents);
		storeContents.init(this);
		storeContents.setOnClickListener(this);

		passedIntent = getIntent();
		passedStore = (Store)passedIntent.getParcelableExtra(DATA_STORE);
		if (passedStore != null) 
			storeContents.setStore(passedStore);
		storeContents.refresh(sessionMapUtil.getNEBound(), sessionMapUtil.getSWBound());
	}

	public void complete() {
		Intent result = new Intent();
		result.putExtra(DATA_STORE, (Parcelable)storeContents.getStore());
		setResult(RESULT_OK, result);
		finish();
	}

	public void onSearchLocationClick(View v) {
		startActivityForResult(new Intent(this, GetMapPositionActivity.class), INTENT_SEARCH_LOCATION);
	}

	public void onWriteStoreClick(View v) {
		startActivityForResult(new Intent(this, WriteStoreActivity.class), INTENT_WRITE_STORE);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return ;

		switch(requestCode) {
		case INTENT_SEARCH_LOCATION:
			int latNe = data.getIntExtra(GetMapPositionActivity.DATA_NE_LAT, GetMapPositionActivity.BASIC_NE_LAT);
			int latSw = data.getIntExtra(GetMapPositionActivity.DATA_SW_LAT, GetMapPositionActivity.BASIC_SW_LAT);
			int lngNe = data.getIntExtra(GetMapPositionActivity.DATA_NE_LNG, GetMapPositionActivity.BASIC_NE_LNG);
			int lngSw = data.getIntExtra(GetMapPositionActivity.DATA_SW_LNG, GetMapPositionActivity.BASIC_SW_LNG);
			storeContents.refresh(latNe, lngNe, latSw, lngSw);
			break;
		case INTENT_WRITE_STORE:
			Store store = (Store)data.getParcelableExtra(WriteStoreActivity.DATA_STORE);
			storeContents.setStore(store);
			break;
		}
	}
}