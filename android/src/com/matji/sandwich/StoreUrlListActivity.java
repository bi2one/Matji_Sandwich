package com.matji.sandwich;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.StoreUrlListView;
import com.matji.sandwich.widget.cell.StoreCell;
import com.matji.sandwich.widget.title.StoreTitle;

public class StoreUrlListActivity extends BaseActivity implements Refreshable {

    private StoreTitle title;
    private StoreCell storeCell;
    private StoreUrlListView listView;

    //    public static final String STORE = "StoreUrlListActivity.store";

    public int setMainViewId() {
        return R.id.activity_store_url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_url);

        title = (StoreTitle) findViewById(R.id.Titlebar);
        //        store = (Store) getIntent().getParcelableExtra(STORE);
        storeCell = new StoreCell(this, StoreMainActivity.store);

        title.setIdentifiable(this);
        title.setStore(StoreMainActivity.store);
        title.setLikeable(storeCell);

        storeCell.setIdentifiable(this);
        storeCell.addRefreshable(this);
        storeCell.addRefreshable(title);

        listView = (StoreUrlListView) findViewById(R.id.store_url_list);
        listView.setStoreId(StoreMainActivity.store.getId());
        listView.addHeaderView(storeCell);
        listView.setActivity(this);
        listView.requestReload();
    }

    @Override
    protected void onNotFlowResume() {
        super.onNotFlowResume();
        storeCell.refresh();
    }

    @Override
    public void refresh() {
        listView.refresh();
    }

    @Override
    public void refresh(MatjiData data) {
        if (data instanceof Store) {
            StoreMainActivity.store = (Store) data;
            listView.setStoreId(StoreMainActivity.store.getId());
            refresh();
        }

    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case STORE_DETAIL_INFO_TAB_ACTIVITY:
            if (resultCode == Activity.RESULT_OK) {
                StoreMainActivity.store = (Store) data.getParcelableExtra(StoreMainActivity.STORE);
                storeCell.setStore(StoreMainActivity.store);
                storeCell.refresh();
                setIsFlow(true);
            }
            break;
        }        
    }
}