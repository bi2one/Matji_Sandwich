package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.RoundTabHost;

public class StoreMainActivity extends BaseTabActivity {
    public final static String DATA_STORE = "StoreMainActivity.store";
    private RoundTabHost tabHost;
    public static Store store;

    public final static String STORE = "store";

    public int setMainViewId() {
        return R.id.activity_store_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();	    

        setContentView(R.layout.activity_store_main);

        tabHost = (RoundTabHost) getTabHost();
        store = (Store) getIntent().getParcelableExtra(STORE);

        Intent storePostListIntent = new Intent(this, StorePostListActivity.class);
        Intent storeImageListIntent = new Intent(this, StoreImageListActivity.class);
        Intent storeUrlListIntent = new Intent(this, StoreUrlListActivity.class);

        tabHost.addLeftTab("tab1",
                R.string.store_main_post_list_view,
                storePostListIntent);
        tabHost.addCenterTab("tab2",
                R.string.store_main_img,
                storeImageListIntent);
        tabHost.addRightTab("tab3",
                R.string.store_main_review,
                storeUrlListIntent);
        refresh();
    }

    private void refresh() {

    }
    
    public void finish() {
        Intent result = new Intent();
        result.putExtra(DATA_STORE, (Parcelable)store);
        setResult(RESULT_OK, result);
        super.finish();
    }
}