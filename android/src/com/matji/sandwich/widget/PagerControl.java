package com.matji.sandwich.widget;

import java.util.*;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matji.sandwich.R;

public class PagerControl extends RelativeLayout {
    private int numPages,  position;
    private int fadeDelay, fadeDuration;
    //public static final int KEY_FOR_VIEW_TITLE = 1;
    //private String[] viewNameRefs;
    private ArrayList<View> mContentViews;
    
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
	//setCurrentPage(defaultPage);
    }

    public void setNumPages(int pages) {
	numPages = pages;
    }

//    public void setViewNames(String[] nameRefs) {
//	viewNameRefs = nameRefs;
//    }

    public void setCurrentPage(int currentPage) {
	if (currentPage - 1 >= 0) {
		String title = (String)mContentViews.get(currentPage-1).getTag(R.string.title); 
	    prevText.setText(title);
	} else {
	    prevText.setText("");
	}
	
	progressText.setText(currentPage + 1 + "");

	if (currentPage + 1 >= numPages) {
	    nextText.setText("");
	} else {
		String title = (String)mContentViews.get(currentPage+1).getTag(R.string.title);
	    nextText.setText(title);
	}
    }

	public void setContentViews(ArrayList<View> contentViews) {
		this.mContentViews = contentViews;
	}

	public ArrayList<View> getContentViews() {
		return mContentViews;
	}


	
	
}
