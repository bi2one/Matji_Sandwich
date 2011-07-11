package com.matji.sandwich.http.request;

import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.data.MatjiData;

import java.util.ArrayList;

public interface RequestCommand {
    public ArrayList<MatjiData> request() throws MatjiException;
}