package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.adapter.MessageAdapter;
import com.matji.sandwich.adapter.MessageAdapter.MessageType;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.MessageHttpRequest;

/**
 * View displaying the list with sectioned header.
 * 
 * @author mozziluv
 * 
 */
public class ReceivedMessageListView extends MessageListView {
    private HttpRequest request;

    public ReceivedMessageListView(Context context, AttributeSet attr) {
        super(context, attr);
    }	

    public HttpRequest request() {
        if (request == null || !(request instanceof MessageHttpRequest)) {
            request = new MessageHttpRequest(getContext());
        }
        ((MessageHttpRequest) request).actionReceivedList(getPage(), getLimit());
        return request;
    }
    
    @Override
    protected MessageType getMessageType() {
        return MessageType.RECEIVED;
    }
    
    public void setLastReadMessageId(int lastReadMessageId) {
        ((MessageAdapter) getMBaseAdapter()).setLastReadMessageId(lastReadMessageId);
    }
    
    public int getFirstIndexMessageId() {
        return ((Message) getAdapterData().get(0)).getId();
    }
}
