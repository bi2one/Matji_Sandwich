package com.matji.sandwich;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.SentMessageListView;

public class SentMessageListActivity extends BaseActivity {

    private SentMessageListView listView;

    public int setMainViewId() {
        return R.id.activity_sent_message_list;
    }

    @Override
    protected void onNotFlowResume() {
        // TODO Auto-generated method stub
        super.onFlowResume();
        listView.requestReload();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_message_list);
        
        listView = (SentMessageListView) findViewById(R.id.sent_message_list_view);
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
}