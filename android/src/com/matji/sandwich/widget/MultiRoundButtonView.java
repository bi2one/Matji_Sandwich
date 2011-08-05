package com.matji.sandwich.widget;

import android.widget.LinearLayout;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.view.LayoutInflater;
// import android.view.View;
// import android.view.View.OnClickListener;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.R;
// import com.matji.sandwich.LoginActivity;
import com.matji.sandwich.Requestable;
// import com.matji.sandwich.session.Session;

import java.util.ArrayList;

public class MultiRoundButtonView extends LinearLayout {
    private Context context;
    private ArrayList<IntentTitlePair> buttons;
    private LayoutInflater inflater;

    public MultiRoundButtonView(Context context, AttributeSet attrs) {
	super(context, attrs);
	this.context = context;
	setOrientation(VERTICAL);
	buttons = new ArrayList<IntentTitlePair>();
	inflater = LayoutInflater.from(context);
    }

    public void add(Intent intent, String title) {
	buttons.add(new IntentTitlePair(intent, title));
    }

    public void add(Intent intent, int titleRef) {
	buttons.add(new IntentTitlePair(intent, titleRef));
    }

    public void updateView() {
	for (IntentTitlePair pair : buttons) {
	    addView(getMiddleView());
	    Log.d("=====", pair.getTitle());
	}
    }

    public ViewGroup getTopRoundedView() {
	ViewGroup result = new LinearLayout(context);
	inflater.inflate(R.layout.top_rounded_button, result, true);
	return result;
    }

    public ViewGroup getMiddleView() {
	ViewGroup result = new LinearLayout(context);
	inflater.inflate(R.layout.middle_rounded_button, result, true);
	return result;
    }

    public ViewGroup getAllRoundedView() {
	ViewGroup result = new LinearLayout(context);
	inflater.inflate(R.layout.all_rounded_button, result, true);
	return result;
    }

    public ViewGroup getBottomRoundedView() {
	ViewGroup result = new LinearLayout(context);
	inflater.inflate(R.layout.bottom_rounded_button, result, true);
	return result;
    }

    private class IntentTitlePair {
	Intent intent;
	String title;
	int titleRef;

	public IntentTitlePair(Intent intent, String title) {
	    setIntent(intent);
	    setTitle(title);
	}

	public IntentTitlePair(Intent intent, int titleRef) {
	    setIntent(intent);
	    setTitle(titleRef);
	}

	public void setIntent(Intent intent) {
	    this.intent = intent;
	}

	public void setTitle(int titleRef) {
	    this.titleRef = titleRef;
	}

	public void setTitle(String title) {
	    this.title = title;
	}

	public Intent getIntent() {
	    return intent;
	}

	public String getTitle() {
	    if (title == null)
		return context.getString(titleRef);
	    else
		return title;
	}
    }
}