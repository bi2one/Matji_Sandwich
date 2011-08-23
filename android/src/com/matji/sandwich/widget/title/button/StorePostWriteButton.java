package com.matji.sandwich.widget.title.button;

import com.matji.sandwich.R;

import android.app.Dialog;
import android.content.Context;

import com.matji.sandwich.widget.dialog.WritePostStoreMatjiDialog;

/**
 * 맛집 발견/이야기 쓰기 버튼
 * 
 * @author excgate
 *
 */
public class StorePostWriteButton extends TitleButton {
    Dialog writeDialog;
    
    public StorePostWriteButton(Context context) {
	super(context);
    }

    /**
     * @see com.matji.sandwich.widget.title.button.TitleButton#init()
     */
    public void init() {
	super.init();
	setImageDrawable(getContext().getResources().getDrawable(R.drawable.btn_write));
	writeDialog = new WritePostStoreMatjiDialog(getContext());
	setFocusable(false);
    }
	
    /**
     * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
     */
    public void onTitleItemClicked() {
	writeDialog.show();
	// Log.d("=====", "MapWriteButtonClicked");
    }
}