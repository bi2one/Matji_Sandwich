package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.SearchResult;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.exception.MatjiException;


public class SearchResultParser extends MatjiDataParser {
    private MatjiDataParser parser;
    private int totalCount;

    public SearchResultParser(MatjiDataParser parser) {
        this.parser = parser;
    }

    @Override
    public String validateData(String data) throws MatjiException {
        try {
            JSONObject json = new JSONObject(data);
            this.totalCount = json.getInt("total_count");
        } catch (JSONException e) {
            throw new JSONMatjiException();
        }

        return parser.validateData(data);
    }
    
    @Override
    public ArrayList<MatjiData> getMatjiDataList(JsonElement jsonElement) throws MatjiException{
        SearchResult result = new SearchResult();
        result.setTotalCount(totalCount);
        result.setData(parser.getMatjiDataList(jsonElement));
        ArrayList<MatjiData> results = new ArrayList<MatjiData>();
        results.add(result);
        
        return results;
    }
    
    @Override
    public MatjiData getMatjiData(JsonObject object) throws MatjiException {
        return parser.getMatjiData(object);
    }
}