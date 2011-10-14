package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.widget.RoundTabHost;
import com.matji.sandwich.widget.title.MessageTitle;

public class MessageListTabActivity extends BaseTabActivity {
    private RoundTabHost tabHost;
    private MessageTitle title;

    public int setMainViewId() {
        return R.id.activity_message_list;
    }

    /**
     * Activity생성시 실행하는 메소드
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setContentView(R.layout.activity_message_list);
        
        title = (MessageTitle) findViewById(R.id.Titlebar);
        title.setIdentifiable(this);
        
        tabHost = (RoundTabHost) getTabHost();

        tabHost.addLeftTab("tab1",
                R.string.received_message_list,
                new Intent(this, ReceivedMessageListActivity.class));
        tabHost.addRightTab("tab2",
                R.string.sent_message_list,
                new Intent(this, SentMessageListActivity.class));
    }
}