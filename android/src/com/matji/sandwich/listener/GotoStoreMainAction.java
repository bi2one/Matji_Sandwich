package com.matji.sandwich.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;

import com.matji.sandwich.StoreMainActivity;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Store;

/**
 * 클릭 시 {@link StoreMainActivity}로 이동하도록 하는 클릭 리스너.
 * 
 * @author mozziluv
 *
 */
public class GotoStoreMainAction implements OnClickListener {

	private Context context;
	private Store store;

	/**
	 * 기본 생성자
	 * 
	 * @param context
	 * @param store {@link StoreMainActivity}에서 사용 할 {@link Store} 정보
	 */
	public GotoStoreMainAction(Context context, Store store) {
		this.store = store;
		this.context = context;
	}

	/**
	 * 해당 리스너의 {@link Store} 정보를 변경한다.
	 * 
	 * @param store 변경 할 {@link Store} 정보
	 */
	public void setStore(Store store) {
		this.store = store;
	}

	/**
	 * 현재 저장된 {@link Store}의 {@link StoreMainActivity}로 이동한다.
	 */
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(context, StoreMainActivity.class);
		intent.putExtra(StoreMainActivity.STORE, (Parcelable) store);
		((Activity) context).startActivityForResult(intent, BaseActivity.STORE_MAIN_ACTIVITY);
	}
}