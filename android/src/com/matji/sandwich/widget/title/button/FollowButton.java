package com.matji.sandwich.widget.title.button;

import android.content.Context;
import android.util.Log;

import com.matji.sandwich.LikeStoreListActivity;
import com.matji.sandwich.R;

/**
 * {@link LikeStoreListActivity}로 이동하게 하는 버튼
 * 
 * @author mozziluv
 *
 */
public class FollowButton extends TitleImageButton {

    public static final int FOLLOW = 30;
    public static final int UNFOLLOW = 100;
    
    private Followable followable;
    
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
        setImageDrawable(context.getResources().getDrawable(R.drawable.icon_navi_follow));
    }

    public void setFollowable(Followable followable) {
        this.followable = followable;
        toggle();
    }

    /**
     * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
     */
    @Override
    public void onTitleItemClicked() {
        // TODO Auto-generated method stub
        Log.d("Matji", "FollowButtonClicked");
        followable.following();
    }
    
    private void toggle() {
//        setAlpha(followable.toggle());
    }
        
    public interface Followable {
        public void following();
    }
}
