package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.LikeStoreListView;
import com.matji.sandwich.widget.title.HomeTitle;

/**
 * 전체 Post 리스트를 보여주는 액티비티.
 * 
 * @author mozziluv
 *
 */


public class LikeStoreListActivity extends BaseActivity {
    private LikeStoreListView listView;
    private User user;
    
    public static final String USER = "user";

    public int setMainViewId() {
	return R.id.activity_like_store_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_store_list);

        ((HomeTitle) findViewById(R.id.Titlebar)).setTitle(R.string.default_string_like);
        user = getIntent().getParcelableExtra(USER);
        listView = (LikeStoreListView) findViewById(R.id.like_store_list_view);
        listView.setUser(user);
        listView.setActivity(this);
        listView.requestReload();
    }
}