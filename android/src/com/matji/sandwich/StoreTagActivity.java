package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.TagHttpRequest;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.cell.StoreCell;
import com.matji.sandwich.widget.tag.TagCloudView;
import com.matji.sandwich.widget.title.StoreTitle;

public class StoreTagActivity extends BaseActivity implements Requestable, Refreshable {

    private HttpRequest request;
	
	private TextView tagCount;
	
	private Store store;

	private StoreTitle title;
	private StoreCell storeCell;
    private TagCloudView tagCloudView;
	
	public static final String STORE = "StoreTagActivity.store";
	
    public int setMainViewId() {
	return R.id.activity_store_tag;
    }
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_tag);
		
		store = getIntent().getParcelableExtra(STORE);
		HttpRequestManager.getInstance(this).request(getMainView(), request(), HttpRequestManager.STORE_TAG_LIST_REQUEST, this);

		tagCloudView = (TagCloudView) findViewById(R.id.store_tag_cloud);
		
		tagCount = (TextView) findViewById(R.id.store_tag_count);
		String numTag = String.format(
		        MatjiConstants.string(R.string.number_of_tag),
		        store.getTagCount());
		tagCount.setText(numTag);
		
		title = (StoreTitle) findViewById(R.id.Titlebar);
		storeCell = (StoreCell) findViewById(R.id.StoreCell);

        
        title.setIdentifiable(this);
        title.setStore(store);
        title.setLikeable(storeCell);

        storeCell.setStore(store);
        storeCell.setIdentifiable(this);
        storeCell.addRefreshable(this);
        storeCell.addRefreshable(title);
	}
	
	@Override
	protected void onNotFlowResume() {
	    // TODO Auto-generated method stub
	    super.onNotFlowResume();
        storeCell.refresh();
	}
	
	public void setStore(Store store) {
		this.store = store;
	}
	
	private HttpRequest request() {
		if (request == null || !(request instanceof TagHttpRequest)) {
			request = new TagHttpRequest(this);
		}
		
		((TagHttpRequest) request).actionStoreTagList(store.getId(), 1, 50);

		return request;
	}
	
	
	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		// TODO Auto-generated method stub
		ArrayList<Tag> tags = new ArrayList<Tag>();
		for (MatjiData d : data) {
			tags.add((Tag) d);
		}
		
		tagCloudView.show(tags);
	}

	@Override
	public void requestExceptionCallBack(int tag, MatjiException e) {
		// TODO Auto-generated method stub
		e.performExceptionHandling(this);
	}

    @Override
    public void refresh() {
    }

    @Override
    public void refresh(MatjiData data) {
        if (data instanceof Store) {        
            this.store = (Store) data;
            refresh();
        }
    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {}
}
