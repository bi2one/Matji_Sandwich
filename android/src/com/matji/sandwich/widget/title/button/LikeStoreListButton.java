package com.matji.sandwich.widget.title.button;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import com.matji.sandwich.LikeStoreListActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.data.User;

/**
 * {@link LikeStoreListActivity}로 이동하게 하는 버튼
 * 
 * @author mozziluv
 *
 */
public class LikeStoreListButton extends TitleImageButton {
    private User user;

    public LikeStoreListButton(Context context) {
        super(context);
    }

    /**
     * @see com.matji.sandwich.widget.title.button.TitleImageButton#init()
     */
    @Override
    public void init() {
        // TODO Auto-generated method stub
        super.init();
        setImageDrawable(context.getResources().getDrawable(R.drawable.icon_navi_like_list));
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
     */
    @Override
    public void onTitleItemClicked() {
        // TODO Auto-generated method stub
        Log.d("Matji", "LikeStoreListButtonClicked");
    }
}
