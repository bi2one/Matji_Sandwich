package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.RankingUserListView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class UserNearRankingActivity extends BaseActivity {
    public static final String IF_LAT_NE = "UserNearRankingActivity.lat_ne";
    public static final String IF_LNG_NE = "UserNearRankingActivity.lng_ne";
    public static final String IF_LAT_SW = "UserNearRankingActivity.lat_sw";
    public static final String IF_LNG_SW = "UserNearRankingActivity.lng_sw";
    private static final int BASIC_LAT_NE = 1000;
    private static final int BASIC_LNG_NE = 1000;
    private static final int BASIC_LAT_SW = 1000;
    private static final int BASIC_LNG_SW = 1000;
    private RankingUserListView listView;
    
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_user_near_ranking);

	Intent retIntent = getIntent();
	double lat_ne = (double)retIntent.getIntExtra(IF_LAT_NE, BASIC_LAT_NE) / 1E6;
	double lng_ne = (double)retIntent.getIntExtra(IF_LNG_NE, BASIC_LNG_NE) / 1E6;
	double lat_sw = (double)retIntent.getIntExtra(IF_LAT_SW, BASIC_LAT_SW) / 1E6;
	double lng_sw = (double)retIntent.getIntExtra(IF_LNG_SW, BASIC_LNG_SW) / 1E6;
	listView = (RankingUserListView) findViewById(R.id.near_ranking_list);

	listView.setLocation(lat_ne, lng_ne, lat_sw, lng_sw);
	listView.setActivity(this);
	listView.requestReload();
    }
	
    protected void onResume() {
	super.onResume();
	listView.dataRefresh();
    }
	
    protected String titleBarText() {
	return "UserNearRankingActivity";
    }

    protected boolean setTitleBarButton(Button button) {
	return false;
    }

    protected void onTitleBarItemClicked(View view) { }
}