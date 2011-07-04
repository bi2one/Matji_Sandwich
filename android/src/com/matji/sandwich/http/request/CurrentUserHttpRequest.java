package com.matji.sandwich.http.request;

import android.content.Context;

public class CurrentUserHttpRequest extends HttpRequest {
	public CurrentUserHttpRequest(Context context) {
		super(context);
		controller = "comments";
	}
}