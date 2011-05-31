package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.exception.MatjiException;

public class MessageParser extends MatjiDataParser {
	protected Message getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Message message = new Message();
		message.setId(getInt(object, "id"));
		message.setSentUserId(getInt(object, "sent_user_id"));
		message.setReceivedUserId(getInt(object, "sent_user_id"));
		message.setThreadId(getInt(object, "thread_id"));
		message.setMessage(getString(object, "message"));
		
		/* Set Sent User */
		UserParser userParser = new UserParser();
		message.setSentUser(userParser.getMatjiData(getObject(object, "sent_user")));
		
		/* Set Received User*/
		message.setReceivedUser(userParser.getMatjiData(getObject(object, "received_user")));
		message.setCreatedAt(getString(object, "created_at"));
		message.setUpdatedAt(getString(object, "updated_at"));

		return message;
	}
}