package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.ReceivedMessageListView;

public class ReceivedMessageListActivity extends BaseActivity {

    private ReceivedMessageListView listView;
    
    public int setMainViewId() {
        return R.id.activity_received_message_list;
    }

    @Override
    protected void onNotFlowResume() {
        // TODO Auto-generated method stub
        super.onFlowResume();
        listView.requestReload();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_message_list);
        
        listView = (ReceivedMessageListView) findViewById(R.id.received_message_list_view);
        listView.setActivity(this);
    }
}