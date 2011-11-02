package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Email;
import com.matji.sandwich.exception.MatjiException;

public class EmailParser extends MatjiDataParser {
	public Email getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Email email = new Email();
		email.setEmail(getString(object, "email"));
		
		return email;
	}
}