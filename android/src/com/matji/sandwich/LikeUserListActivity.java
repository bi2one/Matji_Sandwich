package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.widget.LikeUserListView;
import com.matji.sandwich.widget.title.HomeTitle;

public class LikeUserListActivity extends BaseActivity {
    
    private HomeTitle title;
    private LikeUserListView listView;
    private MatjiData data;

    public static final String DATA = "LlikeUserListActivity.data";

    public int setMainViewId() {
        return R.id.activity_like_user_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_user_list);

        data = getIntent().getParcelableExtra(DATA);
        
        title = (HomeTitle) findViewById(R.id.Titlebar);
        title.setTitle(R.string.default_string_like);
        
        listView = (LikeUserListView) findViewById(R.id.like_user_list_view);
        listView.setData(data);
        listView.setActivity(this);
        listView.requestReload();
    }
}