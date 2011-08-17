package com.matji.sandwich.widget.title;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.title.button.HomeButton;
import com.matji.sandwich.widget.title.button.LikeButton;
import com.matji.sandwich.widget.title.button.LikeStoreListButton;

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

	/**
	 * @see com.matji.sandwich.widget.title.TypedTitleContainer#setButtons()
	 */
	@Override
	protected void setButtons() {
		leftButton1 = new HomeButton(getContext());
		rightButton1 = new LikeStoreListButton(getContext());
		rightButton2 = new LikeButton(getContext());
	}
	
	public void setUser(User user) {
        setTitle(user.getNick());
        ((LikeStoreListButton) rightButton1).setUser(user);
	}
}