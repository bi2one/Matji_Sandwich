package com.matji.sandwich.widget;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;

// import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.R;

public abstract class MListView extends ListView implements OnItemClickListener {
    private Activity activity;
    private LinearLayout header;
    public abstract void onListItemClick(int position);

    public MListView(Context context, AttributeSet attrs) {
	super(context, attrs);
	header = new LinearLayout(context);
	header.setOrientation(LinearLayout.VERTICAL);

	super.addHeaderView(header, null, false);
	setOnItemClickListener(this);
    }

    public void addHeaderView(View v) {
	header.addView(v, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
    }

    public void addHeaderView(ViewContainer container) {
	addHeaderView(container.getRootView());
    }
    
    public LinearLayout getHeaderViewContainer(){
    	return header;
    }

    public Activity getActivity() {
	return activity;
    }

    public void setActivity(Activity activity) {
	this.activity = activity;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	if ((position - 1) >= 0) {
	    onListItemClick(position - 1);
	}
    }
}