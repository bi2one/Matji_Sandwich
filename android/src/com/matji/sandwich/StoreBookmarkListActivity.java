package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.StoreBookmarkListView;
import com.matji.sandwich.widget.title.HomeTitle;

public class StoreBookmarkListActivity extends BaseActivity {
    private HomeTitle title;
    private StoreBookmarkListView listView;
    
    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.activity_store_bookmark_list);
        
        title = (HomeTitle) findViewById(R.id.Titlebar);
        title.setTitle(R.string.store_bookmark_list);
        
        listView = (StoreBookmarkListView) findViewById(R.id.store_bookmark_list);
        listView.setActivity(this);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        listView.requestReload();
    }    

    @Override
    public int setMainViewId() {
        // TODO Auto-generated method stub
        return R.id.activity_store_bookmark;
    }
}