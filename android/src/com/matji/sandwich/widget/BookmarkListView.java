package com.matji.sandwich.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.matji.sandwich.MainMapActivity;
import com.matji.sandwich.adapter.BookmarkAdapter;
import com.matji.sandwich.data.Region;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.RegionHttpRequest;

public class BookmarkListView extends RequestableMListView {
    private HttpRequest request;

    public BookmarkListView(Context context, AttributeSet attrs) {
	super(context, attrs, new BookmarkAdapter(context), 10);
	request = new RegionHttpRequest(context);

	setPage(1);
    }

    public HttpRequest request() {
	((RegionHttpRequest) request).actionBookmarkedList(getPage(), getLimit());
	return request;
    }

    public void onListItemClick(int position) {
	Region region = (Region) getAdapterData().get(position);
	Intent data = new Intent();
	    
	data.putExtra(MainMapActivity.RETURN_KEY_LAT_NE, region.getLatNe());
	data.putExtra(MainMapActivity.RETURN_KEY_LAT_SW, region.getLatSw());
	data.putExtra(MainMapActivity.RETURN_KEY_LNG_NE, region.getLngNe());
	data.putExtra(MainMapActivity.RETURN_KEY_LNG_SW, region.getLngSw());

	getActivity().setResult(Activity.RESULT_OK, data);
	getActivity().finish();
    }

    public void addItem(Region region) {
	getAdapterData().add(0, region);
	if (getAdapterData().size() % getLimit() > 0 && isNextRequestable()) {
	    getAdapterData().remove(getAdapterData().size() - 1);
	}
	getMBaseAdapter().notifyDataSetChanged();
    }
}