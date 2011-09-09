package com.matji.sandwich.widget.title.button;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

/**
 * 맛집 발견/이야기 쓰기 버튼
 * 
 * @author excgate
 *
 */
public class ExportButton extends TitleImageButton {
    Dialog writeDialog;
    
    public ExportButton(Context context) {
	super(context);
    }

    /**
     * @see com.matji.sandwich.widget.title.button.TitleImageButton#init()
     */
    public void init() {
	super.init();
//	setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_navi_));
    }
	
    /**
     * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
     */
    public void onTitleItemClicked() {
	 Log.d("=====", "ExportButtonClicked");
    }
}
