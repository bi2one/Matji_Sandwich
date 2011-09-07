package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.StorePostListView;
import com.matji.sandwich.widget.cell.StoreCell;
import com.matji.sandwich.widget.cell.StoreInfoCell;
import com.matji.sandwich.widget.title.StoreTitle;

public class StorePostListActivity extends BaseActivity {
    private Store store;
    private StoreTitle title;
    private StoreCell storeCell;
    private StoreInfoCell storeInfoCell;
    private StorePostListView listView;

    public static final String STORE = "store";

    public int setMainViewId() {
	return R.id.activity_store_post;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_post);
        store = (Store) getIntent().getParcelableExtra(STORE);

        title = (StoreTitle) findViewById(R.id.Titlebar);
        storeCell = new StoreCell(this, store);
        storeInfoCell = new StoreInfoCell(this, store);
        
        title.setIdentifiable(this);
        title.setSpinnerContainer(getMainView());
        title.setRefreshable(storeCell);
        title.setStore(store);
        
        storeCell.setMainView(getMainView());
        storeCell.showLine();
        
        listView = (StorePostListView) findViewById(R.id.store_post_list);
        listView.addHeaderView(storeCell);
        listView.addHeaderView(storeInfoCell);
        listView.setStore(store);
        listView.setActivity(this);
        listView.requestReload();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.dataRefresh();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case WRITE_POST_ACTIVITY:
            if (resultCode == RESULT_OK) {
                listView.requestReload();
            }
            break;

        case POST_ACTIVITY:
            if (resultCode == RESULT_OK) {
                ArrayList<MatjiData> posts = data.getParcelableArrayListExtra(PostActivity.POSTS);
                listView.setPosts(posts);
            }
            break;
        }
    }
}