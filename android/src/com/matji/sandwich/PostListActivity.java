package com.matji.sandwich;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.widget.PostListView;

/**
 * 전체 Post 리스트를 보여주는 액티비티.
 * 
 * @author mozziluv
 *
 */


public class PostListActivity extends BaseActivity implements ActivityStartable {
    public static final String TYPE = "PostListActivity.type";
    public static final String TYPE_ALL = "PostListActivity.type_all";
    public static final String TYPE_FRIEND = "PostListActivity.type_friend";
    public static final String TYPE_DOMESTIC = "PostListActivity.type_domestic";

    private PostListView listView;
    private String type;

    public int setMainViewId() {
        return R.id.activity_post_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        listView = (PostListView) findViewById(R.id.post_list_view);
        listView.setActivity(this);
        Intent intent = getIntent();
        type = intent.getStringExtra(TYPE);
        if (type == null) type = TYPE_ALL;

        if (type.equals(TYPE_ALL)) {
            listView.setType(PostListView.Type.ALL);
        } else if (type.equals(TYPE_FRIEND)) {
            listView.setType(PostListView.Type.FRIEND);
        } else if (type.equals(TYPE_DOMESTIC)) {
            listView.setType(PostListView.Type.DOMESTIC);
        } else {
            listView.setType(PostListView.Type.ALL);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.dataRefresh();
    }

    @Override
    protected void onNotFlowResume() {
        super.onNotFlowResume();
        listView.requestReload();
    }

    @Override
    public void activityResultDelivered(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case POST_MAIN_ACTIVITY:
            if (resultCode == RESULT_OK) {
                ArrayList<MatjiData> posts = data.getParcelableArrayListExtra(PostMainActivity.POSTS);
                listView.setPosts(posts);
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
}