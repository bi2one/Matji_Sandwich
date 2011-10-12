package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.UserParser;

import android.content.Context;

public class FindPasswordHttpRequest extends HttpRequest {

	public FindPasswordHttpRequest(Context context) {
		super(context);
		controller = "users";
	}

	public void actionForgotPassword(String email) {
		action = "forgot_password";
		parser = new UserParser();
		
		getHashtable.clear();
		getHashtable.put("email", email);
	}
}
