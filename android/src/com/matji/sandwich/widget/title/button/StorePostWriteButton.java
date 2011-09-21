package com.matji.sandwich.widget.title.button;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.matji.sandwich.R;
import com.matji.sandwich.LoginActivity;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.dialog.WritePostStoreMatjiDialog;

/**
 * 맛집 발견/이야기 쓰기 버튼
 * 
 * @author excgate
 *
 */
public class StorePostWriteButton extends TitleImageButton {
    Dialog writeDialog;
    Session session;
    
    public StorePostWriteButton(Context context) {
	super(context);
    }

    /**
     * @see com.matji.sandwich.widget.title.button.TitleImageButton#init()
     */
    public void init() {
	super.init();
	setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_navi_memowrite));
	writeDialog = new WritePostStoreMatjiDialog(getContext());
	setFocusable(false);
	session = Session.getInstance(context);
    }
	
    /**
     * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
     */
    public void onTitleItemClicked() {
	if (session.isLogin()) {
	    writeDialog.show();
	} else {
	    Intent intent = new Intent(context, LoginActivity.class);
	    context.startActivity(intent);
	}
	// Log.d("=====", "MapWriteButtonClicked");
    }
}
