package com.matji.sandwich.http.parser;

import android.content.Context;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.UserExternalAccount;
import com.matji.sandwich.exception.MatjiException;

public class UserExternalAccountParser extends MatjiDataParser {
	public UserExternalAccountParser(Context context) {
		super(context);
	}

	protected UserExternalAccount getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		UserExternalAccount externalAccount = new UserExternalAccount();
		externalAccount.setId(getInt(object, "id"));
		externalAccount.setUserId(getInt(object, "user_id"));
		externalAccount.setService(getString(object, "service"));
		externalAccount.setData(getString(object, "data"));
		
		/* Set User */
		UserParser userParser = new UserParser(context);
		externalAccount.setUser(userParser.getMatjiData(getObject(object, "user")));

		return externalAccount;
	}
}