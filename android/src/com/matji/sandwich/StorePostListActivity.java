package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.StorePostListView;

import android.content.Intent;
import android.os.Bundle;

public class StorePostListActivity extends BaseActivity {
    private Store store;
    private StorePostListView listView;

    public static final String STORE = "store";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_post);
        store = (Store) getIntent().getParcelableExtra(STORE);

        listView = (StorePostListView) findViewById(R.id.store_post_list);
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