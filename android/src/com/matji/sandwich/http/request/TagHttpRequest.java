package com.matji.sandwich.http.request;

import com.matji.sandwich.http.parser.StoreTagParser;
import com.matji.sandwich.http.parser.TagParser;
import com.matji.sandwich.http.parser.UserTagParser;

import android.content.Context;

public class TagHttpRequest extends HttpRequest {
    //	private boolean isPost; // tag �����  GET 諛⑹�..?

    public TagHttpRequest(Context context) {
        super(context);
        controller = "tags";
    }

    public void actionShow(int tag_id) {
        action = "show";
        parser = new TagParser();

        getHashtable.clear();
        getHashtable.put("tag_id", tag_id + "");
    }

    public void actionList() {
        action = "list";
        parser = new TagParser();

        getHashtable.clear();
    }

    public void actionStoreTagList(int store_id, int page, int limit) {
        action = "store_tag_list";
        parser = new StoreTagParser();

        getHashtable.clear();
        getHashtable.put("store_id", store_id + "");
        getHashtable.put("page", page + "");
        getHashtable.put("limit", limit + "");
    }

    public void actionStoreTagListForCloud(int store_id) {
        actionStoreTagList(store_id, 1, 50);
        getHashtable.put("order", "count desc");
    }

    public void actionUserTagList(int user_id, int page, int limit) {
        action = "user_tag_list";
        parser = new UserTagParser();

        getHashtable.clear();
        getHashtable.put("user_id", user_id+ "");		
        getHashtable.put("page", page+ "");
        getHashtable.put("limit", limit+ "");
        getHashtable.put("order", "count DESC");
    }

    public void actionUserTagListForCloud(int user_id) {
        actionUserTagList(user_id, 1, 50);
        getHashtable.put("order", "count desc");
    }

    public void actionPostTagList(int post_id) {
        action = "post_tag_list";
        parser = new TagParser();

        getHashtable.clear();
        getHashtable.put("post_id", post_id + "");
    }
}