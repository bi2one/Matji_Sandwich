package com.matji.sandwich;

import com.matji.sandwich.session.*;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.TabHost;

public class MainTabActivity extends TabActivity{
    private TabHost tabHost;
	
    public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main_tab);
	
	tabHost = getTabHost();

	tabHost.addTab(tabHost.newTabSpec("tab1")
		       .setIndicator(getString(R.string.default_string_map))
		       .setContent(new Intent(this, MainMapActivity.class)));
    
	tabHost.addTab(tabHost.newTabSpec("tab2")
		       .setIndicator(getString(R.string.default_string_store))
		       .setContent(new Intent(this, StoreSliderActivity.class)));

	tabHost.addTab(tabHost.newTabSpec("tab3")
		       .setIndicator("설정")
		       .setContent(new Intent(this, SettingActivity.class)));
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch(requestCode) {
    	case 1:
    		startActivity(new Intent(this, WritePostActivity.class));
    	}
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	Session session = Session.getInstance(this);
    	switch (item.getItemId()) {
    	case R.id.posting:
    		if(session.getToken() == null) {
        		startActivityForResult( new Intent(this, LoginActivity.class), 1);
    		} else {
        		startActivity(new Intent(this, WritePostActivity.class));
    		}
    		return true;
    	}
    	return false;
    }
}