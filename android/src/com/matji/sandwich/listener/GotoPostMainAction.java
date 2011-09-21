package com.matji.sandwich.listener;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.matji.sandwich.ActivityStartable;
import com.matji.sandwich.PostMainActivity;
import com.matji.sandwich.StoreMainActivity;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;

/**
 * 클릭 시 {@link StoreMainActivity}로 이동하도록 하는 클릭 리스너.
 * 
 * @author mozziluv
 *
 */
public class GotoPostMainAction implements OnClickListener {

    private Context context;
    private ArrayList<Post> posts;
    private int position;
    private Mode mode;

    private enum Mode {
        POST,
        POSTS,
        POST_ID,
    }

    /**
     * 기본 생성자
     * 
     * @param context
     * @param posts {@link PostMainActivity}에서 사용 할 {@link Post} 정보
     * @param position posts에서 현재 사용할 post의 위치
     */
    public GotoPostMainAction(Context context, Post post) {
        this.posts = new ArrayList<Post>();
        this.posts.add(post);
        this.position = 0;
        this.context = context;
        this.mode = Mode.POSTS;
    }
    
    /**
     * 기본 생성자
     * 
     * @param context
     * @param posts {@link PostMainActivity}에서 사용 할 {@link Post} 정보
     * @param position posts에서 현재 사용할 post의 위치
     */
    public GotoPostMainAction(Context context, ArrayList<Post> posts, int position) {
        this.posts = posts;
        this.position = position;
        this.context = context;
        this.mode = Mode.POSTS;
    }

    /**
     * 해당 리스너의 {@link Post} 정보를 변경한다.
     * 
     * @param posts 변경 할 {@link Post} 정보
     */
    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    /**
     * 현재 저장된 {@link Store}의 {@link StoreMainActivity}로 이동한다.
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, PostMainActivity.class);
        switch (mode) {
        case POSTS:
            intent.putExtra(PostMainActivity.POSTS, posts);
            intent.putExtra(PostMainActivity.POSITION, position);
            if (((Activity) context).getParent() != null && ((Activity) context).getParent().getParent() instanceof BaseTabActivity) {
                    BaseTabActivity parent = (BaseTabActivity) ((Activity) context).getParent();
                    parent.tabStartActivityForResult(intent, BaseActivity.POST_MAIN_ACTIVITY, (ActivityStartable) context);
            } else {
                ((Activity) context).startActivityForResult(intent, BaseActivity.POST_MAIN_ACTIVITY);
            }
            break;
//        case POST_ID:
//            intent.putExtra(PostMainActivity.POST_ID, post_id);
//            if (((Activity) context).getParent() != null) {
//                if (((Activity) context).getParent().getParent() instanceof BaseTabActivity) {
//                    BaseTabActivity parent = (BaseTabActivity) ((Activity) context).getParent();
//                    parent.tabStartActivityForResult(intent, BaseActivity.POST_MAIN_ACTIVITY, (ActivityStartable) context);
//                }
//            } else {
//                ((Activity) context).startActivityForResult(intent, BaseActivity.POST_MAIN_ACTIVITY);
//            }          
        }
    }
}