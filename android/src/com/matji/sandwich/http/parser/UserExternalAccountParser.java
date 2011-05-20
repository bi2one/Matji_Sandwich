package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.UserExternalAccount;
import com.matji.sandwich.exception.MatjiException;

public class UserExternalAccountParser extends MatjiDataParser<UserExternalAccount> {
	protected UserExternalAccount getMatjiData(JsonObject object) throws MatjiException {
		UserExternalAccount externalAccount = new UserExternalAccount();
		externalAccount.setId(getInt(object, "id"));
		externalAccount.setUserId(getInt(object, "user_id"));
		externalAccount.setService(getString(object, "service"));
		externalAccount.setData(getString(object, "data"));
		externalAccount.setUser(new UserParser().getRawObject(getObject(object, "user") + ""));
		
		return externalAccount;
	}
}