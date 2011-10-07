package com.matji.sandwich.widget.title.button;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.matji.sandwich.AlarmActivity;
import com.matji.sandwich.R;

/**
 * TODO
 *
 * @author mozziluv
 *
 */
public class AlarmButton extends TitleImageButton {

    public AlarmButton(Context context) {
        super(context);
    }

    /**
     * @see com.matji.sandwich.widget.title.button.TitleImageButton#init()
     */
    @Override
    protected void init() {
        super.init();
        setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_navi_activity));
    }

    /**
     * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
     */
    @Override
    public void onTitleItemClicked() {
        Log.d("Matji", "AlarmButtonClicked");
        getContext().startActivity(new Intent(getContext(), AlarmActivity.class));
    }
}
