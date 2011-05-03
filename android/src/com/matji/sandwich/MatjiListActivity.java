package com.matji.sandwich;

import android.app.ListActivity;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView;
import android.view.View;
import android.content.Context;

import java.util.ArrayList;

public abstract class MatjiListActivity extends ListActivity
    implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {
    // for http request/eventHandle
    protected Context mContext;

    // views
    private RelativeLayout footer = null;
    private RelativeLayout header = null;

    public void init() {
	mContext = getApplicationContext();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	int viewId = view.getId();

	if (footer != null && header != null) {
	    if (viewId == footer.getId() || viewId == header.getId())
		return;
	}
	
	onListItemClick(parent, view, position, id);
    }

    protected abstract void onListItemClick(AdapterView<?> parent, View view, int position, long id);

    public void onScrollStateChanged(AbsListView view, int scrollState) { }

    public abstract void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    // 스크롤시마다 불러짐.
    // view : The view whose scroll state is being reported
    // first : the index of the first visible cell
    // visible : the number of visible cells
    // total : the number of items in the list adapter.
}