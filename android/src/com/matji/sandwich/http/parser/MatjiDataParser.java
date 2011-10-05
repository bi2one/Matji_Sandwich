package com.matji.sandwich.http.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;
import com.matji.sandwich.exception.JSONCodeMatjiException;
import com.matji.sandwich.http.request.HttpUtility;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class MatjiDataParser implements MatjiParser {
    protected abstract MatjiData getMatjiData(JsonObject object) throws MatjiException;
    public Context context;

    public MatjiDataParser(Context context) {
        this.context = context;
    }

    public ArrayList<MatjiData> getMatjiDataList(JsonElement jsonElement) throws MatjiException{
        if (!isArray(jsonElement)) {
            return new ArrayList<MatjiData>();
        }

        JsonArray jsonArray = jsonElement.getAsJsonArray();
        ArrayList<MatjiData> matjiDataList = new ArrayList<MatjiData>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement em = jsonArray.get(i);
            try {
                matjiDataList.add(getMatjiData(jsonArray.get(i).getAsJsonObject()));
            } catch (IllegalStateException e) {
                Log.d("Matji", "WTF is " + em.getClass());
            }
        }

        return matjiDataList;
    }

    public String validateData(String data) throws MatjiException {
        try {
            JSONObject json = new JSONObject(data);
            int code = json.getInt("code");
            if (code == HttpUtility.HTTP_STATUS_OK) {
                return json.getString("result");
            } else {
                String message = json.getString("description");
                throw new JSONCodeMatjiException(message);
            }
        } catch (JSONException e) {
            throw new JSONMatjiException();
        }
    }

    protected MatjiData getRawObject(String data) throws MatjiException {
        JsonObject jsonObject = null;
        JsonElement jsonElement = null;

        if (isNull(data)) return null;

        try {			
            JsonParser parser = new JsonParser();
            jsonElement = parser.parse(data);
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new JSONMatjiException();
        }
        if (!isObject(jsonElement)){
            throw new JSONMatjiException();
        }

        jsonObject = jsonElement.getAsJsonObject();

        return getMatjiData(jsonObject);
    }

    protected ArrayList<MatjiData> getRawObjects(String data) throws MatjiException {
        JsonElement jsonElement = null;

        if (isNull(data)) return null;

        try {
            JsonParser parser = new JsonParser();
            jsonElement = parser.parse(data);
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new JSONMatjiException();
        }

        return getMatjiDataList(jsonElement);
    }

    public ArrayList<MatjiData> parseToMatjiDataList(String data) throws MatjiException {
        if (data == null) return null;
        String validData = validateData(data);
        return getRawObjects(validData);
    }

    public MatjiData parseToMatjiData(String data) throws MatjiException{
        if (data == null) return null;
        String validData = validateData(data);
        return getRawObject(validData);		
    }

    protected boolean isNull(JsonElement element) {
        if (element == null || element instanceof JsonNull) {
            return true;
        }
        return false;
    }

    protected boolean isNull(String data) {
        // TODO
        if (data == null) return true;
        else if (data.equals("null") || data.equals("NULL") || data.equals("Null")) return true;
        return false;
    }

    protected boolean isArray(JsonElement element) {
        if (isNull(element)) return false;

        return (element instanceof JsonArray) ? true : false;

    }

    protected boolean isObject(JsonElement element) {
        if (isNull(element)) return false;

        return (element instanceof JsonObject) ? true : false;
    }

    protected boolean isPrimitive(JsonElement element) {
        if (isNull(element)) return false;

        return (element instanceof JsonPrimitive) ? true : false;
    }

    protected int getInt(JsonObject object, String key) {
        JsonElement element = object.get(key);

        return (isPrimitive(element)) ? element.getAsInt() : 0;
    }

    protected double getDouble(JsonObject object, String key) {
        JsonElement element = object.get(key);			

        return (isPrimitive(element)) ? element.getAsDouble() : 0.0;
    }	

    protected long getLong(JsonObject object, String key) {
        JsonElement element = object.get(key);			

        return (isPrimitive(element)) ? element.getAsLong() : 0;
    }

    protected boolean getBoolean(JsonObject object, String key) {
        JsonElement element = object.get(key);			

        return (isPrimitive(element)) ? element.getAsBoolean() : false;
    }

    protected String getString(JsonObject object, String key) {
        JsonElement element = object.get(key);

        return (isPrimitive(element)) ? element.getAsString() : null;
    }

    protected JsonObject getObject(JsonObject object, String key) {
        JsonElement element = object.get(key);

        return (isObject(element)) ? element.getAsJsonObject() : null;	
    }

    protected JsonArray getArray (JsonObject object, String key) {
        JsonElement element = object.get(key);

        return (isArray(element)) ? element.getAsJsonArray() : null;
    }
}