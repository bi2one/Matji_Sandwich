package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.SessionPrivateUtil;
import com.matji.sandwich.widget.AlarmListView;
import com.matji.sandwich.widget.title.HomeTitle;

public class AlarmActivity extends BaseActivity {
    
    private AlarmListView listView;
    private SessionPrivateUtil sessionPrivateUtil;
    
    public int setMainViewId() {
        return R.id.activity_alarm;
    }

    @Override
    protected void init() {
        super.init();

        setContentView(R.layout.activity_alarm);

        sessionPrivateUtil = Session.getInstance(this).getPrivateUtil();
        
        HomeTitle title = (HomeTitle) findViewById(R.id.Titlebar);
        title.setTitle(R.string.default_string_alarm);
        listView = (AlarmListView) findViewById(R.id.alarm_list);
        listView.setLastReadAlarmId(sessionPrivateUtil.getLastReadAlarmId());
        listView.setActivity(this);
        listView.requestReload();
    }
    
    @Override
    public void finish() {
        super.finish();
        if (sessionPrivateUtil.getLastReadAlarmId() < listView.getLastReadAlarmId()) {
            sessionPrivateUtil.setLastReadAlarmId(listView.getLastReadAlarmId());
            sessionPrivateUtil.setNewAlarmCount(0);
        }
    }
}