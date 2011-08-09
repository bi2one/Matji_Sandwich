package com.matji.sandwich.data.provider;

import java.io.*;
import java.util.HashMap;
import android.content.Context;
import android.util.*;

public class PreferenceProvider {
	//private final static String PRIMITIVE_PREFERENCE_NAME = "app_primitive_preference";
	private final static String OBJECT_PREFERENCE_NAME = "app_object_preference";
	
	//private SharedPreferences sharedPref;
	private Context context;
	private HashMap<String, Object> sharedObjects; 
	
	@SuppressWarnings("unused")
	private PreferenceProvider(){}
	
	
	public PreferenceProvider(Context context){
		this.context = context;	
		
		initSharedPreferences();		
	}
	
	
	private void initSharedPreferences(){
		//sharedPref = context.getSharedPreferences(PRIMITIVE_PREFERENCE_NAME, 0);
		sharedObjects = getSharedObjectPreferences();			
	}
	
	@SuppressWarnings("unchecked")
	private HashMap<String, Object> getSharedObjectPreferences(){
	    // Log.d("=====", "load");
		HashMap<String, Object> hmap = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = context.openFileInput(OBJECT_PREFERENCE_NAME);
			ois = new ObjectInputStream(fis);
			hmap = (HashMap<String, Object>)ois.readObject();
			if (hmap == null){
				Log.d("PreferenceProvider", "Preference Object read null Object");
				hmap = new HashMap<String, Object>();
			}else{
				Log.d("PreferenceProvider", "Preference Object read completely. Object count is " + hmap.size());	
			}
			
		}catch (Throwable e){
		    Log.d("=====", e.getMessage());
		    // file not found exception 발생함. 아래 있는 commit method를
		    // 실행하면 나지 않을 것 같음. --TODO--
			hmap = new HashMap<String, Object>();
			Log.d("PreferenceProvider", "Error occured while read Preference Object.");
		}
		
		/* Close File handler */
		try {
			if (ois != null) ois.close();
			if (fis != null) fis.close();
		}catch (IOException e) {
			e.printStackTrace(); 
		}
			
		
		return hmap;
	}
	
	public int getInt(String key, int defValue){
//		return sharedPref.getInt(key, defValue);
		Integer intValue = (Integer)sharedObjects.get(key);
		return (intValue == null) ?  defValue : intValue.intValue();
	}
	
	public String getString(String key ,String defValue){
//		return sharedPref.getString(key, defValue);
		String stringValue = (String)sharedObjects.get(key);
		return (stringValue == null) ? defValue : stringValue;
 	}
	
	public boolean getBoolean(String key, boolean defValue){
//		return sharedPref.getBoolean(key, defValue);
		Boolean boolValue = (Boolean)sharedObjects.get(key);
		return (boolValue == null) ? defValue : boolValue.booleanValue();
	}
	
	public Object getObject(String key){
		return sharedObjects.get(key);
	}
	
	public void setInt(String key, int value){
//		sharedPref.edit().putInt(key, value);
//		sharedPref.edit().commit();
		Integer intValue = new Integer(value);
		sharedObjects.put(key, intValue);
	}
	
	public void setBoolean(String key, boolean value){
//		sharedPref.edit().putBoolean(key, value);
		Boolean boolValue = new Boolean(value);
		sharedObjects.put(key, boolValue);
	}

	public void setString(String key, String value){
//		sharedPref.edit().putString(key, value);
//		sharedPref.edit().commit();		
		sharedObjects.put(key, value);
	}
	
	public void remove(String key){
//		sharedPref.edit().remove(key);
		sharedObjects.remove(key);
	}

	public void setObject(String key, Object obj) throws NotSerializableException{
		if (obj instanceof Serializable){
			sharedObjects.put(key, obj);
		}else{
			throw new NotSerializableException();
		}
	}
	
	
	
	
	public boolean commit(){
	    // Log.d("=====", "commit!!");
		boolean success = false;
		try{
            FileOutputStream fos = context.openFileOutput(OBJECT_PREFERENCE_NAME, Context.MODE_WORLD_WRITEABLE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(sharedObjects);
            oos.flush();
            oos.close();
            fos.close();
            success = true;
            //success = sharedPref.edit().commit();
        }catch(Throwable e){
        	e.printStackTrace();
        }
        
		return success;
	}
	
	public void clear(){
		//sharedPref.edit().clear();
		sharedObjects.clear();
		
	}
	
}


