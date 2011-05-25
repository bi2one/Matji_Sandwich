package com.matji.sandwich.http.parser;

import com.google.gson.JsonObject;
import com.matji.sandwich.data.Region;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;

public class RegionParser extends MatjiDataParser {
	protected Region getMatjiData(JsonObject object) throws MatjiException {
		Region region = new Region();
		region.setId(getInt(object, "id"));
		region.setUserId(getInt(object, "user_id"));
		region.setLatSw(getInt(object, "lat_sw"));
		region.setLngSw(getInt(object, "lng_sw"));
		region.setLatNe(getInt(object, "lat_ne"));
		region.setLngNe(getInt(object, "lng_ne"));
		region.setDescription(getString(object, "description"));
		region.setUser((User) new UserParser().getRawObject(getObject(object, "user") + ""));
		
		return region;
	}
}