package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.RoundTabHost;

public class StoreDetailInfoTabActivity extends BaseTabActivity {
	private RoundTabHost tabHost;
	public static Store store;
	
	public static final String STORE = "StoreDetailIfoTabActivity.store";

    public int setMainViewId() {
	return R.id.activity_store_detail_tab;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void init() {
		super.init();
		setContentView(R.layout.activity_store_detail_tab);
		
		store = (Store) getIntent().getParcelableExtra(STORE);

		setTabHost();
	}
	
	private void setTabHost() {
		Intent defaultInfoIntent = new Intent(this, StoreDefaultInfoActivity.class);
        Intent tagIntent = new Intent(this, StoreTagActivity.class);
        Intent storeFoodIntent = new Intent(this, StoreFoodListActivity.class);
        
		tabHost = (RoundTabHost)getTabHost();
		
		tabHost.addLeftTab("tab1", 
				R.string.default_string_info, defaultInfoIntent);
		tabHost.addCenterTab("tab2", 
				R.string.default_string_tag, tagIntent);
		tabHost.addRightTab("tab3", 
				R.string.default_string_menu, storeFoodIntent);
	}
	
	@Override
	public void finish() {
	    Intent intent = new Intent();
	    intent.putExtra(StoreMainActivity.STORE, (Parcelable) store);
	    Log.d("Matji", store.getLikeCount()+"*");
	    setResult(RESULT_OK, intent);
	    super.finish();
	}
}