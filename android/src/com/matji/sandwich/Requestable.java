package com.matji.sandwich;

import java.util.ArrayList;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;

public interface Requestable {
    public void requestCallBack(int tag, ArrayList<MatjiData> data);
    public void requestExceptionCallBack(int tag, MatjiException e) ;
}