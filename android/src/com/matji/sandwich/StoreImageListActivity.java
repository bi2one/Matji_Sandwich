package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.StoreImageListView;
import com.matji.sandwich.widget.SubtitleHeader;
import com.matji.sandwich.widget.cell.StoreCell;
import com.matji.sandwich.widget.title.StoreTitle;

public class StoreImageListActivity extends BaseActivity implements Refreshable {

    public static final String STORE = "store";

    private Store store;
    private StoreTitle title;
    private StoreCell storeCell;
    private StoreImageListView listView;

    public int setMainViewId() {
        return R.id.activity_store_image_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_image_list);

        store = (Store) getIntent().getParcelableExtra(STORE);        
        title = (StoreTitle) findViewById(R.id.Titlebar);
        storeCell = new StoreCell(this, store);
        listView = (StoreImageListView) findViewById(R.id.store_image_list_view);

        title.setIdentifiable(this);
        title.setStore(store);
        title.setLikeable(storeCell);

        storeCell.setIdentifiable(this);
        storeCell.setStore(store);
        storeCell.addRefreshable(this);
        storeCell.addRefreshable(title);
        
        listView.setStore(store);
        listView.addHeaderView(storeCell);
        listView.addHeaderView(new SubtitleHeader(
                this, 
                String.format(
                        MatjiConstants.string(R.string.subtitle_store_image),
                        store.getImageCount())));
        listView.setActivity(this);
        listView.requestReload();
    }

    @Override
    protected void onNotFlowResume() {
        // TODO Auto-generated method stub
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
            listView.setStore(store);
            refresh();
        }
    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {}
}