package com.matji.sandwich.widget.title.button;

import android.content.Context;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.title.CompletableTitle.Completable;

/**
 * 쓰기 버튼
 * 
 * @author mozziluv
 *
 */
public class CompleteButton extends TitleButton {

    private Completable completable;
    
	public CompleteButton(Context context) {
		super(context);
	}

	/**
	 * @see com.matji.sandwich.widget.title.button.TitleButton#init()
	 */
	@Override
	public void init() {
		super.init();
		setText(MatjiConstants.string(R.string.default_string_complete));
		enlarge();
	}
	
    /**
     * 파라미터로 전달받은 {@link Completable}을 저장한다.
     * @param completable 저장할 {@link Completable} 객체
     */
	public void setCompletable(Completable completable) {
	    this.completable = completable;
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
	 */
	@Override
	public void onTitleItemClicked() {
		Log.d("Matji", "CompleteClicked");
		if (completable != null) {
		    completable.complete();
		}
	}

    public void setLabel(int resid) {
        setText(resid);
    }
    
    public void setLabel(String text) {
        setText(text);
    }
}
