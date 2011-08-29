package com.matji.sandwich.widget.title;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.title.button.HomeButton;
import com.matji.sandwich.widget.title.button.LikeStoreListButton;
import com.matji.sandwich.widget.title.button.MessageButton;
import com.matji.sandwich.widget.title.button.TitleImageButton;

/**
 * UserMainActivity에서 사용하는 Titlebar.
 * Titlebar에서 {@link User} 객체를 사용해야 하므로 생성자에서 User 정보를 무조건 받도록 한다. 
 * 
 * @author mozziluv
 *
 */
public class UserTitle extends TitleContainerTypeA {
    public UserTitle(Context context) {
        super(context);
    }

    public UserTitle(Context context, User user) {
        super(context);
        setUser(user);
    }

	public UserTitle(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	public void setUser(User user) {
        setTitle(user.getNick());
        ((LikeStoreListButton) rightButton1).setUser(user);
	}

    @Override
    protected TitleImageButton getLeftButton1() {
        // TODO Auto-generated method stub
        return new HomeButton(getContext());
    }

    @Override
    protected TitleImageButton getRightButton1() {
        // TODO Auto-generated method stub
        return new LikeStoreListButton(getContext());
    }

    @Override
    protected TitleImageButton getRightButton2() {
        // TODO Auto-generated method stub
        return new MessageButton(getContext());
    }
}