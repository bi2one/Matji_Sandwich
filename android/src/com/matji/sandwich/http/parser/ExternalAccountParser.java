package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.ExternalAccount;
import com.matji.sandwich.exception.MatjiException;

public class ExternalAccountParser extends MatjiDataParser {
	public ExternalAccount getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		ExternalAccount externalAccount = new ExternalAccount();
        externalAccount.setLinkedTwitter(getBoolean(object, "twitter"));
		externalAccount.setLinkedFacebook(getBoolean(object, "facebook"));
		
		return externalAccount;
	}
}