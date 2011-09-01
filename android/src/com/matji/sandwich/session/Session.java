package com.matji.sandwich.session;

import java.io.NotSerializableException;
import java.util.ArrayList;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.ViewGroup;

import com.matji.sandwich.Loginable;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Me;
import com.matji.sandwich.data.User;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.data.provider.PreferenceProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.MeHttpRequest;

public class Session implements Requestable {

    private volatile static Session session = null;
    private static final int AUTHORIZE = 0;

    private static final String keyForCurrentUser = "CurrentUser";
    private static final String keyForAccessToken = "AccessToken";

    private PreferenceProvider mPrefs;
    private Context mContext;
    private HttpRequestManager mManager;
    private Loginable mLoginable;

    private Session(){}

    private Session(Context context){
        this.mContext = context;
        this.mPrefs = new PreferenceProvider(context);
    }


    public static Session getInstance(Context context) {
        if(session == null) {
            synchronized(Session.class) {
                if(session == null) {
                    session = new Session(context);
                }
            }
        }

        return session;
    }

    public void sessionValidate(Loginable loginable, ViewGroup layout){
        this.mLoginable = loginable;
        mManager = HttpRequestManager.getInstance(mContext);
        MeHttpRequest request = new MeHttpRequest(mContext);
        request.actionMe();
        mManager.request(layout, request, AUTHORIZE, this);
    }


    public boolean login(ViewGroup spinnerContainer, String userid, String password){
        mManager = HttpRequestManager.getInstance(mContext);
        MeHttpRequest request = new MeHttpRequest(mContext);
        request.actionAuthorize(userid, password);
        try {
            ArrayList<MatjiData> data = request.request();
            Me me = (Me)data.get(0);
            saveMe(me);
            return true;
        } catch (MatjiException e) {            
            return false;
        }
//        mManager.request(spinnerContainer, request, AUTHORIZE, this);
    }


    public boolean logout(){
        mPrefs.clear();
        removePrivateDataFromDatabase();
        return mPrefs.commit();
    }


    private void removePrivateDataFromDatabase(){
        DBProvider dbProvider = DBProvider.getInstance(mContext);

        dbProvider.deleteBookmarks();
        dbProvider.deleteLikes();
        dbProvider.deleteFollowers();
        dbProvider.deleteFollowings();
    }

    public boolean isLogin(){
        return (mPrefs.getObject(keyForCurrentUser) == null) ? false : true;	
    }


    public void saveMe(Me me){
        try {
            mPrefs.setObject(keyForCurrentUser, me.getUser());
        } catch (NotSerializableException e) {
            e.printStackTrace();
        }

        mPrefs.setString(keyForAccessToken, me.getToken());
        mPrefs.commit();

        removePrivateDataFromDatabase();

        DBProvider dbProvider = DBProvider.getInstance(mContext);               
        SQLiteDatabase db = dbProvider.getDatabase();
        try{
            db.beginTransaction();
            dbProvider.insertBookmarks(me.getBookmarks());  
            dbProvider.insertLikes(me.getLikes());
            dbProvider.insertFollowers(me.getFollowers());
            dbProvider.insertFollowings(me.getFollowings());
            db.setTransactionSuccessful();
        } catch (SQLException e){
        } finally {
            db.endTransaction();
        }
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        if (tag == AUTHORIZE){
            Me me = (Me)data.get(0);
            saveMe(me);

            if (mLoginable != null)
                mLoginable.loginCompleted();
        }
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
        if (tag == AUTHORIZE){
            if (mLoginable != null)
                mLoginable.loginFailed();
        }
    }


    public String getToken() {
        return mPrefs.getString(keyForAccessToken, null);
    }

    public PreferenceProvider getPreferenceProvider() {
        return mPrefs;
    }

    public User getCurrentUser(){
        Object obj = mPrefs.getObject(keyForCurrentUser);
        if (obj == null)
            return null;

        return (User)obj;
    }



//
//    /*
//     *  Async write Me info to local database
//     */	
//    private class SaveMeAsyncTask extends AsyncTask<Me, Integer, Boolean>{
//        ProgressDialog dialog;
//
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//        }
//
//
//        private boolean save(Me me){
//            try {
//                mPrefs.setObject(keyForCurrentUser, me.getUser());
//            } catch (NotSerializableException e) {
//                e.printStackTrace();
//            }
//
//            mPrefs.setString(keyForAccessToken, me.getToken());
//            mPrefs.commit();
//
//            removePrivateDataFromDatabase();
//
//            DBProvider dbProvider = DBProvider.getInstance(mContext);               
//            SQLiteDatabase db = dbProvider.getDatabase();
//            try{
//                db.beginTransaction();
//                dbProvider.insertBookmarks(me.getBookmarks());  
//                dbProvider.insertLikes(me.getLikes());
//                dbProvider.insertFollowers(me.getFollowers());
//                dbProvider.insertFollowings(me.getFollowings());
//                db.setTransactionSuccessful();
//            } catch (SQLException e){
//            } finally {
//                db.endTransaction();
//            }
//
//            return true;
//        }
//
//
//        @Override
//        protected Boolean doInBackground(Me... args) {
//            // TODO Auto-generated method stub
//            if (args.length == 0) return false;
//
//            return save(args[0]);
//        }
//
//        @Override
//        protected void onPostExecute(Boolean result) {
//            // TODO Auto-generated method stub
//            super.onPostExecute(result);
//
//            Log.d("Matji", "Me info saved properly");
//            dialog.dismiss();
//        }
//    }


}
