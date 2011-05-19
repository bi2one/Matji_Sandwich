package com.matji.sandwich.data.provider;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import android.content.Context;
import android.content.SharedPreferences;

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
		
		try{ 
			FileInputStream fis = context.openFileInput(OBJECT_PREFERENCE_NAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			hmap = (HashMap<String, Object>)ois.readObject();
			ois.close();
			fis.close();
		}catch(Throwable e){}
		
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
	}
	
	public void setBoolean(String key, boolean value){
		sharedPref.edit().putBoolean(key, value);
	}

	public void setString(String key, String value){
		sharedPref.edit().putString(key, value);
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


