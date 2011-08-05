package com.matji.sandwich.widget;

import android.widget.LinearLayout;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.LayoutInflater;
// import android.view.View;
// import android.view.View.OnClickListener;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.R;
// import com.matji.sandwich.LoginActivity;
import com.matji.sandwich.Requestable;
// import com.matji.sandwich.session.Session;

// import java.util.ArrayList;

public class RecentChangedLocationView extends LinearLayout {
    private Context context;
    private View emptyView;
    private View filledView;
    private MultiRoundButtonView locationButtons;

    public RecentChangedLocationView(Context context, AttributeSet attrs) {
	super(context, attrs);
	this.context = context;
	LayoutInflater.from(context).inflate(R.layout.recent_changed_location, this, true);
	emptyView = findViewById(R.id.recent_changed_location_empty);
	filledView = findViewById(R.id.recent_changed_location_filled);
	locationButtons = (MultiRoundButtonView) findViewById(R.id.recent_changed_location_locations);

	// for test -- TODO --
	locationButtons.add(new Intent(), "test");
	locationButtons.add(new Intent(), "test2");
	locationButtons.add(new Intent(), "test3");
	locationButtons.updateView();
    }
}