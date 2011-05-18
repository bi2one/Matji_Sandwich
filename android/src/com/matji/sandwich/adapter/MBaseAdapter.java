package com.matji.sandwich.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.matji.sandwich.data.MatjiData;

import java.util.ArrayList;

public abstract class MBaseAdapter extends BaseAdapter {
    protected LayoutInflater inflater;
    protected Context context;
    protected ArrayList<MatjiData> data;
    
    public MBaseAdapter(Context context, ArrayList<MatjiData> data) {
	inflater = LayoutInflater.from(context);
	this.context = context;
	this.data = data;
    }

    public MBaseAdapter(Context context) {
	inflater = LayoutInflater.from(context);
	this.context = context;
    }

    protected LayoutInflater getLayoutInflater() {
	return inflater;
    }
    
    public void setData(ArrayList<MatjiData> data) {
	this.data = data;
    }
  	
    public int getCount() {
	return data.size();
    }
   
    public Object getItem(int position) {
	return data.get(position);
    }
   
    public long getItemId(int position) {
	return position;
    }
}
