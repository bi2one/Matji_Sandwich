package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;


public interface Refreshable {
    public void refresh();
    public void refresh(MatjiData data);
    public void refresh(ArrayList<MatjiData> data);
}