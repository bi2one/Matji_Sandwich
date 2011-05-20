package com.matji.sandwich.widget;

import com.matji.sandwich.Requestable;

public interface ListScrollRequestable<T> extends Requestable<T> {
    public void requestNext();
    public void requestReload();
    
}