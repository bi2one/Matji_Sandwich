package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.CommentParser;

import android.content.Context;

public class CommentHttpRequest extends HttpRequest {
	public CommentHttpRequest(Context context) {
		super(context);
		parser = new CommentParser(context);
		controller = "comments";
	}
	
	public void actionNew(int post_id, String comment, String from_where) {

	    parser = new CommentParser(getContext());
		httpMethod = HttpMethod.HTTP_POST;
		action = "new";
				
		postHashtable.clear();
		postHashtable.put("post_id", post_id);
		postHashtable.put("comment", comment);
		postHashtable.put("from_where", from_where);
		postHashtable.put("include", "user");
	}
	
	public void actionDelete(int comment_id) {
		httpMethod = HttpMethod.HTTP_POST;
		action = "delete";
		
		postHashtable.clear();
		postHashtable.put("comment_id", comment_id);
	}
	
	public void actionList(int post_id, int page, int limit) {
	    parser = new CommentParser(getContext());
		httpMethod = HttpMethod.HTTP_GET;
		action = "list";

		getHashtable.clear();
		getHashtable.put("post_id", post_id+ "");
    	getHashtable.put("page", page+"");
    	getHashtable.put("limit", limit+"");  	
		getHashtable.put("include", "user");
	}
	

	public void actionShow(int comment_id) {
	    parser = new CommentParser(getContext());
		httpMethod = HttpMethod.HTTP_GET;
		action = "show";
		
		getHashtable.clear();
		getHashtable.put("comment_id", "" + comment_id);
	}
}