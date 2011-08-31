package com.matji.sandwich.widget.title.button;

import android.content.Context;
import android.util.Log;

import com.matji.sandwich.LikeStoreListActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.widget.title.UserTitle.Followable;

/**
 * {@link LikeStoreListActivity}로 이동하게 하는 버튼
 * 
 * @author mozziluv
 *
 */
public class FollowButton extends TitleImageButton {

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
}
