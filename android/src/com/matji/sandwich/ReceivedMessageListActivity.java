package com.matji.sandwich;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.ReceivedMessageListView;

public class ReceivedMessageListActivity extends BaseActivity implements ActivityStartable {
    private ReceivedMessageListView listView;
    
    public int setMainViewId() {
        return R.id.activity_received_message_list;
    }

    @Override
    protected void onNotFlowResume() {
        super.onFlowResume();
        listView.requestReload();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_message_list);
        listView = (ReceivedMessageListView) findViewById(R.id.received_message_list_view);
        listView.setActivity(this);
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

	@Override
	public void activityResultDelivered(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case STORE_MAIN_ACTIVITY: case USER_MAIN_ACTIVITY: case IMAGE_SLIDER_ACTIVITY:
            if (resultCode == Activity.RESULT_OK)
                setIsFlow(true);
            break;
        }		
	}
}