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
import com.matji.sandwich.widget.StorePostListView;
import com.matji.sandwich.widget.cell.StoreCell;
import com.matji.sandwich.widget.cell.StoreInfoCell;
import com.matji.sandwich.widget.title.StoreTitle;

public class StorePostListActivity extends BaseActivity implements Refreshable {
    private StoreTitle title;
    private StoreCell storeCell;
    private StoreInfoCell storeInfoCell;
    private StorePostListView listView;

    //    public static final String STORE = "store";

    public int setMainViewId() {
        return R.id.activity_store_post;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_post);
        //        store = (Store) getIntent().getParcelableExtra(STORE);

        title = (StoreTitle) findViewById(R.id.Titlebar);
        storeCell = new StoreCell(this, StoreMainActivity.store);
        storeInfoCell = new StoreInfoCell(this, StoreMainActivity.store);
        listView = (StorePostListView) findViewById(R.id.store_post_list);

        title.setIdentifiable(this);
        title.setStore(StoreMainActivity.store);
        title.setLikeable(storeCell);
        title.setTitle(R.string.title_store_post);

        storeCell.setIdentifiable(this);
        storeCell.addRefreshable(this);
        storeCell.addRefreshable(title);
        storeCell.showLine();

        listView.addHeaderView(storeCell);
        listView.addHeaderView(storeInfoCell);
        listView.setStore(StoreMainActivity.store);
        listView.setActivity(this);
        storeCell.refresh();
    }

    @Override
    protected void onNotFlowResume() {
        // TODO Auto-generated method stub
        super.onNotFlowResume();
        storeCell.refresh();
    }

    @Override
    protected void onFlowResume() {
        super.onFlowResume();
        listView.dataRefresh();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case WRITE_POST_ACTIVITY:
            if (resultCode == RESULT_OK) {
                listView.requestReload();
                setIsFlow(true);
            }
            break;

        case POST_MAIN_ACTIVITY:
            if (resultCode == RESULT_OK) {
                ArrayList<MatjiData> posts = data.getParcelableArrayListExtra(PostMainActivity.POSTS);
                listView.setPosts(posts);
                setIsFlow(true);
            }
            break;        

        case STORE_DETAIL_INFO_TAB_ACTIVITY:
            if (resultCode == Activity.RESULT_OK) {
                StoreMainActivity.store = (Store) data.getParcelableExtra(StoreMainActivity.STORE);
                storeCell.setStore(StoreMainActivity.store);
                storeCell.refresh();
                setIsFlow(true);
            }
            break;
        case STORE_MAIN_ACTIVITY: case USER_MAIN_ACTIVITY: case IMAGE_SLIDER_ACTIVITY:            
            if (resultCode == Activity.RESULT_OK) {
                setIsFlow(true);
            }
            break;

        }
    }

    @Override
    public void setIsFlow(boolean isFlow) {
        if (getParent() instanceof BaseTabActivity) {
            ((BaseTabActivity) getParent()).setIsFlow(true);
        }
        super.setIsFlow(isFlow);
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