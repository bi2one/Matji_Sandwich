package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.BaseViewContainer;
import com.matji.sandwich.widget.StoreNoteListView;

public class StoreNoteListActivity extends BaseActivity {
	private StoreNoteListView listView;
	private BaseViewContainer header;

	public static final String STORE = "store";

    public int setMainViewId() {
	return R.id.layout_main;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_note);

		header = new BaseViewContainer(this, getString(R.string.default_string_write_note));
		listView = (StoreNoteListView) findViewById(R.id.store_note_list);
		listView.setStoreId(((Store) getIntent().getParcelableExtra(STORE)).getId());
		listView.setActivity(this);
		listView.getHeaderViewContainer().removeView(header.getRootView());
		listView.addHeaderView(header);
		listView.requestReload();		
	}
}