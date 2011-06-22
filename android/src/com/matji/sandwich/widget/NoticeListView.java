package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.NoticeAdapter;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Notice;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.NoticeHttpRequest;

public class NoticeListView extends RequestableMListView {
	private HttpRequest request;
	private Context context;
	private NoticeAdapter adapter;

	public NoticeListView(Context context, AttributeSet attrs) {
		super(context, attrs, new NoticeAdapter(context), 10);
		this.context = context;

		setPage(1);
	}

	@Override
	public HttpRequest request() {
		if (request == null || !(request instanceof NoticeHttpRequest)) {
			request = new NoticeHttpRequest(getActivity());
		}

		((NoticeHttpRequest) request).actionList(getPage(), getLimit());

		return request;
	}

	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		String target = null;
		String targetAndroid = context.getString(R.string.target_android);
		String targetCommon = context.getString(R.string.target_common);

		ArrayList<MatjiData> removedData = new ArrayList<MatjiData>();
		
		for (MatjiData d : data) {
			target = ((Notice) d).getTarget();
			
			if (target != null) {
				if (!(target.equals(targetAndroid) || target.equals(targetCommon))) {
					removedData.add(d);
				}
			}  else {
				removedData.add(d);
			}
		}
		
		for (MatjiData d : removedData) {
			data.remove(d);
		}

		super.requestCallBack(tag, data);
	}

	@Override
	public void onListItemClick(int position) {
//		Log.d("Matji", find)+"");
	}
}