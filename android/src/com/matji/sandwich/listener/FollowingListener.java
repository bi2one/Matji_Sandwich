package com.matji.sandwich.listener;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.matji.sandwich.Requestable;
import com.matji.sandwich.base.Identifiable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.FollowingHttpRequest;

/**
 * Follow, Unfollow를 수행하는 리스너.
 * 
 * @author mozziluv
 *
 */
public abstract class FollowingListener implements OnClickListener, Requestable {

    /**
     * Follow 요청 후 처리할 작업을 수행하는 메소드
     */
    public abstract void postFollowRequest();

    /**
     * Unfollow 요청 후 처리할 작업을 수행하는 메소드
     */
    public abstract void postUnfollowRequest();

    private User user;
    private Identifiable identifiable;
    private Context context;
    private FollowingHttpRequest followingRequest;
    private HttpRequestManager manager;
    private DBProvider dbProvider;
    private RelativeLayout spinnerContainer;

    /**
     * 기본 생성자. data의 전달 없이 {@link Identifiable}객체와 {@link Context}객체만 전달받는다.
     * 생성 후 반드시 {@link #setUser(User)}를 이용해 데이터를 지정해 주어야 한다.
     * 
     * @param identifiable login 확인을 위한 identifiable 객체
     * @param context
     */
    public FollowingListener(Identifiable identifiable, Context context, RelativeLayout spinnerContainer) {
        this.identifiable = identifiable;
        this.context = context;
        followingRequest = new FollowingHttpRequest(context);
        manager = HttpRequestManager.getInstance(context);
        dbProvider = DBProvider.getInstance(context);
        this.spinnerContainer = spinnerContainer;
    }

    /**
     * 
     * @param identifiable login 확인을 위한 identifiable 객체
     * @param context
     * @param store follow, unfollow 할 {@link User}
     */
    public FollowingListener(Identifiable identifiable, Context context, User user, RelativeLayout spinnerContainer) {
        this(identifiable, context, spinnerContainer);
        this.user = user;
    }

    /**
     * 파라미터로 전달받은 {@link User} 객체를 저장한다.
     * 
     * @param user follow, unfollow 할 {@link User}
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onClick(View v) {
        following();
    }
    
    /**
     * follow, unfollow 요청을 한다.
     */
    public void following() {
        if (identifiable.loginRequired() && !manager.isRunning()) {
            if (dbProvider.isExistFollowing(user.getId())){
                unfollowRequest();
            } else {
                followRequest();
            }
        }        
    }

    /**
     * follow 요청
     */
    private void followRequest() {
        followingRequest.actionNew(user.getId());
        manager.request(spinnerContainer, followingRequest, HttpRequestManager.FOLLOW_REQUEST, this);
    }

    /**
     * unfollow 요청
     */
    private void unfollowRequest() {
        followingRequest.actionDelete(user.getId());
        manager.request(spinnerContainer, followingRequest, HttpRequestManager.UN_FOLLOW_REQUEST, this);
    }


    /**
     * @see Requestable#requestCallBack(int, ArrayList)
     */
    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch (tag) {
        case HttpRequestManager.FOLLOW_REQUEST:
            dbProvider.insertFollowing(user.getId());

            postFollowRequest();
            break;
        case HttpRequestManager.UN_FOLLOW_REQUEST:
            dbProvider.deleteFollowing(user.getId());

            postUnfollowRequest();
            break;
        }
    }

    /**
     * @see Requestable#requestExceptionCallBack(int, MatjiException)
     */
    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.showToastMsg(context);
    }

    public boolean isExistFollowing() {
        return (dbProvider.isExistFollowing(user.getId())) ? true : false;
    }
}