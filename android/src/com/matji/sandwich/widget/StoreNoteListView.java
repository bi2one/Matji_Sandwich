package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

import com.matji.sandwich.adapter.NoteAdapter;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreHttpRequest;

public class StoreNoteListView extends RequestableMListView implements OnClickListener {
	private HttpRequest request;
	private int store_id;

	public StoreNoteListView(Context context, AttributeSet attrs) {
		super(context, attrs, new NoteAdapter(context), 10);
		request = new StoreHttpRequest(context);
		setPage(1);
	}

	public void setStoreId(int store_id) {
		this.store_id = store_id;
	}
	
	@Override
	public HttpRequest request() {
		((StoreHttpRequest) request).actionDetailList(store_id, getPage(), getLimit());
		return request;
	}

	@Override
	public void onListItemClick(int position) {}

	
	public void onClick(View v) {}
}