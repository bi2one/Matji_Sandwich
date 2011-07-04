package com.matji.sandwich.data.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.matji.sandwich.data.Bookmark;
import com.matji.sandwich.data.CoordinateRegion;
import com.matji.sandwich.data.Like;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.data.db.request.DBStoreRequest;
import com.matji.sandwich.data.provider.DataBaseHelper;

public class DBProvider {
	private static String STORE_TABLE_NAME = "matji_stores";
	private static String BOOKMARK_TABLE_NAME = "matji_bookmarks";
	private static String LIKE_TABLE_NAME = "matji_likes";
	private static String FOLLOWING_TABLE_NAME = "matji_followings";
	private static String FOLLOWER_TABLE_NAME = "matji_followers";

	private static DBProvider dbProvider;
	private static DataBaseHelper dbHelper;

	private DBProvider(Context context) {
		dbHelper = new DataBaseHelper(context);
		try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			throw new Error("Unable to create database");
		}

		try {
			dbHelper.open();
		} catch (SQLException e) {
			throw e;
		}
	}

	public static DBProvider getInstance(Context context) {
		if (dbProvider == null) {
			synchronized (DBProvider.class) {
				if (dbProvider == null) {
					dbProvider = new DBProvider(context);
				}
			}
		}
		return dbProvider;
	}

	private int getRecordNums(String table, String condition) {
		SQLiteDatabase db = dbHelper.getDatabase();
		String query = "SELECT count(*) FROM " + table;
		if (condition != null) {
			query = query + " WHERE " + condition;
		}

		Cursor dataCount = db.rawQuery(query, null);
		dataCount.moveToFirst();
		int jcount = dataCount.getInt(0);
		dataCount.close();

		return jcount;
	}

	public boolean isExistStore(int storeId) {
		return getRecordNums(STORE_TABLE_NAME, "store_id = " + storeId) > 0;
	}

	private ContentValues createStoreContentValues(Store store) {
		String tags = null;
		if (store.getTags() != null) {
			ArrayList<String> tagArray = new ArrayList<String>();
			Iterator<Tag> tagIter = store.getTags().iterator();
			Tag t = null;
			while ((t = tagIter.next()) != null) {
				tagArray.add(t.getTag());
			}
			tags = TextUtils.join(",", tagArray);
		}

		int attachFileId = store.getFile().getId();

		ContentValues cv = new ContentValues();
		if (store.getName() == null)
			cv.putNull("name");
		else
			cv.put("name", store.getName());
		if (store.getAddress() == null)
			cv.putNull("address");
		else
			cv.put("address", store.getAddress());
		if (store.getAddAddress() == null)
			cv.putNull("add_address");
		else
			cv.put("add_address", store.getAddAddress());
		if (store.getTel() == null)
			cv.putNull("tel");
		else
			cv.put("tel", store.getTel());
		if (store.getWebsite() == null)
			cv.putNull("website");
		else
			cv.put("website", store.getWebsite());
		if (store.getCover() == null)
			cv.putNull("cover");
		else
			cv.put("cover", store.getCover());
		if (tags == null)
			cv.putNull("tags");
		else
			cv.put("tags", tags);
		if (store.getRegUserId() == 0)
			cv.putNull("reg_user_id");
		else
			cv.put("reg_user_id", store.getRegUserId());
		if (attachFileId == 0)
			cv.putNull("attach_file");
		else
			cv.put("attach_file", attachFileId);

		cv.put("store_id", store.getId());
		cv.put("lat", store.getLat());
		cv.put("lng", store.getLng());
		cv.put("like_count", store.getLikeCount());
		cv.put("bookmark_count", store.getBookmarkCount());
		cv.put("post_count", store.getPostCount());
		cv.put("image_count", store.getImageCount());
		cv.put("tag_count", store.getTagCount());

		return cv;
	}

	private boolean insertStore(Store store) {
		SQLiteDatabase db = dbHelper.getDatabase();
		ContentValues cvInsert = createStoreContentValues(store);

		return db.insert(STORE_TABLE_NAME, null, cvInsert) != -1;
	}

	private boolean updateStore(Store store) {
		SQLiteDatabase db = dbHelper.getDatabase();
		ContentValues cvUpdate = createStoreContentValues(store);

		return db.update(STORE_TABLE_NAME, cvUpdate,
				"store_id = " + store.getId(), null) == 1;
	}

	public void insertStores(ArrayList<Store> stores) {
		boolean success = false;

		for (Store store : stores) {
			if (isExistStore(store.getId()) == false) {
				success = insertStore(store);
			} else {
				success = updateStore(store);
			}

			if (!success) {
				// Todo : Error handling
			}

		}

	}

	public boolean deleteStores() {
		SQLiteDatabase db = dbHelper.getDatabase();
		db.delete(STORE_TABLE_NAME, null, null);

		return true;
	}

	public ArrayList<Store> loadStoresInRegion(CoordinateRegion region) {
		SQLiteDatabase db = dbHelper.getDatabase();
		DBStoreRequest storeRequest = new DBStoreRequest(db);

		String sql = "";

		return storeRequest.query(sql);
	}

	public boolean isExistBookmark(int id, String object) {
		return getRecordNums(BOOKMARK_TABLE_NAME, "object_id =  " + id + " AND object = " + "'" + object + "'") > 0;
	}

	public boolean insertBookmark(Bookmark bookmark) {
		SQLiteDatabase db = dbHelper.getDatabase();

		ContentValues cv = new ContentValues();
		cv.put("object", bookmark.getObject());
		cv.put("object_id", bookmark.getForeignKey());

		return db.insert(BOOKMARK_TABLE_NAME, null, cv) != -1;
	}

	public boolean insertBookmarks(ArrayList<Bookmark> bookmarks) {
		for (Bookmark bookmark : bookmarks) {
			insertBookmark(bookmark);
		}
		return true;
	}

	public boolean deleteBookmark(int id, String object) {
		SQLiteDatabase db = dbHelper.getDatabase();
		db.delete(BOOKMARK_TABLE_NAME, "object_id = " + id + " AND object = '" + object + "'", null);
		
		return true;
	}
	
	public boolean deleteBookmarks() {
		SQLiteDatabase db = dbHelper.getDatabase();
		db.delete(BOOKMARK_TABLE_NAME, null, null);

		return true;
	}

	public boolean isExistLike(int id, String object) {
		return getRecordNums(LIKE_TABLE_NAME, "object_id =  " + id + " AND object = " + "'" + object + "'") > 0;
	}
	
	public boolean insertLike(Like like) {
		SQLiteDatabase db = dbHelper.getDatabase();
		ContentValues cv = new ContentValues();
		cv.put("object", like.getObject());
		cv.put("object_id", like.getForeignKey());

		return db.insert(LIKE_TABLE_NAME, null, cv) != -1;
	}

	public boolean insertLikes(ArrayList<Like> likes) {
		for (Like like : likes) {
			insertLike(like);
		}

		return true;
	}
	
	public boolean deleteLike(int id, String object) {
		SQLiteDatabase db = dbHelper.getDatabase();
		db.delete(LIKE_TABLE_NAME, "object_id = " + id + " AND object = '" + object + "'", null);
		
		return true;
	}
	
	public boolean deleteLikes() {
		SQLiteDatabase db = dbHelper.getDatabase();
		db.delete(LIKE_TABLE_NAME, null, null);

		return true;
	}

	public boolean isExistFollowing(int id) {
		return getRecordNums(FOLLOWING_TABLE_NAME, "following_user_id =  " + id) > 0;
	}
	
	public boolean insertFollowing(int followingUId) {
		SQLiteDatabase db = dbHelper.getDatabase();

		ContentValues cv = new ContentValues();
		cv.put("following_user_id", followingUId);

		db.insert(FOLLOWING_TABLE_NAME, null, cv);

		return true;
	}

	public boolean insertFollowings(String[] followings) {
		if (followings == null)
			return false;

		String followingUId;
		int index = 0;
		int count = followings.length;

		for (index = 0; index < count; index++) {
			followingUId = followings[index];
			insertFollowing(Integer.parseInt(followingUId));
		}
		return true;
	}

	public boolean deleteFollowing(int followingUId) {
		SQLiteDatabase db = dbHelper.getDatabase();
		db.delete(FOLLOWING_TABLE_NAME, "following_user_id = " + followingUId, null);
		
		return true;
	}
	
	public boolean deleteFollowings() {
		SQLiteDatabase db = dbHelper.getDatabase();
		db.delete(FOLLOWING_TABLE_NAME, null, null);

		return true;
	}
	
	public boolean isExistFollower(int id) {
		return getRecordNums(FOLLOWER_TABLE_NAME, "followed_user_id =  " + id) > 0;
	}
	
	public boolean insertFollower(int followerUId) {
		SQLiteDatabase db = dbHelper.getDatabase();

		ContentValues cv = new ContentValues();
		cv.put("followed_user_id", followerUId);

		db.insert(FOLLOWER_TABLE_NAME, null, cv);

		return true;
	}

	public boolean insertFollowers(String[] followers) {
		if (followers == null)
			return false;

		String followerUId;
		int index = 0;
		int count = followers.length;
		for (index = 0; index < count; index++) {
			followerUId = followers[index];
			insertFollower(Integer.parseInt(followerUId));
		}
		return true;
	}
	
	public boolean deleteFollower(int followerUId) {
		SQLiteDatabase db = dbHelper.getDatabase();
		db.delete(FOLLOWER_TABLE_NAME, "followed_user_id= " + followerUId, null);
		
		return true;
	}
	
	public boolean deleteFollowers() {
		SQLiteDatabase db = dbHelper.getDatabase();
		db.delete(FOLLOWER_TABLE_NAME, null, null);

		return true;
	}

}
