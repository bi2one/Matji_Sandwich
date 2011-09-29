package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.PostAdapter;
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
public class PostListView extends RequestableMListView {
    private HttpRequest request;
    private Type type;
    public enum Type {
        ALL, FRIEND, DOMESTIC;
    };

    protected String getSubtitle() {
        return MatjiConstants.string(R.string.subtitle_all_post);
    }

    public PostListView(Context context, AttributeSet attr) {
        super(context, attr, new PostAdapter(context), 10);
        init();
    }	

    protected void init() {
        setBackgroundDrawable(MatjiConstants.drawable(R.drawable.bg_01));
        setDivider(null);
        setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
        setCacheColorHint(Color.TRANSPARENT);
        setSelector(android.R.color.transparent);
        setSubtitle(getSubtitle());
        type = Type.ALL;
    }

    public void setSubtitle(String subtitle) {
        //        ((PostAdapter) getMBaseAdapter()).setSpinnerContainer(getLoadingFooterView());
        ((PostAdapter) getMBaseAdapter()).setSubtitle(subtitle);
    }

    public HttpRequest request() {
        if (request == null || !(request instanceof PostHttpRequest)) {
            request = new PostHttpRequest(getContext());
        }

        switch(type) {
        case ALL:
            ((PostHttpRequest) request).actionList(getPage(), getLimit());
            break;
        case FRIEND:
            ((PostHttpRequest) request).actionFriendList(getPage(), getLimit());
            break;
        case DOMESTIC:
            ((PostHttpRequest) request).actionCountryList(getPage(),
                    getLimit(),
                    getContext().getResources().getConfiguration().locale.getCountry());
            break;
        default:
            ((PostHttpRequest) request).actionList(getPage(), getLimit());
        }
        return request;
    }
    
    //    public void setActivity(Activity activity) {
    //        super.setActivity(activity);
    //        ((PostAdapter) getMBaseAdapter()).setActivity(getActivity());
    //    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setPosts(ArrayList<MatjiData> data) {
        getMBaseAdapter().setData(data);
    }

    public void onListItemClick(int position) {}
}
