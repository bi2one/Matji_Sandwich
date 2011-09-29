package com.matji.sandwich;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.http.request.PostHttpRequest.TagByType;
import com.matji.sandwich.widget.TagPostListView;
import com.matji.sandwich.widget.cell.StoreCell;
import com.matji.sandwich.widget.title.StoreTitle;

public class StoreTagPostListActivity extends BaseActivity implements Refreshable {
    private StoreTitle title;
    private TagPostListView listView;
    private StoreCell storeCell;
    private Tag tag;
    public static final String TAG = "StoreTagPostListActivity.tag";

    public int setMainViewId() {
        return R.id.activity_store_tag_post;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_tag_post);
        
        tag = getIntent().getParcelableExtra(TAG);

        title = (StoreTitle) findViewById(R.id.Titlebar);
        storeCell = new StoreCell(this, StoreMainActivity.store);
        listView = (TagPostListView) findViewById(R.id.store_tag_post_list_view);

        title.setIdentifiable(this);
        title.setStore(StoreMainActivity.store);
        title.setLikeable(storeCell);

        storeCell.setIdentifiable(this);
        storeCell.addRefreshable(this);
        storeCell.addRefreshable(title);

        listView.addHeaderView(storeCell);
        listView.setTag(tag);
        listView.setType(TagByType.STORE);
        listView.setActivity(this);
        listView.requestReload();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        listView.dataRefresh();
        storeCell.refresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case POST_MAIN_ACTIVITY:
            if (resultCode == RESULT_OK) {
                ArrayList<MatjiData> posts = data.getParcelableArrayListExtra(PostMainActivity.POSTS);
                listView.setPosts(posts);
                setIsFlow(true);
            }
            break;
        case USER_PROFILE_TAB_ACTIVITY:
            if (resultCode == RESULT_OK) {
                StoreMainActivity.store = (Store) data.getParcelableExtra(StoreMainActivity.STORE);
                storeCell.setStore(StoreMainActivity.store);
                storeCell.refresh();
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
    public void refresh() {}

    @Override
    public void refresh(MatjiData data) {
        if (data instanceof Store){
            StoreMainActivity.store= (Store) data;
            refresh();
        }
    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {}
}