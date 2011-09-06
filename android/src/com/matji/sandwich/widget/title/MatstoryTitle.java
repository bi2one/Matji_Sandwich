package com.matji.sandwich.widget.title;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.SearchActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.title.button.SearchButton;
import com.matji.sandwich.widget.title.button.SettingButton;
import com.matji.sandwich.widget.title.button.StorePostWriteButton;
import com.matji.sandwich.widget.title.button.TitleImageButton;

/**
 * UserMainActivity에서 사용하는 Titlebar.
 * Titlebar에서 {@link User} 객체를 사용해야 하므로 생성자에서 User 정보를 무조건 받도록 한다. 
 * 
 * @author mozziluv
 *
 */
public class MatstoryTitle extends TitleContainerTypeRRR implements MainTabTitle {
    public MatstoryTitle(Context context) {
        super(context);
    }

	public MatstoryTitle(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
    @Override
    protected TitleImageButton getRightButton1() {
        return new SearchButton(getContext(), SearchActivity.POST);
    }

    @Override
    protected TitleImageButton getRightButton2() {
        // TODO Auto-generated method stub
        return new StorePostWriteButton(getContext());
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