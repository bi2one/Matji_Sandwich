package com.matji.sandwich.listener;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.matji.sandwich.Requestable;
import com.matji.sandwich.base.Identifiable;
import com.matji.sandwich.data.Like;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.NotSupportedMatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.LikeHttpRequest;

/**
 * Like, Unlike를 수행하는 리스너.
 * 
 * @author mozziluv
 *
 */
public abstract class LikeListener implements OnClickListener, Requestable {

    /**
     * Like 요청 후 처리할 작업을 수행하는 메소드
     */
    public abstract void postLikeRequest();

    /**
     * Unlike 요청 후 처리할 작업을 수행하는 메소드
     */
    public abstract void postUnlikeRequest();
    
    private int type = MatjiData.UNSELECTED;
    private MatjiData data;
    private Identifiable identifiable;
    private Context context;
    private LikeHttpRequest likeRequest;
    private HttpRequestManager manager;
    private DBProvider dbProvider;

    /**
     * 기본 생성자. data의 전달 없이 {@link Identifiable}객체와 {@link Context}객체만 전달받는다.
     * 생성 후 반드시 {@link #setData(MatjiData)}를 이용해 데이터를 지정해 주어야 한다.
     * 
     * @param identifiable login 확인을 위한 identifiable 객체
     * @param context
     */
    public LikeListener(Identifiable identifiable, Context context) {
        this.identifiable = identifiable;
        this.context = context;
        likeRequest = new LikeHttpRequest(context);
        manager = HttpRequestManager.getInstance(context);
        dbProvider = DBProvider.getInstance(context);
    }

    /**
     * 
     * @param identifiable login 확인을 위한 identifiable 객체
     * @param context
     * @param store like, unlike 할 store
     */
    public LikeListener(Identifiable identifiable, Context context, Store store) {
        this(identifiable, context);
        type = MatjiData.STORE;
        data = store;
    }

    /**
     * 
     * @param identifiable identifiable login 확인을 위한 identifiable 객체
     * @param context
     * @param post like, unlike 할 post
     */
    public LikeListener(Identifiable identifiable, Context context, Post post) {
        this(identifiable, context);
        type = MatjiData.POST;
        data = post;
    }

    /**
     * 파라미터로 전달받은 데이터를 저장한다.
     * 해당 데이터의 타입에 따라 type 변수도 변경해준다.
     * 
     * @param data Like 가능한 {@link MatjiData}
     */
    public void setData(MatjiData data) {
        this.data = data;
        if (data instanceof Store) {
            type = MatjiData.STORE;
        } else if (data instanceof Post) {
            type = MatjiData.POST;
        } else {
            notSupported();
        }
    }

    @Override
    public void onClick(View v) {
        if (identifiable.loginRequired() && !manager.isRunning(context)) {
            if (dbProvider.isExistLike(getDataId(), getDBProviderObjectType())) {
                unlikeRequest();
            } else {
                likeRequest();
            }
        }
    }

    /**
     * 현재 타입에 따라 object type String을 리턴한다.
     * @return
     */
    private String getDBProviderObjectType() {
        String objType = "";
        switch (type) {
        case MatjiData.STORE:
            objType = DBProvider.STORE;
            break;
        case MatjiData.POST:
            objType = DBProvider.POST;
            break;
        default:
            notSupported();
        }

        return objType;
    }

    /**
     * 현재 타입에 따라 데이터의 id를 구해 리턴한다.
     * @return
     */
    private int getDataId() {
        int id = -1;

        switch (type) {
        case MatjiData.STORE:
            id = ((Store) data).getId();
            break;
        case MatjiData.POST:
            id = ((Post) data).getId();
            break;
        default:
            notSupported();
        }

        return id;
    }

    /**
     * like 요청
     */
    private void likeRequest() {
        switch (type) {
        case MatjiData.STORE:
            likeRequest.actionStoreLike(getDataId());
            break;
        case MatjiData.POST:
            likeRequest.actionPostLike(getDataId());
            break;
        default:
            notSupported();
        }
        manager.request(context, likeRequest, HttpRequest.LIKE_REQUEST, this);
    }

    /**
     * unlike 요청
     */
    private void unlikeRequest() {
        switch (type) {
        case MatjiData.STORE:
            likeRequest.actionStoreUnLike(getDataId());
            break;
        case MatjiData.POST:
            likeRequest.actionPostUnLike(getDataId());
            break;
        default:
            notSupported();
        }
        manager.request(context, likeRequest, HttpRequest.UN_LIKE_REQUEST, this);
    }


    /**
     * @see Requestable#requestCallBack(int, ArrayList)
     */
    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch (tag) {
        case HttpRequest.LIKE_REQUEST:
            Like like = new Like();
            like.setForeignKey(getDataId());
            like.setObject(getDBProviderObjectType());
            dbProvider.insertLike(like);

            postLikeRequest();
            break;
        case HttpRequest.UN_LIKE_REQUEST:
            dbProvider.deleteLike(getDataId(), getDBProviderObjectType());

            postUnlikeRequest();
            break;
        }
    }

    /**
     * @see Requestable#requestExceptionCallBack(int, MatjiException)
     */
    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.showToastMsg(context);
    }

    private void notSupported() {
        try {
            throw new NotSupportedMatjiException();
        } catch (NotSupportedMatjiException e) {
            e.printStackTrace();
        }
    }
}