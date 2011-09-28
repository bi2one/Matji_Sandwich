package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.AppVersionParser;

import android.content.Context;

public class VersionHttpRequest extends HttpRequest {

	public VersionHttpRequest(Context context) {
		super(context);
		controller = "notices";
	}
	
	public void actionAppVersion(String target, String version) {
		action = "app_version";
		parser = new AppVersionParser(context);
		
		getHashtable.clear();
		getHashtable.put("target", target);
		getHashtable.put("version", version);
	}

}
