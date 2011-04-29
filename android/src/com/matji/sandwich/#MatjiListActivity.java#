package com.matji.sandwich;

public abstract class MatjiListActivity extends ListActivity
    implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {
    // for http request/eventHandle
    protected MatjiBaseAdapter adapter;
    protected ListHttpRequester requester;
    protected ListEventHandler eventHandler;
    protected ListHelper helper;
    protected Intent intent;
    protected Context mContext;

    // views
    private RelativeLayout footer = null;
    private RelativeLayout header = null;
    
    protected void init() {
	intent = getIntent();
	helper = (ListHelper) intent.getParcelableExtra("com.matji.sandwich.listhelper.ListHelper");
	adapter = helper.getAdapter();
	requester = helper.getRequester();
	eventHandler = helper.getEventHandler();
	mContext = getApplicationContext();
    }

    protected void onPause() {
	requester.cancel();
	super.onPause();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position long id) {
	int viewId = view.getId();

	if (footer != null && header != null) {
	    if (viewId == footer.getId() || viewId == header.getId())
		return;
	}
	
	onListItemClick(parent, view, position, id);
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
	// 스크롤시마다 불러짐. hasmorepage를 수행하기에는 조금 무겁다. 
	// view : The view whose scroll state is being reported
	// first : the index of the first visible cell
	// visible : the number of visible cells
	// total : the number of items in the list adapter.
	if (requester.hasMoreData()
	    && requester.isRequestReady()
	    && 
	    
	}
    }
}