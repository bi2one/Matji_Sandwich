package com.matji.sandwich.data.db.parser;

import java.util.ArrayList;
import java.util.HashMap;

import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.data.db.sqlite.SQLiteResult;

public class DBStoreParser extends DBParser<Store>{
	public ArrayList<Store> parse(SQLiteResult sqliteResult){
		ArrayList<HashMap<String, String>> result = sqliteResult.getResult();
		ArrayList<Store> stores = new ArrayList<Store>();
		
		for(HashMap<String, String> record : result){
			Store store = new Store();
			
			// int
			store.setId(getInt(record, "store_id"));
			store.setRegUserId(getInt(record, "reg_user_id"));
			store.setBookmarkCount(getInt(record, "bookmark_count"));
			store.setLikeCount(getInt(record, "like_count"));
			store.setPostCount(getInt(record, "post_count"));
			store.setImageCount(getInt(record, "image_count"));
			store.setTagCount(getInt(record, "tag_count"));
			// double		
			store.setLat(getDouble(record, "lat"));
			store.setLng(getDouble(record, "lng"));
			// String
			store.setName(getString(record, "name"));
			store.setAddAddress(getString(record, "add_address"));
			store.setAddress(getString(record, "address"));
			store.setCover(getString(record, "cover"));
			store.setWebsite(getString(record, "website"));
			// AttachFile
			int attachFileId = getInt(record, "attach_file");
			if (attachFileId != 0){
				AttachFile af = new AttachFile();
				af.setId(attachFileId);
				store.setFile(af);
			}
			// Tag
			String tagString = getString(record, "tags");
			if (tagString != null && tagString.trim().length() > 0){
				String[] tagArr = tagString.split(",");
				ArrayList<Tag> tags = new ArrayList<Tag>();
				for (String tag : tagArr){
					Tag t = new Tag();
					t.setTag(tag);
					tags.add(t);
				}
				store.setTags(tags);
			}					
		}
		
        
        return stores;
	}
}
