package com.matji.sandwich.http.parser;

import java.util.ArrayList;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;

public interface MatjiParser {
    public ArrayList<MatjiData> parseToMatjiDataList(String data) throws MatjiException;
}