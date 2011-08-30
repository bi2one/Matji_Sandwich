package com.matji.sandwich.listener;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;

import com.matji.sandwich.LikeStoreListActivity;
import com.matji.sandwich.data.User;

/**
 * Like, Unlike를 수행하는 리스너.
 * 
 * @author mozziluv
 *
 */
public class LikeStoreListListener implements OnClickListener {

    private Context context;
    private User user;

    public LikeStoreListListener(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent(context, LikeStoreListActivity.class);
        intent.putExtra(LikeStoreListActivity.USER, (Parcelable) user);
        context.startActivity(intent);
    }

}