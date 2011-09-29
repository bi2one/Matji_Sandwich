package com.matji.sandwich.widget;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;

import java.util.ArrayList;

public class MultiRoundButtonView extends LinearLayout implements View.OnClickListener {
    private Context context;
    private ArrayList<IntentTitlePair> buttons;
    private LayoutInflater inflater;
    private OnItemClickListener listener;

    public MultiRoundButtonView(Context context, AttributeSet attrs) {
	super(context, attrs);
	this.context = context;
	setOrientation(VERTICAL);
	buttons = new ArrayList<IntentTitlePair>();
	inflater = LayoutInflater.from(context);
    }

    public void init(OnItemClickListener listener) {
	this.listener = listener;
    }

    public void add(Intent intent, String title) {
	buttons.add(new IntentTitlePair(intent, title));
    }

    public void add(Intent intent, int titleRef) {
	buttons.add(new IntentTitlePair(intent, titleRef));
    }

    public void clear() {
	buttons.clear();
	updateView();
    }

    public void updateView() {
	int buttonSize = buttons.size();
	if (buttonSize == 1) {
	    IntentTitlePair firstPair = buttons.get(0);
	    addButtonView(new AllRoundButtonView(context), firstPair.getTitle(), firstPair.getIntent());
	} else if (buttonSize > 1) {
	    IntentTitlePair firstPair = buttons.get(0);
	    IntentTitlePair lastPair = buttons.get(buttonSize - 1);
	    for (IntentTitlePair pair : buttons) {
		if (pair == firstPair) {
		    addButtonView(new TopRoundButtonView(context), pair.getTitle(), pair.getIntent());
		} else if (pair == lastPair) {
		    addButtonView(new BottomRoundButtonView(context), pair.getTitle(), pair.getIntent());
		} else {
		    addButtonView(new MiddleRoundButtonView(context), pair.getTitle(), pair.getIntent());
		}
	    }
	} else if (buttonSize == 0) {
	    removeAllViews();
	}
    }

    private void addButtonView(ButtonView v, String text, Intent intent) {
	v.setText(text);
	v.setOnClickListener(this);
	v.setTag(intent);
	addView(v);
    }

    public void onClick(View v) {
    	Intent intent = (Intent)v.getTag();
	listener.onItemClick(v, intent);
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

    public interface OnItemClickListener {
	public void onItemClick(View v, Intent intent);
    }
}