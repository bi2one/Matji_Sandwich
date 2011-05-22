package com.matji.sandwich.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.Gravity;

import com.matji.sandwich.R;

/**
 * User: jeanguy@gmail.com
 * Date: Aug 11, 2010
 */
public class PagerControl extends RelativeLayout {
    private int numPages, currentPage, position;
    private int fadeDelay, fadeDuration;
    private int[] viewNameRefs;

    private TextView prevText;
    private TextView nextText;
    private TextView progressText;
    private Activity mActivity;

    public PagerControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void start(Activity activity) {
	mActivity = activity;
	prevText = (TextView)activity.findViewById(R.id.PagerControlPrevText);
	nextText = (TextView)activity.findViewById(R.id.PagerControlNextText);
	progressText = (TextView)activity.findViewById(R.id.PagerControlProgress);
	// prevText.setText("test!!!");
	// nextText.setText("tesssss");
	// progressText.setText("1");
	setCurrentPage(0);
    }

    public void setNumPages(int pages) {
	numPages = pages;
    }

    public void setViewNames(int[] nameRefs) {
	viewNameRefs = nameRefs;
    }

    public void setCurrentPage(int currentPage) {
	if (currentPage - 1 >= 0) {
	    prevText.setText(viewNameRefs[currentPage - 1]);
	} else {
	    prevText.setText("");
	}
	
	progressText.setText(currentPage + 1 + "");

	if (currentPage + 1 >= numPages) {
	    nextText.setText("");
	} else {
	    nextText.setText(viewNameRefs[currentPage + 1]);
	}
    }
}
