package com.matji.sandwich.widget.title.button;

import android.content.Context;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.base.Identifiable;
import com.matji.sandwich.widget.title.Switchable;

/**
 * Follow 버튼
 * 
 * @author mozziluv
 *
 */
public class FollowButton extends TitleImageButton implements Switchable {

    private static final int ALPHA_FOLLOW_STATE = 0x46;     // opacity 70%
    private static final int ALPHA_UNFOLLOW_STATE = 0xff;   // opacity 100%
    
    private Followable followable;
    private Identifiable identifiable;
    
    public FollowButton(Context context) {
        super(context);
    }

    /**
     * @see com.matji.sandwich.widget.title.button.TitleImageButton#init()
     */
    @Override
    public void init() {
        // TODO Auto-generated method stub
        super.init();
        setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_navi_follow));
    }
    
    public void setIdentifiable(Identifiable identifiable) {
        this.identifiable = identifiable;
    }

    public void setFollowable(Followable followable) {
        this.followable = followable;
    }

    /**
     * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
     */
    @Override
    public void onTitleItemClicked() {
        // TODO Auto-generated method stub
        Log.d("Matji", "FollowButtonClicked");
        if (identifiable.loginRequired()) followable.following();
    }
    
    /**
     * Unfollow 요청 버튼
     */
    @Override
    public void on() {
        setAlpha(ALPHA_UNFOLLOW_STATE);
    }
    
    /**
     * Follow 요청 버튼
     */
    @Override
    public void off() {
        setAlpha(ALPHA_FOLLOW_STATE);
    }
    
    public void refresh() {
        if (followable.isFollow()) {
            on();
        } else {
            off();
        }
    }
    
    public interface Followable {
        public void following();
        public boolean isFollow();
    }
}
