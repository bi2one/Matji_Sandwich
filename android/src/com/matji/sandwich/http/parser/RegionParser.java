package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Region;
import com.matji.sandwich.exception.MatjiException;

public class RegionParser extends MatjiDataParser {
	public Region getMatjiData(JsonObject object) throws MatjiException {
		if (object == null) return null;
		
		Region region = new Region();
		region.setId(getInt(object, "id"));
		region.setUserId(getInt(object, "user_id"));
		region.setLatSw(getDouble(object, "lat_sw"));
		region.setLngSw(getDouble(object, "lng_sw"));
		region.setLatNe(getDouble(object, "lat_ne"));
		region.setLngNe(getDouble(object, "lng_ne"));
		region.setDescription(getString(object, "description"));
		
		/* Set User */
		UserParser userParser = new UserParser();
		region.setUser(userParser.getMatjiData(getObject(object, "user")));
		
		return region;
	}
}