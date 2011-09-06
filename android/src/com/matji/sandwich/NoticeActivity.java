package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.SessionPrivateUtil;
import com.matji.sandwich.widget.NoticeListView;
import com.matji.sandwich.widget.title.HomeTitle;

public class NoticeActivity extends BaseActivity {

    private NoticeListView listView;
    private SessionPrivateUtil sessionPrivateUtil;

    public int setMainViewId() {
        return R.id.activity_notice;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        HomeTitle title = (HomeTitle) findViewById(R.id.Titlebar);
        title.setTitle(R.string.default_string_notice);
        
        sessionPrivateUtil = Session.getInstance(this).getPrivateUtil();
        
        listView = (NoticeListView) findViewById(R.id.notice_list);
        listView.setLastReadNoticeId(sessionPrivateUtil.getLastReadNoticeId());
        listView.setActivity(this);
        listView.requestReload();
    }
    
    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        if (sessionPrivateUtil.getLastReadNoticeId() < listView.getFirstIndexNoticeId()) {
            sessionPrivateUtil.setLastReadNoticeId(listView.getFirstIndexNoticeId());
            sessionPrivateUtil.setNewNoticeCount(0);
        }
    }
}
