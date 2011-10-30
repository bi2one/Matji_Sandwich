package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class MultiRoundButtonView extends LinearLayout implements View.OnClickListener {
    private ArrayList<IntentTitlePair> buttons;
    private OnItemClickListener listener;

    public MultiRoundButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        buttons = new ArrayList<IntentTitlePair>();
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
            addButtonView(new AllRoundButtonView(getContext()), firstPair.getTitle(), firstPair.getIntent());
        } else if (buttonSize > 1) {
            IntentTitlePair firstPair = buttons.get(0);
            IntentTitlePair lastPair = buttons.get(buttonSize - 1);
            for (IntentTitlePair pair : buttons) {
                if (pair == firstPair) {
                    addButtonView(new TopRoundButtonView(getContext()), pair.getTitle(), pair.getIntent());
                } else if (pair == lastPair) {
                    addButtonView(new BottomRoundButtonView(getContext()), pair.getTitle(), pair.getIntent());
                } else {
                    addButtonView(new MiddleRoundButtonView(getContext()), pair.getTitle(), pair.getIntent());
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
                return getContext().getString(titleRef);
            else
                return title;
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, Intent intent);
    }
}