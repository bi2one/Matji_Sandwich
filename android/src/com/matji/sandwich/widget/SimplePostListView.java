package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.SimplePostAdapter;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.util.MatjiConstants;

/**
 * View displaying the list with sectioned header.
 * 
 * @author mozziluv
 * 
 */
public class SimplePostListView extends RequestableMListView {
    private HttpRequest request;

    public SimplePostListView(Context context, AttributeSet attr) {
        super(context, attr, new SimplePostAdapter(context), 10);
        init();
    }
    
    protected void init() {
        setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
        setDividerHeight(1);
        setBackgroundDrawable(null);
    }

    public HttpRequest request() {
        if (request == null || !(request instanceof PostHttpRequest)) {
            request = new PostHttpRequest(getContext());
        }
        ((PostHttpRequest) request).actionList(getPage(), getLimit());
        return request;
    }

    public void setPosts(ArrayList<MatjiData> data) {
        getMBaseAdapter().setData(data);
    }

    public void onListItemClick(int position) {}
}
