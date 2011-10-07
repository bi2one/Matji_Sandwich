package com.matji.sandwich;

import android.content.Intent;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.AlarmListView;
import com.matji.sandwich.widget.title.HomeTitle;

public class AlarmActivity extends BaseActivity {

    private AlarmListView listView;

    public int setMainViewId() {
        return R.id.activity_alarm;
    }

    @Override
    protected void init() {
        super.init();

        setContentView(R.layout.activity_alarm);

        HomeTitle title = (HomeTitle) findViewById(R.id.Titlebar);
        title.setTitle(R.string.default_string_alarm);
        listView = (AlarmListView) findViewById(R.id.alarm_list);
        listView.setActivity(this);
        listView.requestReload();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
            case POST_MAIN_ACTIVITY:
                boolean hasDeleted = data.getBooleanExtra(PostMainActivity.IS_DELETED, false);
                
                if (hasDeleted) {
                    int deletedPostId = data.getIntExtra(PostMainActivity.DELETED_POST_ID, -1);
                    if (deletedPostId > 0) {
                        listView.deletedPost(deletedPostId);
                    }
                }
                break;
            }
        }
    }
}