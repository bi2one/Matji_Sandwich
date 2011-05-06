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

    public ArrayList<MatjiData> getRawData(String data) throws MatjiException {
    	ArrayList<MatjiData> user_external_accountList = new ArrayList<MatjiData>();
    	JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(data);
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
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	return user_external_accountList;
    }

	public ArrayList<MatjiData> getData(String data) throws MatjiException {
		String validData = validateData(data);
		return getRawData(validData);
	}
}