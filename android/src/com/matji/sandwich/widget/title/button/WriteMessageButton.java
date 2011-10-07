package com.matji.sandwich.widget.title.button;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.WriteMessageActivity;
import com.matji.sandwich.base.Identifiable;
import com.matji.sandwich.data.User;

/**
 * 메세지 버튼
 * 
 * @author mozziluv
 *
 */
public class WriteMessageButton extends TitleImageButton {

    private User user;
    private Identifiable identifiable;

    public WriteMessageButton(Context context) {
        super(context);
    }

    /**
     * @see com.matji.sandwich.widget.title.button.TitleImageButton#init()
     */
    @Override
    public void init() {
        // TODO Auto-generated method stub
        super.init();
        setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_navi_memo));
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIdentifiable(Identifiable identifiable) {
        this.identifiable = identifiable;
    }

    /**
     * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
     */
    @Override
    public void onTitleItemClicked() {
        // TODO Auto-generated method stub
        Log.d("Matji", "WriteMessageButtonClicked");
        if (identifiable.loginRequired()) {
            Intent intent = new Intent(getContext(), WriteMessageActivity.class);
            intent.putExtra(WriteMessageActivity.USER, (Parcelable) user);
            getContext().startActivity(intent);
        }
    }
}
