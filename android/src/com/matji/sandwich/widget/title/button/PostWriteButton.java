package com.matji.sandwich.widget.title.button;

import android.os.Parcelable;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.matji.sandwich.R;
import com.matji.sandwich.LoginActivity;
import com.matji.sandwich.WritePostActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.session.Session;

/**
 * 맛집 발견/이야기 쓰기 버튼
 * 
 * @author excgate
 *
 */
public class PostWriteButton extends TitleImageButton {
    Session session;
    private Store store;
    private String tags;
    
    public PostWriteButton(Context context) {
	super(context);
    }

    /**
     * @see com.matji.sandwich.widget.title.button.TitleImageButton#init()
     */
    public void init() {
	super.init();
	setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_navi_memowrite));
	setFocusable(false);
	session = Session.getInstance(context);
    }

    public void setData(Store store, String tags) {
	this.store = store;
	this.tags = tags;
    }
	
    /**
     * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
     */
    public void onTitleItemClicked() {
	if (session.isLogin()) {
	    Intent writePostIntent = new Intent(getContext(), WritePostActivity.class);
	    if (store != null)
		writePostIntent.putExtra(WritePostActivity.INTENT_STORE, (Parcelable)store);
	    if (tags != null)
		writePostIntent.putExtra(WritePostActivity.INTENT_STORE, tags);
	    context.startActivity(writePostIntent);
	} else {
	    Intent intent = new Intent(context, LoginActivity.class);
	    context.startActivity(intent);
	}
	// Log.d("=====", "MapWriteButtonClicked");
    }
}
