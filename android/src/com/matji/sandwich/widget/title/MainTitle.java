package com.matji.sandwich.widget.title;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.title.button.SearchButton;
import com.matji.sandwich.widget.title.button.SettingButton;
import com.matji.sandwich.widget.title.button.PostWriteButton;
import com.matji.sandwich.widget.title.button.TitleImageButton;

public class MainTitle extends TitleContainerTypeRRR implements MainTabTitle {
	public MainTitle(Context context) {
		super(context);
	}
	
	public MainTitle(Context context, AttributeSet attr) {
		super(context, attr);
	}

    @Override
    protected TitleImageButton getRightButton1() {
        // TODO Auto-generated method stub
        return new SearchButton(getContext());
    }

    @Override
    protected TitleImageButton getRightButton2() {
        // TODO Auto-generated method stub
        return new PostWriteButton(getContext());
    }

    @Override
    protected TitleImageButton getRightButton3() {
        // TODO Auto-generated method stub
        return new SettingButton(getContext());
    }
    
    @Override
    public void notificationValidate() {
        if (Session.getInstance(getContext()).getPrivateUtil().getNewNoticeCount() > 0) {
            showNewIcon(rightButton3);
        } else {
            dismissNewIcon(rightButton3);
        }
    }
}