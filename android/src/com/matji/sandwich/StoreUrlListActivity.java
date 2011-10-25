package com.matji.sandwich;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.http.request.UrlHttpRequest.UrlType;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.SubtitleHeader;
import com.matji.sandwich.widget.UrlListView;
import com.matji.sandwich.widget.cell.StoreCell;
import com.matji.sandwich.widget.title.StoreTitle;

public class StoreUrlListActivity extends BaseActivity implements Refreshable {

    private StoreTitle title;
    private StoreCell storeCell;
    private UrlListView listView;

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
        title.setTitle(R.string.title_store_url);

        storeCell.setIdentifiable(this);
        storeCell.addRefreshable(this);
        storeCell.addRefreshable(title);

        listView = (UrlListView) findViewById(R.id.store_url_list);
        listView.setUrlType(UrlType.STORE);
        listView.setId(StoreMainActivity.store.getId());
        listView.addHeaderView(storeCell);
        listView.addHeaderView(
                new SubtitleHeader(
                        this,
                        String.format(
                                MatjiConstants.string(R.string.subtitle_url),
                                StoreMainActivity.store.getUrlCount())));
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
            listView.setId(StoreMainActivity.store.getId());
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
        case WRITE_POST_ACTIVITY:
            Activity parent = getParent();
            if (parent instanceof BaseTabActivity) {
                ((BaseTabActivity) parent).getTabHost().setCurrentTab(0);
            }
            break;
        }        
    }
    
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