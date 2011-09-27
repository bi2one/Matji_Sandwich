package com.matji.sandwich.widget.title;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.Refreshable;
import com.matji.sandwich.base.Identifiable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.title.button.FollowButton;
import com.matji.sandwich.widget.title.button.FollowButton.Followable;
import com.matji.sandwich.widget.title.button.HomeButton;
import com.matji.sandwich.widget.title.button.TitleImageButton;
import com.matji.sandwich.widget.title.button.WriteMessageButton;

/**
 * UserMainActivity에서 사용하는 Titlebar.
 * 
 * @author mozziluv
 *
 */
public class UserTitle extends TitleContainerTypeLRR implements Refreshable {
	
	public static User title_user;
	
	public UserTitle(Context context) {
        super(context);
    }

    public UserTitle(Context context, User user, Identifiable identifiable) {
        super(context);
        setIdentifiable(identifiable);
        setUser(user);
    }

	public UserTitle(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	public void setUser(User user) {
        title_user = user;
		setTitle(title_user.getNick());
        ((WriteMessageButton) rightButton2).setUser(title_user);

        Session session = Session.getInstance(getContext());
        if (session.isLogin()) 
            if (session.getCurrentUser().getId() == title_user.getId()) {
                removeRightButton(rightButton1);
        }
	}
	
	public void setIdentifiable(Identifiable identifiable) {
	    ((FollowButton) rightButton1).setIdentifiable(identifiable);
	    ((WriteMessageButton) rightButton2).setIdentifiable(identifiable);
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

    @Override
    public void refresh() {
        ((FollowButton) rightButton1).refresh();
    }

    @Override
    public void refresh(MatjiData data) {
        if (data instanceof User) {
            setUser((User) data);
            refresh();
        }
    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {}
}