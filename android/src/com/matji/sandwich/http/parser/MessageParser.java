package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class MessageParser extends MatjiDataParser {
	protected MatjiData getMatjiData(JsonObject object) throws MatjiException {
		Message message = new Message();
		message.setId(getInt(object, "id"));
		message.setSentUserId(getInt(object, "sent_user_id"));
		message.setReceivedUserId(getInt(object, "sent_user_id"));
		message.setMessage(getString(object, "message"));
		message.setSentUser((User) new UserParser().getRawObject(getObject(object, "sent_user") + ""));
		message.setReceivedUser((User) new UserParser().getRawObject(getObject(object, "received_user") + ""));
		message.setCreatedAt(getString(object, "created_at"));
		message.setUpdatedAt(getString(object, "updated_at"));
		
		return message;
	}
}