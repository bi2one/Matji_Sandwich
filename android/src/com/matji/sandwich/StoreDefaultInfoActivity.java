package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.cell.StoreCell;
import com.matji.sandwich.widget.title.StoreTitle;

public class StoreDefaultInfoActivity extends BaseActivity implements Refreshable {
	private Store store;
	private StoreTitle title;
	private StoreCell storeCell;
	
	private TextView defaultInfo;
	public static final String STORE  = "StoreDetailInfoActivity.store";
	private TextView fullName;
	private TextView cover;
	private TextView tel;
	private TextView address;
	private TextView website;

    public int setMainViewId() {
	return R.id.activity_store_default_info;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void init() {
		super.init();
		setContentView(R.layout.activity_store_default_info);
		store = (Store) getIntent().getParcelableExtra(STORE);
		title = (StoreTitle) findViewById(R.id.Titlebar);
		storeCell = (StoreCell) findViewById(R.id.StoreCell);
		
		fullName = (TextView) findViewById(R.id.store_info_fullname);
		cover = (TextView) findViewById(R.id.store_info_cover);
		tel = (TextView) findViewById(R.id.store_info_tel);
		address = (TextView) findViewById(R.id.store_info_address);
		website = (TextView) findViewById(R.id.store_info_website);

		storeCell.setStore(store);
		storeCell.setMainView(getMainView());
		
		title.setIdentifiable(this);
		title.setSpinnerContainer(getMainView());
		title.setRefreshable(this);
		title.setStore(store);
		
		fullName.setText(store.getName());
		tel.setText(store.getTel());
		address.setText(store.getAddress());
		
		if(store.getCover() != null)
			cover.setText(store.getCover());
		else	
			cover.setText("-");
		
		if(store.getWebsite() != null)
			website.setText(store.getWebsite());
		else
			website.setText("-");
	}

	@Override
	protected void onNotFlowResume() {
	    // TODO Auto-generated method stub
	    super.onNotFlowResume();
	    refresh();
	}
	
    @Override
    public void refresh() {
        storeCell.refresh();
        title.syncSwitch();        
    }

    @Override
    public void refresh(MatjiData data) {
        if (data instanceof Store) {
            this.store = (Store) data;
            storeCell.setStore(store);
        }        
    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {
        // TODO Auto-generated method stub
        
    }
}