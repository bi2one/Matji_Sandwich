package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.PhoneCallUtil;
import com.matji.sandwich.widget.cell.StoreCell;
import com.matji.sandwich.widget.title.StoreTitle;

public class StoreDefaultInfoActivity extends BaseActivity implements Refreshable {
    private StoreTitle title;
    private StoreCell storeCell;

    private TextView tvName;
    private TextView tvCover;
    private TextView tvTel;
    private TextView tvAddress;
    private TextView tvWebsite;
    private PhoneCallUtil phoneCallUtil;

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
        title = (StoreTitle) findViewById(R.id.Titlebar);
        storeCell = (StoreCell) findViewById(R.id.StoreCell);
        storeCell.setClickable(false);

        tvName = (TextView) findViewById(R.id.store_default_info_name);
        tvCover = (TextView) findViewById(R.id.store_default_info_cover);
        tvTel = (TextView) findViewById(R.id.store_default_info_tel);
        tvAddress = (TextView) findViewById(R.id.store_default_info_address);
        tvWebsite = (TextView) findViewById(R.id.store_default_info_website);

        title.setIdentifiable(this);
        title.setStore(StoreDetailInfoTabActivity.store);
        title.setLikeable(storeCell);

        storeCell.setStore(StoreDetailInfoTabActivity.store);
        storeCell.setIdentifiable(this);
        storeCell.addRefreshable(this);
        storeCell.addRefreshable(title);

        phoneCallUtil = new PhoneCallUtil(this);
        
    }

    @Override
    protected void onNotFlowResume() {
        // TODO Auto-generated method stub
        super.onNotFlowResume();
        storeCell.refresh();
    }

    @Override
    public void refresh() {
        Store store = StoreDetailInfoTabActivity.store;
        String cover = store.getCover();
        String tel = store.getTel();
        String website = store.getWebsite();

        tvName.setText(StoreDetailInfoTabActivity.store.getName());
        tvCover.setText(
                (cover == null || cover.equals("")) ?
                        MatjiConstants.string(R.string.default_string_not_exist_cover)
                        :StoreDetailInfoTabActivity.store.getCover());

        tvTel.setText(
                (tel == null || tel.equals("")) ?
                        MatjiConstants.string(R.string.default_string_not_exist_tel)
                        : StoreDetailInfoTabActivity.store.getTel());
        tvAddress.setText(StoreDetailInfoTabActivity.store.getAddress());

        tvWebsite.setText(
                (website == null || website.equals("")) ?
                        MatjiConstants.string(R.string.default_string_not_exist_website)
                        : StoreDetailInfoTabActivity.store.getWebsite());
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

    public void onAddressClicked(View v) {
        Intent intent = new Intent(this, StoreLocationMapActivity.class);
        intent.putExtra(StoreLocationMapActivity.INTENT_STORE, (Parcelable)StoreDetailInfoTabActivity.store);
        startActivity(intent);
    }

    public void onTelClicked(View v) {
        phoneCallUtil.call(StoreDetailInfoTabActivity.store.getTelNotDashed());
    }

    public void onWebsiteClicked(View v) {
        String website = StoreDetailInfoTabActivity.store.getWebsite();
        if (!website.equals("")) {
            Uri uri = Uri.parse(website);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    public void onReportClicked(View v) {
    	Session session = Session.getInstance(this);
        if (!session.isLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
        	Intent intent = new Intent(this, StoreModifyActivity.class);
        	intent.putExtra(StoreModifyActivity.STORE, (Parcelable) StoreDetailInfoTabActivity.store);
        	startActivity(intent);
    	}
    }
    
}