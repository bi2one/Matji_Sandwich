package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;

import com.matji.sandwich.StoreTabActivity;
import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.UserHttpRequest;

public class RankingUserListView extends UserListView {
    private UserHttpRequest request;
    private double lat_ne, lng_ne, lat_sw, lng_sw;

    public RankingUserListView(Context context, AttributeSet attrs) {
	super(context, attrs);
	request = new UserHttpRequest(context);
	
	setPage(1);
    }
	
    public void setLocation(double lat_ne, double lng_ne, double lat_sw, double lng_sw) {
	this.lat_ne = lat_ne;
	this.lng_ne = lng_ne;
	this.lat_sw = lat_sw;
	this.lng_sw = lng_sw;
    }
	
    public HttpRequest request() {
	request.actionNearByRankingList(lat_sw, lat_ne, lng_sw, lng_ne, 1, 10);
	return request;
    }

    public void onListItemClick(int position) {
	User user = (User) getAdapterData().get(position);
	Intent intent = new Intent(getActivity(), UserTabActivity.class);
	intent.putExtra(UserTabActivity.USER, (Parcelable) user);
	getActivity().startActivity(intent);
    }

    protected void gotoUserPage(Post post) {
	Intent intent = new Intent(getActivity(), UserTabActivity.class);
	intent.putExtra(UserTabActivity.USER, (Parcelable) post.getUser());
	getActivity().startActivity(intent);
    }	

    protected void gotoStorePage(Post post) {
	Intent intent = new Intent(getActivity(), StoreTabActivity.class);
	intent.putExtra(StoreTabActivity.STORE, (Parcelable) post.getStore());
	getActivity().startActivity(intent);
    }
}