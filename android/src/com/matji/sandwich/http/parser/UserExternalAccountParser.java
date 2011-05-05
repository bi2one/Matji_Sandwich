package com.matji.sandwich.http.parser;

import java.util.ArrayList;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.UserExternalAccount;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.JSONMatjiException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class UserExternalAccountParser extends MatjiDataParser{

    public ArrayList<MatjiData> getData(String data) throws MatjiException {
	JSONArray jsonArray = validateData(data);
	ArrayList<MatjiData> user_external_accountList = new ArrayList<MatjiData>();

	try{
	    JSONObject element;
	    for(int i=0 ; i < jsonArray.length() ; i++){
		element = jsonArray.getJSONObject(i);
		UserExternalAccount user_external_account = new UserExternalAccount();
		user_external_account.setId(element.getInt("id"));
		user_external_account.setUser_id(element.getInt("user_id"));
		user_external_account.setService(element.getString("service"));
		user_external_account.setData(element.getString("data"));
		user_external_account.setSequence(element.getInt("sequence"));
	    }
	} catch(JSONException e){
	    throw new JSONMatjiException();
	}
	return user_external_accountList;
    }
}