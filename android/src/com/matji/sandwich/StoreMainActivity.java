package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.RoundTabHost;

public class StoreMainActivity extends BaseTabActivity {
    public static final String STORE = "StoreMainActivity.store";
    public static final String POSITION = "StoreMainActivity.position";
    
    private RoundTabHost tabHost;
    public static Store store;

    public int setMainViewId() {
        return R.id.activity_store_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setContentView(R.layout.activity_store_main);

        tabHost = (RoundTabHost) getTabHost();
        store = (Store) getIntent().getParcelableExtra(STORE);

        Intent storePostListIntent = new Intent(this, StorePostListActivity.class);
        Intent storeImageListIntent = new Intent(this, StoreImageListActivity.class);
		Intent storeFoodIntent = new Intent(this, StoreFoodListActivity.class);

        tabHost.addLeftTab("tab1",
                R.string.default_string_post,
                storePostListIntent);
        tabHost.addCenterTab("tab2",
                R.string.default_string_picture,
                storeImageListIntent);
        tabHost.addRightTab("tab3",
                R.string.default_string_menu,
                storeFoodIntent);
        refresh();
    }
    
    private void refresh() {

    }
    
    public void finish() {
        Intent result = new Intent();
        result.putExtra(STORE, (Parcelable)store);
        result.putExtra(POSITION, getIntent().getIntExtra(POSITION, -1));
        setResult(RESULT_OK, result);
        super.finish();
    }
}