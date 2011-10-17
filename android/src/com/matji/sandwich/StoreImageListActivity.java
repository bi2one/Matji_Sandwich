package com.matji.sandwich;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
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

    //    public static final String STORE = "store";
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

        //        store = (Store) getIntent().getParcelableExtra(STORE);
        title = (StoreTitle) findViewById(R.id.Titlebar);
        storeCell = new StoreCell(this, StoreMainActivity.store);
        listView = (StoreImageListView) findViewById(R.id.store_image_list_view);

        title.setIdentifiable(this);
        title.setStore(StoreMainActivity.store);
        title.setLikeable(storeCell);
        title.setTitle(R.string.title_store_image);

        storeCell.setIdentifiable(this);
        storeCell.setStore(StoreMainActivity.store);
        storeCell.addRefreshable(this);
        storeCell.addRefreshable(title);

        listView.setStore(StoreMainActivity.store);
        listView.addHeaderView(storeCell);
        listView.addHeaderView(new SubtitleHeader(
                this, 
                String.format(
                        MatjiConstants.string(R.string.subtitle_store_image),
                        StoreMainActivity.store.getImageCount())));
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
            StoreMainActivity.store = (Store) data;
            listView.setStore(StoreMainActivity.store);
            refresh();
        }
    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case STORE_DETAIL_INFO_TAB_ACTIVITY:
            if (resultCode == Activity.RESULT_OK) {
                StoreMainActivity.store = (Store) data.getParcelableExtra(StoreMainActivity.STORE);
                setIsFlow(true);
            }
            break;
        }
    }
}