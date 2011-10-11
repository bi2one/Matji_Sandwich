package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.AlarmAdapter;
import com.matji.sandwich.data.Alarm;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.AlarmHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.SessionPrivateUtil;
import com.matji.sandwich.util.MatjiConstants;

public class AlarmListView extends RequestableMListView {
	
	private HttpRequest request;
	private SessionPrivateUtil privateUtil;
	private int lastReadAlarmCount;
	
	public AlarmListView(Context context, AttributeSet attrs) {
		super(context, attrs, new AlarmAdapter(context), 10);
		
		init();
	}
	
	protected void init() {
        setPage(1);
        setBackgroundColor(MatjiConstants.color(R.color.matji_white));
        setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
        setDividerHeight(MatjiConstants.dimenInt(R.dimen.default_divider_size));
        setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
        setCacheColorHint(Color.TRANSPARENT);
        setSelector(android.R.color.transparent);
        
        privateUtil = Session.getInstance(getContext()).getPrivateUtil();
	}

	public HttpRequest request() {
		if (request == null || !(request instanceof AlarmHttpRequest)) {
			request = new AlarmHttpRequest(getActivity());
		}

		((AlarmHttpRequest) request).actionList(getPage(), getLimit());

		return request;
	}

	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		if (tag != HttpRequestManager.ALARM_READ_REQUEST) {
			super.requestCallBack(tag, data);
			manager.request(getContext(), readRequest(data), HttpRequestManager.ALARM_READ_REQUEST, this);
			lastReadAlarmCount = data.size();
		} else {
			int newAlarmCount = privateUtil.getNewAlarmCount()-lastReadAlarmCount;
			if (newAlarmCount < 0) newAlarmCount = 0;
			privateUtil.setNewAlarmCount(newAlarmCount);
		}
	}

	public HttpRequest readRequest(ArrayList<MatjiData> alarms) {
		if (request == null || !(request instanceof AlarmHttpRequest)) { 
			request = new AlarmHttpRequest(getContext());
		}

		((AlarmHttpRequest) request).actionRead(alarms);
		return request;
	}

	public void deletedPost(int postId) {
	    for (MatjiData data : getAdapterData()) {
	        
	        if (((Alarm) data).getPost() != null && ((Alarm) data).getPost().getId() == postId) {
	            ((Alarm) data).setPost(null);
	        }
	    }
	    getMBaseAdapter().notifyDataSetChanged();
	}
	
    @Override
    public void onListItemClick(int position) {}
}