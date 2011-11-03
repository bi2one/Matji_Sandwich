package com.matji.sandwich.session;

import java.io.NotSerializableException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.ViewGroup;

import com.matji.sandwich.ActivityStartable;
import com.matji.sandwich.Loginable;
import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.TermsActivity;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.Badge;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Me;
import com.matji.sandwich.data.User;
import com.matji.sandwich.data.provider.ConcretePreferenceProvider;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.data.provider.PreferenceProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.DialogAsyncTask;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.MeHttpRequest;
import com.matji.sandwich.http.request.NoticeHttpRequest;
import com.matji.sandwich.http.request.RequestCommand;

public class Session implements Requestable, DialogAsyncTask.ProgressListener {
    private volatile static Session session = null;

    private static final String keyForCurrentUser = "CurrentUser";
    private static final String keyForAccessToken = "AccessToken";

    private PreferenceProvider mPrefs;
    private ConcretePreferenceProvider mConcretePrefs;
    private SessionPrivateUtil mPrivateUtil;
    private WeakReference<Context> mContextRef;
    private HttpRequestManager mManager;
    private WeakReference<Loginable> mLoginableRef;

    private ArrayList<LoginListener> mLoginListeners; 
    private ArrayList<LogoutListener> mLogoutListeners;

    public interface LoginListener {
        public void preLogin();
        public void postLogin();
    }

    public interface LogoutListener {
        public void preLogout();
        public void postLogout();
    }

    public void addLoginListener(LoginListener listener) {
        mLoginListeners.add(listener);
    }

    public void addLogoutListener(LogoutListener listener) {
        mLogoutListeners.add(listener);
    }

    private Session(){}

    private Session(Context context){
        this.mPrefs = new PreferenceProvider(context);
        this.mConcretePrefs = new ConcretePreferenceProvider(context);        
        this.mPrivateUtil = new SessionPrivateUtil(this);
        this.mLoginListeners = new ArrayList<Session.LoginListener>();
        this.mLogoutListeners = new ArrayList<Session.LogoutListener>();
    }

    public void setContext(Context context) {
        this.mContextRef = new WeakReference<Context>(context);
    }

    public static Session getInstance(Context context) {
        if(session == null) {
            synchronized(Session.class) {
                if(session == null) {
                    session = new Session(context);
                }
            }
        }
        session.setContext(context);

        return session;
    }

    public void sessionValidate(Loginable loginable, ViewGroup layout){
        preLogin();
        this.mLoginableRef = new WeakReference<Loginable>(loginable);
        mManager = HttpRequestManager.getInstance();
        MeHttpRequest request = new MeHttpRequest(mContextRef.get());
        request.actionMe();
        mManager.request(mContextRef.get(), layout, request, HttpRequestManager.AUTHORIZE, this);
        notificationValidate();
    }

    public void unsyncSessionValidate() {
        mManager = HttpRequestManager.getInstance();
        MeHttpRequest request = new MeHttpRequest(mContextRef.get());
        request.actionMe();
        ArrayList<MatjiData> data = null;
        try {
            data = request.request();
        } catch (MatjiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

        if (data != null && !data.isEmpty() && data.size() > 0) {
            Me me = (Me) data.get(0);
            saveMe(me);
        } else {
            // TODO error
        }
    }

    public void loginWithDialog(final Context context, final String userid, final String password, Loginable loginable) {
        mLoginableRef = new WeakReference<Loginable>(loginable);
        RequestCommand loginRequest = new RequestCommand() {

            private HttpRequest request; 

            @Override
            public ArrayList<MatjiData> request() throws MatjiException {
                request = new MeHttpRequest(context);
                ((MeHttpRequest) request).actionAuthorize(userid, password);
                ArrayList<MatjiData> data = null;
                data = request.request();
                Me me = (Me)data.get(0);

                saveMe(me);
                notificationValidate();
                if (!me.isAgreeTerm()) {
                    
                    Intent intent = new Intent(context, TermsActivity.class);
                    intent.putExtra(TermsActivity.FROM_REGISTER_ACTIVITY, false);
                    
                    if (context instanceof ActivityStartable) {
                        ((BaseTabActivity) ((Activity) context).getParent()).tabStartActivityForResult(intent, BaseActivity.TERMS_ACTIVITY, (ActivityStartable) context);
                    } else {
                        ((Activity) context).startActivityForResult(intent, BaseActivity.TERMS_ACTIVITY);
                    }
                }

                return data;
            }

            @Override
            public void cancel() {
                request.cancel();
            }
        };
        DialogAsyncTask loginAsyncTask = new DialogAsyncTask(context, this, loginRequest, HttpRequestManager.AUTHORIZE);
        loginAsyncTask.setDialogString(R.string.session_onlogin);
        loginAsyncTask.setProgressListener(this);
        loginAsyncTask.execute();
    }

    public void onPreExecute(int tag) {
        switch(tag) {
        case HttpRequestManager.AUTHORIZE:
            preLogin();
            break;
        }
    }
    public void onStartBackground(int tag) { }
    public void onEndBackground(int tag) { }
    public void onPostExecute(int tag) { }

    //    public boolean login(String userid, String password) {
    //        mManager = HttpRequestManager.getInstance();
    //        MeHttpRequest request = new MeHttpRequest(mContextRef.get());
    //        request.actionAuthorize(userid, password);
    //        try {
    //            ArrayList<MatjiData> data = request.request();
    //            Me me = (Me)data.get(0);
    //            saveMe(me);
    //            return true;
    //        } catch (MatjiException e) {
    //            return false;
    //        }
    //        //        mManager.request(spinnerContainer, request, AUTHORIZE, this);
    //    }

    public boolean logout(){
        preLogout();

        mPrefs.clear();
        mPrivateUtil.notificationClear();
        removePrivateDataFromDatabase();

        postLogout();

        return mPrefs.commit(mContextRef.get());
    }


    private void removePrivateDataFromDatabase(){
        DBProvider dbProvider = DBProvider.getInstance(mContextRef.get());

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
        mPrefs.commit(mContextRef.get());

        removePrivateDataFromDatabase();

        DBProvider dbProvider = DBProvider.getInstance(mContextRef.get());               
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
        switch (tag) {
        case HttpRequestManager.AUTHORIZE:
            if (data == null || data.isEmpty()) {
                if (mLoginableRef != null && mLoginableRef.get() != null) {
                    mLoginableRef.get().loginFailed();
                }                
                return;
            }
            Me me = (Me)data.get(0);
            saveMe(me);

            if (mLoginableRef != null && mLoginableRef.get() != null) {
                mLoginableRef.get().loginCompleted();
            }

            postLogin();
            break;
        }
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
        if (tag == HttpRequestManager.AUTHORIZE){
            e.performExceptionHandling(mContextRef.get());
            if (mLoginableRef != null && mLoginableRef.get() != null) 
                mLoginableRef.get().loginFailed();
        }
    }

    public String getToken() {
        return mPrefs.getString(keyForAccessToken, null);
    }

    public PreferenceProvider getPreferenceProvider() {
        return mPrefs;
    }

    public ConcretePreferenceProvider getConcretePreferenceProvider() {
        return mConcretePrefs;
    }

    public void setCurrentUser(User user) {
        User currentUser = getCurrentUser();
        if (currentUser != null) {
            currentUser.setNick(user.getNick());
            currentUser.setEmail(user.getEmail());
            currentUser.setTitle(user.getTitle());
            currentUser.setIntro(user.getIntro());
            currentUser.setWebsite(user.getWebsite());
            currentUser.setPostCount(user.getPostCount());
            currentUser.setTagCount(user.getTagCount());
            currentUser.setUrlCount(user.getUrlCount());
            currentUser.setLikeStoreCount(user.getLikeStoreCount());
            currentUser.setDiscoverStoreCount(user.getDiscoverStoreCount());
            currentUser.setBookmarkStoreCount(user.getBookmarkStoreCount());
            currentUser.setFollowingCount(user.getFollowingCount());
            currentUser.setFollowerCount(user.getFollowerCount());
            currentUser.setReceivedMessageCount(user.getReceivedMessageCount());
            currentUser.setImageCount(user.getImageCount());
            currentUser.setMileage(user.getMileage());
            currentUser.setAttchFiles(user.getAttachFiles());
            currentUser.setStores(user.getStores());
            currentUser.setCountryCode(user.getCountryCode());
        } else {
            currentUser = user;
        }
        try {
            mPrefs.setObject(keyForCurrentUser, currentUser);
        } catch (NotSerializableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public User getCurrentUser(){
        Object obj = mPrefs.getObject(keyForCurrentUser);
        if (obj == null)
            return null;

        return (User)obj;
    }

    public SessionPrivateUtil getPrivateUtil() {
        return mPrivateUtil;
    }

    public boolean isCurrentUser(User user) {
        return isLogin() && (getCurrentUser().getId() == user.getId());
    }

    public void preLogin() {
        Log.d("Matji","pre login at " + mContextRef.get().getClass());
        for (LoginListener listener : mLoginListeners) listener.preLogin();
    }

    public void postLogin() {
        Log.d("Matji","post login at " + mContextRef.get().getClass());
        for (LoginListener listener : mLoginListeners) listener.postLogin();
    }

    public void preLogout() {
        Log.d("Matji", "pre logout at " + mContextRef.get().getClass());
        for (LogoutListener listener : mLogoutListeners) listener.preLogout();
    }

    public void postLogout() {
        Log.d("Matji", "post logout at " + mContextRef.get().getClass());
        for (LogoutListener listener : mLogoutListeners) listener.postLogout();
    }

    public void notificationValidate() {
        NoticeHttpRequest request = new NoticeHttpRequest(mContextRef.get());
        request.actionBadge(mPrivateUtil.getLastReadNoticeId());
        ArrayList<MatjiData> data = null;
        try {
            data = request.request();
        } catch (MatjiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (data != null && !data.isEmpty() && data.size() > 0) {
            Badge badge = (Badge) data.get(0);
            mPrivateUtil.setNewNoticeCount(badge.getNewNoticeCount());
            mPrivateUtil.setNewAlarmCount(badge.getNewAlarmCount());
        }
    }

    //
    //    /*
    //     *  Async write Me info to local database
    //     */	
    //    private class AsyncTask extends AsyncTask<Me, Integer, Boolean>{
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
