package com.matji.sandwich.widget.title;

import android.content.Context;
import android.util.AttributeSet;

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
}