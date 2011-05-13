package com.matji.sandwich.http.parser;

import com.google.gson.*;
import com.matji.sandwich.data.*;
import com.matji.sandwich.exception.*;

public class AttachFileParser extends MatjiDataParser{

	protected MatjiData getMatjiData(JsonObject object) throws MatjiException {
		AttachFile attach_file = new AttachFile();
		attach_file.setId(getInt(object, "id"));
		attach_file.setId(getInt(object, "user_id"));
		attach_file.setId(getInt(object, "store_id"));
		attach_file.setId(getInt(object, "post_id"));

		return attach_file;
	}
	

}
