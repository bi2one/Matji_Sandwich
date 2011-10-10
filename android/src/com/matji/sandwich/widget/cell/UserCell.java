package com.matji.sandwich.widget.cell;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.matji.sandwich.MessageListTabActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.Refreshable;
import com.matji.sandwich.UserProfileTabActivity;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.base.Identifiable;
import com.matji.sandwich.data.User;
import com.matji.sandwich.listener.FollowingListener;
import com.matji.sandwich.listener.LikeStoreListListener;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.title.UserTitle;
import com.matji.sandwich.widget.title.button.FollowButton.Followable;

/**
 * UI 상의 유저 셀 (유저 기본 정보가 보이는 곳)
 * 
 * @author mozziluv
 *
 */
public class UserCell extends Cell implements Followable {
    private User user;

    private Identifiable identifiable;

    private Button follow;
    private Button messageList;

    private Session session;

    private FollowingListener followingListener;
    private ViewGroup spinnerContainer;

    private CellProfileImageView profile;
    private TextView nick;
    private TextView point;
    private TextView area;
    private TextView likeList;

    private ArrayList<Refreshable> refreshables;
    /**
     * 기본 생성자 (Java Code)
     * 
     * @param context
     */
    public UserCell(Context context) {
        super(context, R.layout.cell_user);
    }

    /**
     * 기본 생성자 (XML)
     * 
     * @param context
     * @param attr
     */
    public UserCell(Context context, AttributeSet attr) {
        super(context, R.layout.cell_user);
        setId(R.id.UserCell);
    }

    /**
     * User 정보를 같이 전달 받을 때 사용하는 생성자.
     * 
     * @param context
     * @param user 이 Cell의 유저 데이터
     */
    public UserCell(Context context, User user) {
        super(context, R.layout.cell_user);
        setUser(user);
    }

    @Override
    protected void init() {
        super.init();
        spinnerContainer = (ViewGroup) findViewById(R.id.UserCell);
        refreshables = new ArrayList<Refreshable>();

        profile = ((CellProfileImageView) findViewById(R.id.profile));
        profile.showInsetBackground();
	
        nick = ((TextView) findViewById(R.id.cell_user_nick));
        point = ((TextView) findViewById(R.id.cell_user_point));
        area = ((TextView) findViewById(R.id.cell_user_area));
        likeList = ((TextView) findViewById(R.id.cell_user_like_list));
        follow = (Button) findViewById(R.id.cell_user_follow);
        messageList = (Button) findViewById(R.id.cell_user_message_list);
        followingListener = new FollowingListener(identifiable, getContext(), user, spinnerContainer) {

            @Override
            public void postUnfollowRequest() {
            	UserTitle.title_user.setFollowerCount(UserTitle.title_user.getFollowerCount() - 1);
            	refresh();
            }

            @Override
            public void postFollowRequest() {
                UserTitle.title_user.setFollowerCount(UserTitle.title_user.getFollowerCount() + 1);
                refresh();
            }
        };
        follow.setOnClickListener(followingListener);
        session = Session.getInstance(getContext());
        messageList.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                getContext().startActivity(new Intent(getContext(), MessageListTabActivity.class));
            }
        });
    }

    /**
     * 이 UserCell에 user 정보를 저장하고 해당 유저의 변하지 않는 정보들을 뷰에 뿌려준다.
     * 
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
        profile.setUserId(user.getId());
        nick.setText(user.getNick());
        if (user.getMileage() != null)
            point.setText(user.getMileage().getTotalPoint()+"");
        else
            point.setText("0");
        area.setText(MatjiConstants.countryName(user.getCountryCode()));
        likeList.setOnClickListener(new LikeStoreListListener(getContext(), user));
        followingListener.setUser(user);
    }

    public void setIdentifiable(Identifiable identifiable) {
        this.identifiable = identifiable;
        followingListener.setIdentifiable(identifiable);
    }

    /**
     * Refresh 가능한 정보들을 refresh
     * 
     * @param user
     */
    public void refresh() {
        if (session.isLogin() && session.getCurrentUser().getId() == user.getId()) {
            setUser(session.getCurrentUser());
            follow.setVisibility(View.GONE);
            messageList.setVisibility(View.VISIBLE);
            messageList.setText(user.getReceivedMessageCount()+"");
        } else {
            follow.setText(
                    (followingListener.isExistFollowing()) ? 
                            R.string.cell_user_unfollow
                            : R.string.cell_user_follow);
        }

        for (Refreshable refreshable : refreshables) {
            refreshable.refresh(user);
        }
    }

    /**
     * 
     */
    @Override
    public void gotoDetailPage() {
        Intent intent = new Intent(getContext(), UserProfileTabActivity.class);
        intent.putExtra(UserProfileTabActivity.USER, (Parcelable) user);
        ((Activity) getContext()).startActivityForResult(intent, BaseActivity.USER_PROFILE_TAB_ACTIVITY);
    }

    public void showLine() {
        findViewById(R.id.cell_user_line).setVisibility(View.VISIBLE);
        findViewById(R.id.cell_user_shadow).setVisibility(View.GONE);
    }

    public void addRefreshable(Refreshable refreshable) {
        refreshables.add(refreshable);
    }

    @Override
    public void following() {
        followingListener.following();
    }

    @Override
    public boolean isFollow() {
        return followingListener.isExistFollowing();
    }
}