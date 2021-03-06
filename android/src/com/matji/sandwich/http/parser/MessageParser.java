package com.matji.sandwich.http.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.util.MatjiConstants;

public class MessageParser extends MatjiDataParser {
    
    public static final String SYSTEM_MESSAGE_SPEC = "!@#$%^&*()";
	public Message getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Message message = new Message();
		message.setId(getInt(object, "id"));
		message.setSentUserId(getInt(object, "sent_user_id"));
		message.setReceivedUserId(getInt(object, "received_user_id"));
		message.setThreadId(getInt(object, "thread_id"));
		message.setMessage(getString(object, "message"));
		
		/* Set Sent User */
		UserParser userParser = new UserParser();
		message.setSentUser(userParser.getMatjiData(getObject(object, "sent_user")));
		
		/* Set Received User*/
		message.setReceivedUser(userParser.getMatjiData(getObject(object, "received_user")));
		message.setCreatedAt(getString(object, "created_at"));
		message.setUpdatedAt(getString(object, "updated_at"));
		message.setAgo(getLong(object, "ago"));
		message.setMsgRead(getBoolean(object, "msg_read"));
		
		if (message.getMessage().startsWith(SYSTEM_MESSAGE_SPEC)) {
		    message.setMessage(parseSystemMessage(message.getMessage()));
		}
		
		return message;
	}
	
	private String parseSystemMessage(String sysMsg) {
	    String msg = sysMsg.substring(SYSTEM_MESSAGE_SPEC.length(), sysMsg.length());
	    JSONObject obj;
        try {
            obj = new JSONObject(msg);
            return obj.getString(MatjiConstants.language());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null; // TODO error
	}
}