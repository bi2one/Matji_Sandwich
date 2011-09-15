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
	private StoreTitle title;
	private StoreCell storeCell;
	
	private TextView defaultInfo;
//	public static final String STORE  = "StoreDetailInfoActivity.store";
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
//		store = (Store) getIntent().getParcelableExtra(STORE);
		title = (StoreTitle) findViewById(R.id.Titlebar);
		storeCell = (StoreCell) findViewById(R.id.StoreCell);
		storeCell.setClickable(false);
		
		fullName = (TextView) findViewById(R.id.store_info_fullname);
		cover = (TextView) findViewById(R.id.store_info_cover);
		tel = (TextView) findViewById(R.id.store_info_tel);
		address = (TextView) findViewById(R.id.store_info_address);
		website = (TextView) findViewById(R.id.store_info_website);

		title.setIdentifiable(this);
		title.setStore(StoreDetailInfoTabActivity.store);
		title.setLikeable(storeCell);

        storeCell.setStore(StoreDetailInfoTabActivity.store);
		storeCell.setIdentifiable(this);
		storeCell.addRefreshable(this);
        storeCell.addRefreshable(title);
	}

	@Override
	protected void onNotFlowResume() {
	    // TODO Auto-generated method stub
	    super.onNotFlowResume();
	    storeCell.refresh();
	}

    @Override
    public void refresh() {
        fullName.setText(StoreDetailInfoTabActivity.store.getName());
        tel.setText(StoreDetailInfoTabActivity.store.getTel());
        address.setText(StoreDetailInfoTabActivity.store.getAddress());
        
        if(StoreDetailInfoTabActivity.store.getCover() != null)
            cover.setText(StoreDetailInfoTabActivity.store.getCover());
        else    
            cover.setText("-");
        
        if(StoreDetailInfoTabActivity.store.getWebsite() != null)
            website.setText(StoreDetailInfoTabActivity.store.getWebsite());
        else
            website.setText("-");
	}
	
    @Override
    public void refresh(MatjiData data) {
        if (data instanceof Store) {
            StoreDetailInfoTabActivity.store = (Store) data;
            refresh();
        }        
    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {}
}