package com.matji.sandwich.http.request;

import android.content.Context;

import com.matji.sandwich.http.parser.EmailParser;
import com.matji.sandwich.http.parser.UserParser;

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
    
    public void actionFindEmail(String userid) {
        action = "find_email";
        parser = new EmailParser();
        
        getHashtable.clear();
        getHashtable.put("userid", userid);
    }
}
