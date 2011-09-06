package com.matji.sandwich.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.AlarmAdapter;
import com.matji.sandwich.data.Alarm;
import com.matji.sandwich.http.request.AlarmHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.util.MatjiConstants;

public class AlarmListView extends RequestableMListView {
	private HttpRequest request;
	
	public AlarmListView(Context context, AttributeSet attrs) {
		super(context, attrs, new AlarmAdapter(context), 10);
		
		init();
	}
	
	protected void init() {
        setPage(1);
        setBackgroundColor(MatjiConstants.color(R.color.matji_white));
        setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
        setDividerHeight(MatjiConstants.dimenInt(R.dimen.default_divider_height));
        setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
        setCacheColorHint(Color.TRANSPARENT);
        setSelector(android.R.color.transparent);
	}

	public HttpRequest request() {
		if (request == null || !(request instanceof AlarmHttpRequest)) {
			request = new AlarmHttpRequest(getActivity());
		}

		((AlarmHttpRequest) request).actionList(getPage(), getLimit());

		return request;
	}

    @Override
    public void onListItemClick(int position) {}
    
    public void setLastReadAlarmId(int lastReadAlarmId) {
        ((AlarmAdapter) getMBaseAdapter()).setLastReadAlarmId(lastReadAlarmId);
    }
    
    public int getLastReadAlarmId() {
        if (!getAdapterData().isEmpty())
            return ((Alarm) getAdapterData().get(0)).getId();
        else return 0;
    }
}