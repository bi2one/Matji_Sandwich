package com.matji.sandwich.widget.title;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.title.button.AlarmButton;
import com.matji.sandwich.widget.title.button.SettingButton;
import com.matji.sandwich.widget.title.button.TitleImageButton;

/**
 * UserMainActivity에서 사용하는 Titlebar.
 * Titlebar에서 {@link User} 객체를 사용해야 하므로 생성자에서 User 정보를 무조건 받도록 한다. 
 * 
 * @author mozziluv
 *
 */
public class PrivateTitle extends TitleContainerTypeRR {
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
    
    public void notificationValidate() {
        if (Session.getInstance(getContext()).getPrivateUtil().getNewAlarmCount() > 0) {
            showNewIcon(rightButton1);
        } else {
            dismissNewIcon(rightButton1);
        }
    }
}