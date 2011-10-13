package com.matji.sandwich.listener;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;

import com.matji.sandwich.LikeUserListActivity;
import com.matji.sandwich.data.MatjiData;

public class LikeUserListListener implements OnClickListener {

    private Context context;
    private MatjiData data;

    public LikeUserListListener(Context context, MatjiData data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent(context, LikeUserListActivity.class);
        intent.putExtra(LikeUserListActivity.DATA , (Parcelable) data);
        context.startActivity(intent);
    }
}