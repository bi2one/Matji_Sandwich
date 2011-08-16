package com.matji.sandwich.adapter;

import android.content.Context;
import android.view.View;

public class RankingAdapter extends SimpleUserAdapter {  

    public RankingAdapter(Context context) {
        super(context);
    }
    
    @Override
    protected int getPositionVisibility() {
        return View.VISIBLE;
    }
}