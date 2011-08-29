package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.RankingListView;

/**
 * 전체 Ranking 리스트를 보여주는 액티비티.
 * 
 * @author bizone
 */
public class RankingAllListActivity extends BaseActivity {
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

    protected void onNotFlowResume() {
	listView.requestReload();
    }
}