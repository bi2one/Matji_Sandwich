package com.matji.sandwich.listener;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;

import com.matji.sandwich.LikeUserListActivity;
import com.matji.sandwich.data.Store;

public class LikeUserListListener implements OnClickListener {

    private Context context;
    private Store store;

    public LikeUserListListener(Context context, Store store) {
        this.context = context;
        this.store = store;
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent(context, LikeUserListActivity.class);
        intent.putExtra(LikeUserListActivity.DATA , (Parcelable) store);
        context.startActivity(intent);
    }
}