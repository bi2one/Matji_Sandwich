package com.matji.sandwich.http.request;

import android.content.Context;

import com.matji.sandwich.http.parser.SearchResultParser;
import com.matji.sandwich.http.parser.UserParser;
import com.matji.sandwich.session.Session;

public class UserHttpRequest extends HttpRequest {
    public UserHttpRequest(Context context) {
        super(context);
        controller = "users";
    }

    public void actionList(int page, int limit){
        httpMethod = HttpMethod.HTTP_GET;
        action = "list";
        parser = new UserParser(context);

        getHashtable.clear();
        getHashtable.put("page", page+"");
        getHashtable.put("limit", limit+"");
        getHashtable.put("include", "stores,attach_files,user_mileage"); 
    }

    public void actionShow(int user_id){
        httpMethod = HttpMethod.HTTP_GET;
        action = "show";
        parser = new UserParser(context);

        getHashtable.clear();
        getHashtable.put("user_id", user_id + "");
        getHashtable.put("include", "stores,attach_files,user_mileage");
    }

    public void actionRankingList(int page, int limit) {
        httpMethod = HttpMethod.HTTP_GET;
        action = "list";
        parser = new UserParser(context);

        getHashtable.clear();
        getHashtable.put("page", page + "");
        getHashtable.put("limit", limit + "");
        getHashtable.put("join", "user_mileage");
        getHashtable.put("order", "user_mileages.total_point desc");
        getHashtable.put("include", "user_mileage");
    }

    public void actionNearByRankingList(double lat_sw, double lat_ne, double lng_sw, double lng_ne, int page, int limit) {
        httpMethod = HttpMethod.HTTP_GET;
        action = "nearby_ranking_list";
        parser = new UserParser(context);

        getHashtable.clear();
        getHashtable.put("lat_ne", lat_ne + "");
        getHashtable.put("lat_sw", lat_sw + "");
        getHashtable.put("lng_ne", lng_ne + "");
        getHashtable.put("lng_sw", lng_sw + "");
        getHashtable.put("page", page + "");
        getHashtable.put("limit", limit + "");
        getHashtable.put("include", "user_mileage");
    }

    public void actionStoreLikeList(int store_id, int page, int limit) {
        httpMethod = HttpMethod.HTTP_GET;
        action = "like_list";
        parser = new UserParser(context);

        getHashtable.clear();		
        getHashtable.put("store_id", store_id+"");
        getHashtable.put("page", page+"");
        getHashtable.put("limit", limit+"");
        getHashtable.put("include", "stores,attach_files,user_mileage"); 
    }

    public void actionSearch(String keyword, int page, int limit) {
        httpMethod = HttpMethod.HTTP_GET;
        action = "search";
        parser = new SearchResultParser(new UserParser(context));

        getHashtable.clear();
        getHashtable.put("q",keyword);
        getHashtable.put("page", page + "");
        getHashtable.put("limit", limit + ""); 
        getHashtable.put("include", "stores,attach_files,user_mileage");
    }

    public void actionStoreFoodLikeList(int store_food_id, int page, int limit) {
        httpMethod = HttpMethod.HTTP_GET;
        action = "like_list";
        parser = new UserParser(context);

        getHashtable.clear();		
        getHashtable.put("store_food_id", store_food_id+"");
        getHashtable.put("page", page+"");
        getHashtable.put("limit", limit+"");
        getHashtable.put("include", "stores,attach_files,user_mileage"); 
    }

    public void actionPostLikeList(int post_id, int page, int limit) {
        httpMethod = HttpMethod.HTTP_GET;
        action = "like_list";
        parser = new UserParser(context);

        getHashtable.clear();
        getHashtable.put("post_id", post_id+"");
        getHashtable.put("page", page+"");
        getHashtable.put("limit", limit+"");
        getHashtable.put("include", "stores,attach_files,user_mileage"); 
    }

    public void actionMe() {

    }

    public void actionAuthorize() {

    }

    public void actionCreate(String email, String nick, String password, String password_confirmation) {
        httpMethod = HttpMethod.HTTP_POST;
        action = "create";
        parser = new UserParser(context);
        
        postHashtable.clear();
        postHashtable.put("user[email]", email);
        postHashtable.put("user[nick]", nick);
        postHashtable.put("user[password]", password);
        postHashtable.put("user[password_confirmation]", password_confirmation);
    }

    public void actionProfile() {

    }

    public void actionUpdateCountry(String country_code) {
        httpMethod = HttpMethod.HTTP_GET;
        action="update";
        parser = new UserParser(context);

        getHashtable.clear();
        Session session = Session.getInstance(context);

        if (session != null && session.isLogin())
            getHashtable.put("access_token", "" + session.getToken());
        getHashtable.put("country_code", country_code);
    }
    
    public void actionUpdateNick(String nick) {
        httpMethod = HttpMethod.HTTP_GET;
        action="update";
        parser = new UserParser(context);

        getHashtable.clear();
        Session session = Session.getInstance(context);

        if (session != null && session.isLogin())
            getHashtable.put("access_token", "" + session.getToken());
        getHashtable.put("nick", nick);        
    }

    public void actionChangePassword(String password, String new_password, String new_password_confirmation) {
        httpMethod = HttpMethod.HTTP_GET;
        action="change_password";
        parser = new UserParser(context);

        getHashtable.clear();
        Session session = Session.getInstance(context);

        if (session != null && session.isLogin())
            getHashtable.put("access_token", "" + session.getToken());
        getHashtable.put("password", password);
        getHashtable.put("new_password", new_password);
        getHashtable.put("new_password_confirmation", new_password_confirmation);
    }

    public void actionUpdateIntro(String intro) {
        httpMethod = HttpMethod.HTTP_GET;
        action="update";
        parser = new UserParser(context);

        getHashtable.clear();
        Session session = Session.getInstance(context);

        if (session != null && session.isLogin())
            getHashtable.put("access_token", "" + session.getToken());
        getHashtable.put("intro", intro);        
    }

    public void actionUpdateWebsite(String website) {
        httpMethod = HttpMethod.HTTP_GET;
        action="update";
        parser = new UserParser(context);

        getHashtable.clear();
        Session session = Session.getInstance(context);

        if (session != null && session.isLogin())
            getHashtable.put("access_token", "" + session.getToken());
        getHashtable.put("website", website);        
    }
}