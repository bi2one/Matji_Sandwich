package com.matji.sandwich;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.widget.RankingListView;

/**
 * 전체 Ranking 리스트를 보여주는 액티비티.
 * 
 * @author bizone
 */
public class RankingAllListActivity extends BaseActivity implements ActivityStartable {
    private RankingListView listView;

    public int setMainViewId() {
        return R.id.activity_ranking_all_list;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_all_list);

        listView = (RankingListView) findViewById(R.id.ranking_all_list_view);
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_reload:
            listView.refresh();
            return true;
        }
        return false;
    }
}