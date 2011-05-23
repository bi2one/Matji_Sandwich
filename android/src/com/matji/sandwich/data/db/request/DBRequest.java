package com.matji.sandwich.data.db.request;

import java.util.ArrayList;
import android.database.sqlite.SQLiteDatabase;

public abstract class DBRequest<T> {
	protected SQLiteDatabase db;
	
	public DBRequest(SQLiteDatabase db){
		this.db = db;
	}
	
	public abstract	ArrayList<T> query(String sql);
}
