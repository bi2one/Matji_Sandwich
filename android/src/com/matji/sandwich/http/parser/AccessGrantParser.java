package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.AccessGrant;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class AccessGrantParser extends MatjiDataParser{
	public ArrayList<MatjiData> getData(String data) throws MatjiException{
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> access_grantList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		AccessGrant access_grant = new AccessGrant();
		access_grant.setId(element.getInt("id"));
		access_grant.setUser_id(element.getInt("user_id"));
		access_grant.setClient_id(element.getInt("client_id"));
		access_grant.setAccess_token(element.getString("access_token"));
		access_grant.setRefresh_token(element.getString("refresh_token"));
		access_grant.setCode(element.getString("code"));
		access_grant.setSequence(element.getInt("sequence"));
		access_grantList.add(access_grant);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return access_grantList;

	}
}