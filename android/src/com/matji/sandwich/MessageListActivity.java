package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.MessageListView;

public class MessageListActivity extends BaseActivity {

    private MessageListView listView;

    public int setMainViewId() {
        return R.id.activity_message_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        
        listView = (MessageListView) findViewById(R.id.message_list_view);
        listView.setActivity(this);
        listView.requestReload();
    }
}