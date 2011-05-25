package com.matji.sandwich.data.db.request;

import java.util.ArrayList;
import android.database.sqlite.SQLiteDatabase;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.db.parser.DBStoreParser;
import com.matji.sandwich.data.db.sqlite.SQLiteResult;

public class DBStoreRequest extends DBRequest<Store>{
	private DBStoreParser storeParser;	
	
	public DBStoreRequest(SQLiteDatabase db){
		super(db);
		storeParser = new DBStoreParser();
	}
	
	public ArrayList<Store> query(String sql){
		SQLiteResult result = new SQLiteResult(db.rawQuery(sql, null));
		
		return storeParser.parse(result);
	}
}
