package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.adapter.MessageAdapter.MessageType;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.MessageHttpRequest;

/**
 * View displaying the list with sectioned header.
 * 
 * @author mozziluv
 * 
 */
public class SentMessageListView extends MessageListView {
    private HttpRequest request;

    public SentMessageListView(Context context, AttributeSet attr) {
        super(context, attr);
    }	

    public HttpRequest request() {
        if (request == null || !(request instanceof MessageHttpRequest)) {
            request = new MessageHttpRequest(getContext());
        }
        ((MessageHttpRequest) request).actionSentList(getPage(), getLimit());
        return request;
    }
    
    @Override
    protected MessageType getMessageType() {
        return MessageType.SENT;
    }
}
