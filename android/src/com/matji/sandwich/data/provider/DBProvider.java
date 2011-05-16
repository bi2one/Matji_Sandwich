package com.matji.sandwich.data.provider;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.database.SQLException;

import com.matji.sandwich.data.CoordinateRegion;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.provider.DataBaseHelper;

public class DBProvider {
	private DataBaseHelper dbHelper;
	
	public DBProvider(Context context) {
		this.dbHelper = new DataBaseHelper(context);
		
		
		try {
			dbHelper.createDataBase();		
		}catch(IOException e) {
			throw new Error("Unable to create database");			
		}
		
		try {
			dbHelper.open();
		}catch(SQLException e) {
			throw e;			
		}
		
	}
	
	
	public void saveStore(ArrayList<Store> stores){
		
	}
	
	public ArrayList<Store> loadStoresInRect(CoordinateRegion region){
		
		return null;
	}

}
