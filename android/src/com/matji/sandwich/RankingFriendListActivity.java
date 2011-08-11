package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.RankingListView;

/**
 * 친구 Ranking 리스트를 보여주는 액티비티.
 * 
 * @author bizone
 */
public class RankingFriendListActivity extends BaseActivity {
    private RankingListView listView;

    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_ranking_friend_list);

	listView = (RankingListView) findViewById(R.id.ranking_friend_list_view);
	listView.setActivity(this);
	listView.requestReload();
    }

    protected void onNotFlowResume() {
	listView.dataRefresh();
    }
}