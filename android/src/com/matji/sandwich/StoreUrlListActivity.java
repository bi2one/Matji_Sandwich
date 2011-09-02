package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.StoreUrlListView;
import com.matji.sandwich.widget.cell.StoreCell;

public class StoreUrlListActivity extends BaseActivity {
	
    private StoreUrlListView listView;
	private Store store;
	private StoreCell storeCell;
    
	public static final String STORE = "store";

    public int setMainViewId() {
	return R.id.activity_store_url;
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_url);

		store = (Store) getIntent().getParcelableExtra(STORE);
		storeCell = new StoreCell(this, store);
		
		listView = (StoreUrlListView) findViewById(R.id.store_url_list);
		listView.setUserId(store.getId());
		listView.addHeaderView(storeCell);
		listView.setActivity(this);
		listView.requestReload();
	}
}