package com.matji.sandwich.adapter.listener;

import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.Intent;

import com.matji.sandwich.StoreMainActivity;
import com.matji.sandwich.data.Store;

public class StoreClickListener implements OnClickListener {
    private Context context;
    private Store store;
    private Intent targetIntent;

    public StoreClickListener(Context context) {
        this.context = context;
        targetIntent = new Intent(context, StoreMainActivity.class);
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void onClick(View v) {
        targetIntent.putExtra(StoreMainActivity.STORE, (Parcelable) store);
        context.startActivity(targetIntent);
    }
}