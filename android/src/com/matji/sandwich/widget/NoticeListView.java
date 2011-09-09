package com.matji.sandwich.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.NoticeAdapter;
import com.matji.sandwich.data.Notice;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.NoticeHttpRequest;
import com.matji.sandwich.util.MatjiConstants;

public class NoticeListView extends RequestableMListView {
    private HttpRequest request;

    public NoticeListView(Context context, AttributeSet attrs) {
        super(context, attrs, new NoticeAdapter(context), 10);

        init();
    }

    protected void init() {
        setPage(1);
        setBackgroundColor(MatjiConstants.color(R.color.matji_white));
        setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
        setDividerHeight((int) MatjiConstants.dimen(R.dimen.default_divider_size));
        setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
        setCacheColorHint(Color.TRANSPARENT);
        setSelector(android.R.color.transparent);
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
    public void onListItemClick(int position) {}

    public void setLastReadNoticeId(int lastReadNoticeId) {
        ((NoticeAdapter) getMBaseAdapter()).setLastReadNoticeId(lastReadNoticeId);
    }

    public int getFirstIndexNoticeId() {
        if (!getAdapterData().isEmpty())
            return ((Notice) getAdapterData().get(0)).getId();
        else return 0;
    }
}