package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.StoreUrlListView;
import com.matji.sandwich.widget.cell.StoreCell;
import com.matji.sandwich.widget.title.StoreTitle;

public class StoreUrlListActivity extends BaseActivity implements Refreshable {

    private StoreTitle title;
    private Store store;
    private StoreCell storeCell;
    private StoreUrlListView listView;

    public static final String STORE = "StoreUrlListActivity.store";

    public int setMainViewId() {
        return R.id.activity_store_url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_url);

        title = (StoreTitle) findViewById(R.id.Titlebar);
        store = (Store) getIntent().getParcelableExtra(STORE);
        storeCell = new StoreCell(this, store);

        title.setIdentifiable(this);
        title.setStore(store);
        title.setLikeable(storeCell);

        storeCell.setIdentifiable(this);
        storeCell.addRefreshable(this);
        storeCell.addRefreshable(title);

        listView = (StoreUrlListView) findViewById(R.id.store_url_list);
        listView.setStoreId(store.getId());
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
            this.store = (Store) data;
            listView.setStoreId(store.getId());
            refresh();
        }

    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {}
}