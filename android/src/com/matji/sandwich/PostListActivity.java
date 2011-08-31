package com.matji.sandwich;

import java.util.ArrayList;

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
    private PostListView listView;

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
        listView.requestReload();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.dataRefresh();
    }

    @Override
    public void activityResultDelivered(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case POST_ACTIVITY:
            if (resultCode == RESULT_OK) {
                ArrayList<MatjiData> posts = data.getParcelableArrayListExtra(PostActivity.POSTS);
                listView.setPosts(posts);
                if (getParent() instanceof BaseTabActivity) {
                    ((BaseTabActivity) getParent()).setIsFlow(true);
                }
            }
        }
    }
}