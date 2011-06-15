package com.matji.sandwich.widget;

import android.view.View;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;

import com.matji.sandwich.http.util.DisplayUtil;

public abstract class MListView extends ListView implements OnItemClickListener {
	private Activity activity;
	private LinearLayout header;
	public abstract void onListItemClick(int position);
	public static final int FADING_EDGE_LENGTH = DisplayUtil.DPFromPixel(10);

	public MListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		header = new LinearLayout(context);
		header.setOrientation(LinearLayout.VERTICAL);

		super.addHeaderView(header, null, false);
		setOnItemClickListener(this);
		setFadingEdgeLength(FADING_EDGE_LENGTH);
		setCacheColorHint(Color.LTGRAY);
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