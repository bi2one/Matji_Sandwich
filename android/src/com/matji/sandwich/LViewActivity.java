package com.matji.sandwich;

import java.util.*;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Following;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.FollowingHttpRequest;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LViewActivity extends ListActivity implements Requestable{
	HttpRequestManager manager;

	public void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);

		 ListView lv = getListView();
		 lv.setTextFilterEnabled(true);

		 manager = new HttpRequestManager(getApplicationContext(), this);
		 request();
	}

	private void request(){
		FollowingHttpRequest request = new FollowingHttpRequest();
		request.actionFollowingList(100000001);
		manager.request(request, 1);
	}
	
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		String myData= "";
		ArrayList<String> mArray = new ArrayList<String>();
		setListAdapter(new ArrayAdapter<String>(this, R.layout.testlistview, mArray));
		for(int i = 0; i < data.size() ; i ++){
			Following mData = (Following)data.get(i);			
			myData = "" + mData.getId();
			mArray.add(myData);
		}
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.showToastMsg(getApplicationContext());
	}

}
