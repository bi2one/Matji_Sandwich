package com.matji.sandwich.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.MessageAdapter;
import com.matji.sandwich.adapter.MessageAdapter.MessageType;
import com.matji.sandwich.util.MatjiConstants;

/**
 * View displaying the list with sectioned header.
 * 
 * @author mozziluv
 * 
 */
public abstract class MessageListView extends RequestableMListView {
    public MessageListView(Context context, AttributeSet attr) {
        super(context, attr, new MessageAdapter(context), 10);
        init();
    }	

    protected void init() {
        setBackgroundColor(MatjiConstants.color(R.color.matji_white));
        setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
        setDividerHeight((int) MatjiConstants.dimen(R.dimen.default_divider_height));
        setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
        setCacheColorHint(Color.TRANSPARENT);
        setSelector(android.R.color.transparent);
        ((MessageAdapter) getMBaseAdapter()).setMessageType(getMessageType());
    }

    protected abstract MessageType getMessageType();
    
    public void onListItemClick(int position) {}
}
