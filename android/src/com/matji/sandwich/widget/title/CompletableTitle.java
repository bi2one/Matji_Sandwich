package com.matji.sandwich.widget.title;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.title.button.CompleteButton;
import com.matji.sandwich.widget.title.button.HomeButton;
import com.matji.sandwich.widget.title.button.TitleButton;
import com.matji.sandwich.widget.title.button.TitleImageButton;

public class CompletableTitle extends TitleContainerTypeLRt {

	public CompletableTitle(Context context) {
		super(context);
	}

	public CompletableTitle(Context context, AttributeSet attr) {
		super(context, attr);
	}

    /**
     * {@link Completable} 객체를 각 버튼에 지정한다.
     * @param completable  버튼에 지정할 {@link Completable} 객체
     */
    public void setCompletable(Completable completable) {
        ((CompleteButton) rightButton1).setCompletable(completable);
    }

    /**
     * 버튼의 문자열을 변경한다.
     * 
     * @param resid 변경할 문자열의 리소스 아이디
     */
    public void setButtonLabel(int resid) {
        ((CompleteButton) rightButton1).setLabel(resid);
    }
    
    
    /**
     * 버튼의 문자열을 변경한다.
     * 
     * @param text 변경할 문자열
     */
    public void setButtonLabel(String text) {
        ((CompleteButton) rightButton1).setLabel(text);        
    }
    
    @Override
    protected TitleImageButton getLeftButton1() {
        return new HomeButton(getContext());
    }
        
    @Override
    protected TitleButton getRightButton1() {
        return new CompleteButton(getContext());
    }
        
    public interface Completable {
        public void complete();
    }
    
    public void lockCompletableButton() {
        rightButton1.setTextColor(MatjiConstants.color(R.color.title_container_title_lock));
        rightButton1.setClickable(false);
    }
    
    public void unlockCompletableButton() {
        rightButton1.setTextColor(MatjiConstants.color(R.color.title_container_title));
        rightButton1.setClickable(true);
    }
}