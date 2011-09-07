package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.StoreDiscoverListView;
import com.matji.sandwich.widget.title.HomeTitle;

public class StoreDiscoverListActivity extends BaseActivity {
    private User user;

    private HomeTitle title;
    private StoreDiscoverListView listView;

    public static final String USER = "StoreDiscoverListActivity.user";
    
    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.activity_store_discover_list);
        
        user = (User) getIntent().getParcelableExtra(USER);
        title = (HomeTitle) findViewById(R.id.Titlebar);
        title.setTitle(R.string.store_discover_list);
        
        listView = (StoreDiscoverListView) findViewById(R.id.store_discover_list);
        listView.setActivity(this);
        listView.setUser(user);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        listView.requestReload();
    }    

    @Override
    public int setMainViewId() {
        // TODO Auto-generated method stub
        return R.id.activity_store_discover;
    }
}