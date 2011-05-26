package com.matji.sandwich.data.provider;

import java.io.*;
import java.util.HashMap;

import android.app.*;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.*;
import android.util.*;

public class PreferenceProvider {
	private final static String PRIMITIVE_PREFERENCE_NAME = "app_primitive_preference";
	private final static String OBJECT_PREFERENCE_NAME = "app_object_preference";
	
	private SharedPreferences sharedPref;
	private Context context;
	private HashMap<String, Object> sharedObjects; 
	
	@SuppressWarnings("unused")
	private PreferenceProvider(){}
	
	
	public PreferenceProvider(Context context){
		this.context = context;	
		
		initSharedPreferences();		
	}
	
	
	private void initSharedPreferences(){
		sharedPref = context.getSharedPreferences(PRIMITIVE_PREFERENCE_NAME, 0);
		sharedObjects = getSharedObjectPreferences();			
	}
	
	@SuppressWarnings("unchecked")
	private HashMap<String, Object> getSharedObjectPreferences(){
		HashMap<String, Object> hmap = null;
		FileInputStream fis = null;
			try {
				fis = context.openFileInput(OBJECT_PREFERENCE_NAME);
			}catch (FileNotFoundException e){
				hmap = new HashMap<String, Object>();
				Log.d("PreferenceProvider", "Object Preference file not found.");
			}
			try {
				ObjectInputStream ois = new ObjectInputStream(fis);
				hmap = (HashMap<String, Object>)ois.readObject();
				if (hmap == null)
					hmap = new HashMap<String, Object>();
				ois.close();
				fis.close();
				Log.d("PreferenceProvider", "Object read completely");
				
			}catch(Throwable e){
				hmap = new HashMap<String, Object>();
				Log.d("PreferenceProvider", "Object Preference file founded. But is wrong.");
			}
			
			
			Log.d("PreferenceProvider", (hmap == null)?"18": "야어ㅑㅁㄴ어ㅐ먀어먀ㅐ");
		
		return hmap;
	}
	
	public int getInt(String key, int defValue){
		return sharedPref.getInt(key, defValue);
	}
	
	public String getString(String key ,String defValue){
		return sharedPref.getString(key, defValue);
	}
	
	public boolean getBoolean(String key, boolean defValue){
		return sharedPref.getBoolean(key, defValue);
	}
	
	public Object getObject(String key){
		return sharedObjects.get(key);		
	}
	
	public void setInt(String key, int value){
		sharedPref.edit().putInt(key, value);
		sharedPref.edit().commit();
	}
	
	public void setBoolean(String key, boolean value){
		sharedPref.edit().putBoolean(key, value);
	}

	public void setString(String key, String value){
		sharedPref.edit().putString(key, value);
		sharedPref.edit().commit();		
	}
	
	public void remove(String key){
		sharedPref.edit().remove(key);
	}

	public void setObject(String key, Object obj) throws NotSerializableException{
		if (obj instanceof Serializable){
			sharedObjects.put(key, obj);
		}else{
			throw new NotSerializableException();
		}
	}
	
	
	
	
	public boolean commit(){
		boolean success = false;
		try{   
            FileOutputStream fos = context.openFileOutput(OBJECT_PREFERENCE_NAME, Context.MODE_WORLD_WRITEABLE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(null); 
            oos.flush();
            
            oos.close();
            fos.close();
            success = sharedPref.edit().commit();
        }catch(Throwable e){ 
            success = false;
        }
        
		return success;
	}
	
	public void clear(){
		sharedPref.edit().clear();
	}
	
}


