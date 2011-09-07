package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.StoreUrlListView;
import com.matji.sandwich.widget.cell.StoreCell;
import com.matji.sandwich.widget.title.StoreTitle;

public class StoreUrlListActivity extends BaseActivity {
	
    private StoreTitle title;
	private Store store;
	private StoreCell storeCell;
    private StoreUrlListView listView;
    
	public static final String STORE = "store";

    public int setMainViewId() {
	return R.id.activity_store_url;
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_url);

		title = (StoreTitle) findViewById(R.id.Titlebar);
		store = (Store) getIntent().getParcelableExtra(STORE);
		storeCell = new StoreCell(this, store);

		title.setIdentifiable(this);
		title.setSpinnerContainer(getMainView());
		title.setStore(store);
		title.setRefreshable(storeCell);
		
		storeCell.setMainView(getMainView());
		
		listView = (StoreUrlListView) findViewById(R.id.store_url_list);
		listView.setUserId(store.getId());
		listView.addHeaderView(storeCell);
		listView.setActivity(this);
		listView.requestReload();
	}
}