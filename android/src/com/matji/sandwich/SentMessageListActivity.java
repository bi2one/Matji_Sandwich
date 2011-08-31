package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.SentMessageListView;

public class SentMessageListActivity extends BaseActivity {

    private SentMessageListView listView;

    public int setMainViewId() {
        return R.id.activity_sent_message_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_message_list);
        
        listView = (SentMessageListView) findViewById(R.id.sent_message_list_view);
        listView.setActivity(this);
        listView.requestReload();
    }
}