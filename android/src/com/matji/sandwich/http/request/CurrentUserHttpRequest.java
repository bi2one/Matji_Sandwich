package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.MatjiDataParser;
import com.matji.sandwich.http.request.HttpUtility.SimpleHttpResponse;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class CurrentUserHttpRequest extends HttpRequest {
	private MatjiDataParser parser;
	private String controller;
	private String action;
		
	public CurrentUserHttpRequest(Context context) {
		super(context);
		controller = "comments";
	}
		
	public ArrayList<MatjiData> request() throws MatjiException {		
		SimpleHttpResponse response = 
			(httpMethod == HttpMethod.HTTP_POST) ? 
					requestHttpResponsePost(serverDomain + controller +"/" + action, null, postHashtable)
					:requestHttpResponseGet(serverDomain + controller +"/" + action, null, getHashtable); 

		String resultBody = response.getHttpResponseBodyAsString();
		String resultCode = response.getHttpStatusCode() + "";

		Log.d("Matji", "CommentresultBody: " + resultBody);
		Log.d("Matji", "CommentresultCode: " + resultCode);

		return parser.parseToMatjiDataList(resultBody);
	}
}