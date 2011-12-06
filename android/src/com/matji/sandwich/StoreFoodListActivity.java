package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.StoreFoodListView;
import com.matji.sandwich.widget.SubtitleHeader;
import com.matji.sandwich.widget.cell.StoreCell;
import com.matji.sandwich.widget.title.StoreTitle;

public class StoreFoodListActivity extends BaseActivity implements Refreshable {

    private StoreTitle title;
    private StoreCell storeCell;
    private StoreFoodListView listView;
    
    public int setMainViewId() {
        return R.id.activity_store_food;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_food);

        title = (StoreTitle) findViewById(R.id.Titlebar);
        storeCell = new StoreCell(this, StoreMainActivity.store);
        storeCell.setClickable(false);

        title.setIdentifiable(this);
        title.setStore(StoreMainActivity.store);
        title.setLikeable(storeCell);
        title.setTitle(R.string.title_store_food);

        storeCell.setStore(StoreMainActivity.store);
        storeCell.setIdentifiable(this);
        storeCell.addRefreshable(this);
        storeCell.addRefreshable(title);

        listView = (StoreFoodListView) findViewById(R.id.store_food_list);
        listView.setIdentifiable(this);
        listView.addHeaderView(storeCell);
        listView.addHeaderView(new SubtitleHeader(this, R.string.store_food_list_menu).paddingBottom());
        listView.setStore(StoreMainActivity.store);
        listView.setActivity(this);
    }

    @Override
    protected void onNotFlowResume() {
        super.onNotFlowResume();
        storeCell.refresh();
    }

    @Override
    protected void onFlowResume() {
        super.onFlowResume();
        storeCell.refresh();
    }
    
    @Override
    public void refresh() {
        listView.requestReload();
    }

    @Override
    public void refresh(MatjiData data) {
        if (data instanceof Store) {
            StoreMainActivity.store = (Store) data;
            refresh();
        }
    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {}
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_reload:
            listView.refresh();
            return true;
        }
        return false;
    }
}