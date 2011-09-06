package com.matji.sandwich.widget.title;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.title.button.AlarmButton;
import com.matji.sandwich.widget.title.button.SettingButton;
import com.matji.sandwich.widget.title.button.TitleImageButton;

public class PrivateTitle extends TitleContainerTypeRR implements MainTabTitle {
    public PrivateTitle(Context context) {
        super(context);
    }

	public PrivateTitle(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
    @Override
    protected TitleImageButton getRightButton1() {
        // TODO Auto-generated method stub
        return new AlarmButton(getContext());
    }

    @Override
    protected TitleImageButton getRightButton2() {
        // TODO Auto-generated method stub
        return new SettingButton(getContext());
    }
 
    @Override
    public void notificationValidate() {
        if (Session.getInstance(getContext()).getPrivateUtil().getNewAlarmCount() > 0) {
            showNewIcon(rightButton1);
        } else {
            dismissNewIcon(rightButton1);
        }

        if (Session.getInstance(getContext()).getPrivateUtil().getNewNoticeCount() > 0) {
            showNewIcon(rightButton2);
        } else {
            dismissNewIcon(rightButton2);
        }
    }
}