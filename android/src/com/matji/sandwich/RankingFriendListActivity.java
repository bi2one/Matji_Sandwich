package com.matji.sandwich;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.widget.RankingListView;

/**
 * 친구 Ranking 리스트를 보여주는 액티비티.
 * 
 * @author bizone
 */
public class RankingFriendListActivity extends BaseActivity implements ActivityStartable {
    private RankingListView listView;

    public int setMainViewId() {
	return R.id.activity_ranking_friend_list;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_friend_list);

        listView = (RankingListView) findViewById(R.id.ranking_friend_list_view);
        listView.setActivity(this);
        listView.requestReload();
    }
    
    @Override
    public void activityResultDelivered(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
        case USER_MAIN_ACTIVITY:        
            if (resultCode == Activity.RESULT_OK) {
                setIsFlow(true);
            }
            break;
        }
    }

    @Override
    public void setIsFlow(boolean isFlow) {
        if (getParent() instanceof BaseTabActivity) {
            ((BaseTabActivity) getParent()).setIsFlow(true);
        }
        super.setIsFlow(isFlow);
    }
    
    protected void onNotFlowResume() {
        listView.refresh();
    }
}