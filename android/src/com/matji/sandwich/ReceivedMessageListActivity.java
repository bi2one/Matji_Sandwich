package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.SessionPrivateUtil;
import com.matji.sandwich.widget.ReceivedMessageListView;

public class ReceivedMessageListActivity extends BaseActivity {

    private ReceivedMessageListView listView;
    private SessionPrivateUtil sessionPrivateUtil;
    
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
        
        sessionPrivateUtil = Session.getInstance(this).getPrivateUtil();
        
        listView = (ReceivedMessageListView) findViewById(R.id.received_message_list_view);
        listView.setLastReadMessageId(sessionPrivateUtil.getLastReadMessageId());
        listView.setActivity(this);
    }
    
    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        if (sessionPrivateUtil.getLastReadMessageId() < listView.getFirstIndexMessageId()) {
            sessionPrivateUtil.setLastReadMessageId(listView.getFirstIndexMessageId());
        }
    }
}