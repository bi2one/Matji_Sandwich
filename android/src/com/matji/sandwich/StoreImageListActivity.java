package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.StoreImageListView;
import com.matji.sandwich.widget.cell.StoreCell;
import com.matji.sandwich.widget.title.StoreTitle;

public class StoreImageListActivity extends BaseActivity {

    public static final String STORE = "store";
    
    private Store store;
    private StoreTitle title;
	private StoreCell storeCell;
	
    public int setMainViewId() {
	return R.id.activity_store_image_list;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_image_list);

		store = (Store) getIntent().getParcelableExtra(STORE);        
        title = (StoreTitle) findViewById(R.id.Titlebar);
        title.setTitle(store.getName());
        storeCell = new StoreCell(this, store);
        storeCell.setStore(store);
        storeCell.setMainView(getMainView());
        
		StoreImageListView listView = (StoreImageListView) findViewById(R.id.store_image_list_view);
        listView.addHeaderView(storeCell);
		listView.setStore(store);
		listView.setActivity(this);
		listView.requestReload();
	}
}