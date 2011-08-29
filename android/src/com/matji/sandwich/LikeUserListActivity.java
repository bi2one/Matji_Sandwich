package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.widget.LikeUserListView;

/**
 * 전체 Post 리스트를 보여주는 액티비티.
 * 
 * @author mozziluv
 *
 */


public class LikeUserListActivity extends BaseActivity {
    private LikeUserListView listView;
    private MatjiData data;
    
    public static final String DATA = "data";

    public int setMainViewId() {
	return R.id.activity_like_user_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_user_list);

        data = getIntent().getParcelableExtra(DATA);
        listView = (LikeUserListView) findViewById(R.id.like_user_list_view);
        listView.setData(data);
        listView.setActivity(this);
        listView.requestReload();
    }
}