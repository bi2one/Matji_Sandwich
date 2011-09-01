package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.ReceivedUserListView;

import android.os.Bundle;

public class ReceivedUserActivity extends BaseActivity {
    public int setMainViewId() {
        return R.id.activity_received_user;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_user);

        ReceivedUserListView listView = (ReceivedUserListView) findViewById(R.id.received_user_list);
        listView.setActivity(this);
        listView.requestReload();
    }
}