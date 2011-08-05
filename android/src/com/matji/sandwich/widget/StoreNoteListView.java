package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.adapter.NoteAdapter;
import com.matji.sandwich.data.StoreDetailInfo;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreDetailHttpRequest;

public class StoreNoteListView extends RequestableMListView implements OnClickListener {
	private HttpRequest request;
	private int store_id;

	public StoreNoteListView(Context context, AttributeSet attrs) {
		super(context, attrs, new NoteAdapter(context), 10);
		request = new StoreDetailHttpRequest(context);
		setPage(1);
	}

	public void setStoreId(int store_id) {
		this.store_id = store_id;
	}
	
	@Override
	public HttpRequest request() {
		((StoreDetailHttpRequest) request).actionList(store_id, getPage(), getLimit());
		return request;
	}

	@Override
	public void onListItemClick(int position) {}


	public void onClick(View v) {
		int position = Integer.parseInt((String)v.getTag());
		StoreDetailInfo info = (StoreDetailInfo) getAdapterData().get(position);

		switch(v.getId()){
//		case R.id.post_adapter_profile: case R.id.post_adapter_nick:
//			gotoUserPage(info);
//			break;
		}
	}
	
	protected void gotoUserPage(StoreDetailInfo info) {
		Intent intent = new Intent(getActivity(), UserTabActivity.class);
		intent.putExtra(UserTabActivity.USER, (Parcelable) info.getUser());
		getActivity().startActivity(intent);
	}	

}