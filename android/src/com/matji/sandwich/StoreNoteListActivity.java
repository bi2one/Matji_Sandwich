package com.matji.sandwich;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.BaseViewContainer;
import com.matji.sandwich.widget.StoreNoteListView;

public class StoreNoteListActivity extends BaseActivity {
	private StoreNoteListView listView;
	private BaseViewContainer header;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_note);

		header = new BaseViewContainer(this, getString(R.string.default_string_write_note));
		listView = (StoreNoteListView) findViewById(R.id.store_note_list);
		listView.setStoreId(((Store) SharedMatjiData.getInstance().top()).getId());
		listView.setActivity(this);
		listView.getHeaderViewContainer().removeView(header.getRootView());
		listView.addHeaderView(header);
		listView.requestReload();		
	}

	@Override
	protected String titleBarText() {
		return "StoreNoteListActivity";
	}

	@Override
	protected boolean setTitleBarButton(Button button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		// TODO Auto-generated method stub
		
	}

	/*

	private void setInfo() {
		listView.getHeaderViewContainer().removeView(header.getRootView());
		listView.addHeaderView(header);
		listView.requestReload();		
	}

	public void onResume() {
		super.onResume();

		setInfo();
	}
	 */
}