package com.matji.sandwich;

import java.util.*;

import com.matji.sandwich.data.Store;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.StoreHttpRequest;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LViewActivity<T> extends ListActivity implements Requestable<T> {
	HttpRequestManager manager;

	public void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);

		 ListView lv = getListView();
		 lv.setTextFilterEnabled(true);

		 manager = new HttpRequestManager(getApplicationContext(), this);
		 request();
	}

	private void request(){
		StoreHttpRequest request = new StoreHttpRequest(getApplicationContext());
		request.actionList(1,1);
//		manager.request(request, 1);
	}
	
	public void requestCallBack(int tag, ArrayList<T> data) {
		String myData= "";
		ArrayList<String> mArray = new ArrayList<String>();
		setListAdapter(new ArrayAdapter<String>(this, R.layout.testlistview, mArray));
		for(int i = 0; i < data.size() ; i ++){
			Store mData = (Store)data.get(i);			
			myData = "" + mData.getLat();
			mArray.add(myData);
			myData = "" + mData.getLng();
			mArray.add(myData);
		}
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.showToastMsg(getApplicationContext());
	}

}
