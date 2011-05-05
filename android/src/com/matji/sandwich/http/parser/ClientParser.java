package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Client;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class ClientParser extends MatjiDataParser{
    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> clientList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		Client client = new Client();
		client.setId(element.getInt("id"));
		client.setUser_id(element.getInt("user_id"));
		client.setApplication_name(element.getString("application_name"));
		client.setClient_id(element.getString("client_id"));
		client.setClient_secret(element.getString("client_secret"));
		client.setRedirect_uri(element.getString("redirect_uri"));
		client.setSequence(element.getInt("sequence"));
		clientList.add(client);
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return clientList;
    }
}