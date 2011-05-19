package com.matji.sandwich.widget;

import com.matji.sandwich.Requestable;

public interface ListScrollRequestable extends Requestable {
    public void requestNext();
    public void requestReload();
    
}