package com.matji.sandwich.widget;

import android.widget.LinearLayout;
import android.widget.ImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.R;

public class BookmarkStarToggleView extends LinearLayout implements OnClickListener {
    private Context context;
    private boolean isUnchecked;
    private ImageView uncheckedView;
    private ImageView checkedView;

    public BookmarkStarToggleView(Context context, AttributeSet attrs) {
	super(context, attrs);
	this.context = context;
	LayoutInflater.from(context).inflate(R.layout.bookmark_star_toggle, this, true);
	setOnClickListener(this);
	isUnchecked = true;
	uncheckedView = (ImageView)findViewById(R.id.bookmark_star_toggle_unchecked);
	checkedView = (ImageView)findViewById(R.id.bookmark_star_toggle_checked);
    }

    public void init(boolean isBookmarked) {
	if (isBookmarked) {
	    showCheckedView();
	} else {
	    showUncheckedView();
	}
    }

    public void onClick(View v) {
	if (isUnchecked) {
	    showCheckedView();
	} else {
	    showUncheckedView();
	}

	isUnchecked = !isUnchecked;
	// Log.d("=====", "bookmark clicked!!");
    }

    private void showUncheckedView() {
	checkedView.setVisibility(View.GONE);
	uncheckedView.setVisibility(View.VISIBLE);
    }

    private void showCheckedView() {
	checkedView.setVisibility(View.VISIBLE);
	uncheckedView.setVisibility(View.GONE);
    }
}