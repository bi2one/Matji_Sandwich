package com.matji.sandwich.widget.title.button;

import android.content.Context;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.base.Identifiable;
import com.matji.sandwich.widget.title.Switchable;

/**
 * 좋아요 버튼
 * 
 * @author mozziluv
 *
 */
public class LikeButton extends TitleImageButton implements Switchable {
    
    private Likeable likeable;
    private Identifiable identifiable;
    
	public LikeButton(Context context) {
		super(context);
	}

    public void setLikeable(Likeable likeable) {
        this.likeable = likeable;
    }
    	
	public void setIdentifiable(Identifiable identifiable) {
	    this.identifiable = identifiable;
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.button.TitleImageButton#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_navi_like));
	}
	
	/**
	 * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
	 */
	@Override
	public void onTitleItemClicked() {
		// TODO Auto-generated method stub
		Log.d("Matji", "LikeButtonClicked");
		if (identifiable.loginRequired()) likeable.like();
	}

    @Override
    public void on() {
        setImageResource(R.drawable.icon_navi_like_touch);
    }

    @Override
    public void off() {
        setImageResource(R.drawable.icon_navi_like);
    }
    
    
    public void refresh() {
        if (likeable.isLike()) {
            on();
        } else {
            off();
        }
    }
    
    public interface Likeable {
        public void like();
        public boolean isLike();
    }
}
