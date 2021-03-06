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
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.DialogAsyncTask;
import com.matji.sandwich.http.request.StoreCloseHttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.PhoneCallUtil;
import com.matji.sandwich.widget.cell.StoreCell;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;
import com.matji.sandwich.widget.dialog.SimpleConfirmDialog;
import com.matji.sandwich.widget.dialog.SimpleDialog;
import com.matji.sandwich.widget.dialog.SimpleListDialog;
import com.matji.sandwich.widget.title.StoreTitle;

public class StoreDefaultInfoActivity extends BaseActivity implements Refreshable, Requestable, 
SimpleListDialog.OnClickListener, SimpleAlertDialog.OnClickListener {
	private static final int TAG_STORE_MODIFY = 0;
	private static final int TAG_STORE_CLOSE = 1;
	private SimpleConfirmDialog reportDialog;
	private SimpleAlertDialog successDialog;
	private PhoneCallUtil phoneCallUtil;
	private StoreCell storeCell;
	private StoreTitle title;
	private TextView tvAddress;
	private TextView tvWebsite;
	private TextView tvTel;

	public int setMainViewId() {
		return R.id.activity_store_default_info;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		setContentView(R.layout.activity_store_default_info);
		title = (StoreTitle) findViewById(R.id.Titlebar);
		storeCell = (StoreCell) findViewById(R.id.StoreCell);
		storeCell.setClickable(false);

		tvTel = (TextView) findViewById(R.id.store_default_info_tel);
		tvAddress = (TextView) findViewById(R.id.store_default_info_address);
		tvWebsite = (TextView) findViewById(R.id.store_default_info_website);

		title.setIdentifiable(this);
		title.setStore(StoreDetailInfoTabActivity.store);
		title.setTitle(R.string.title_store_info);
		title.setLikeable(storeCell);

		storeCell.setStore(StoreDetailInfoTabActivity.store);
		storeCell.setIdentifiable(this);
		storeCell.addRefreshable(this);
		storeCell.addRefreshable(title);

		phoneCallUtil = new PhoneCallUtil(this);
		successDialog = new SimpleAlertDialog(this, R.string.store_close_success);
		reportDialog = new SimpleConfirmDialog(this, R.string.request_check);
		reportDialog.setOnClickListener(new SimpleConfirmDialog.OnClickListener() {
			@Override
			public void onConfirmClick(SimpleDialog dialog) {
				StoreCloseHttpRequest request = new StoreCloseHttpRequest(StoreDefaultInfoActivity.this);
				request.actionClose(StoreDetailInfoTabActivity.store.getId());
				DialogAsyncTask requestTask = new DialogAsyncTask(StoreDefaultInfoActivity.this, StoreDefaultInfoActivity.this, getMainView(), request, TAG_STORE_CLOSE);
				requestTask.execute();     
			}
			@Override
			public void onCancelClick(SimpleDialog dialog) {}
		});
	}

	@Override
	protected void onNotFlowResume() {
		super.onNotFlowResume();
		storeCell.refresh();
	}

	@Override
	public void refresh() {
		Store store = StoreDetailInfoTabActivity.store;
		//        String name = store.getName();
		String address = store.getAddress();
		String addAdress = store.getAddAddress();
		//        String cover = store.getCover();
		String tel = store.getTel();
		String website = store.getWebsite();

		if (tel == null || tel.equals("")) {
			tvTel.setText(MatjiConstants.string(R.string.not_exist_tel));
			tvTel.setTextColor(MatjiConstants.color(R.color.matji_light_gray));
		} else {
			tvTel.setText(tel);
			tvTel.setTextColor(MatjiConstants.color(R.color.matji_black));
		}

		tvAddress.setText(
				(addAdress == null || addAdress.equals("")) ?
						address
						: address + " " + addAdress);

		if (website == null || website.trim().equals("")) {
		} else {
			tvWebsite.setVisibility(1);
			tvWebsite.setText(website);
		}
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
		if (StoreDetailInfoTabActivity.store.getTelNotDashed().equals("")) {
			Intent intent = new Intent(this, StoreModifyActivity.class);
			intent.putExtra(StoreModifyActivity.STORE, (Parcelable) StoreDetailInfoTabActivity.store);
			startActivity(intent);
		} else {
			phoneCallUtil.call(StoreDetailInfoTabActivity.store.getTelNotDashed());
		}
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

	// Store info modify or report close
	public void onReportClicked(View v) {
		Session session = Session.getInstance(this);
		if (session.isLogin()) {
			String a[] = { MatjiConstants.string(R.string.default_string_store_modify_request), 
					MatjiConstants.string(R.string.default_string_store_close)};
			SimpleListDialog listDialog = new SimpleListDialog(this, null, a);
			listDialog.setOnClickListener(this);
			listDialog.show();
		} else {
			startActivity(new Intent(this, LoginActivity.class));
		}
	}

	public void onItemClick(SimpleDialog dialog, int position) {
		switch (position) {
		case TAG_STORE_MODIFY:
			Intent intent = new Intent(this, StoreModifyActivity.class);
			intent.putExtra(StoreModifyActivity.STORE, (Parcelable) StoreDetailInfoTabActivity.store);
			startActivity(intent);
			break;
		case TAG_STORE_CLOSE:
			reportDialog.show();
			break;
		}
	}

	public void onConfirmClick(SimpleDialog dialog) {
		if (dialog == successDialog) {
			setResult(RESULT_OK);
		}
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch (tag) {
		case TAG_STORE_CLOSE:
			successDialog.show();
			break;
		}
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(this);
	}
}