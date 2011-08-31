package com.matji.sandwich.widget.title;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.title.button.FollowButton;
import com.matji.sandwich.widget.title.button.FollowButton.Followable;
import com.matji.sandwich.widget.title.button.HomeButton;
import com.matji.sandwich.widget.title.button.TitleImageButton;
import com.matji.sandwich.widget.title.button.WriteMessageButton;

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
	}
	
	public void setFollowable(Followable followable) {
	    ((FollowButton) rightButton1).setFollowable(followable);
	}
	
    @Override
    protected TitleImageButton getLeftButton1() {
        // TODO Auto-generated method stub
        return new HomeButton(getContext());
    }

    @Override
    protected TitleImageButton getRightButton1() {
        // TODO Auto-generated method stub
        return new FollowButton(getContext());
    }

    @Override
    protected TitleImageButton getRightButton2() {
        // TODO Auto-generated method stub
        return new WriteMessageButton(getContext());
    }
}