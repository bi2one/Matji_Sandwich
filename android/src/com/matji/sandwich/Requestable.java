package com.matji.sandwich;

import java.util.ArrayList;
import com.matji.sandwich.exception.MatjiException;

public interface Requestable<T> {
    public void requestCallBack(int tag, ArrayList<T> data);
    public void requestExceptionCallBack(int tag, MatjiException e) ;
}