package com.matji.sandwich.widget.cell;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matji.sandwich.MessageListActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.UserProfileActivity;
import com.matji.sandwich.base.Identifiable;
import com.matji.sandwich.data.User;
import com.matji.sandwich.listener.FollowingListener;
import com.matji.sandwich.listener.LikeStoreListListener;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.CellProfileImageView;
import com.matji.sandwich.widget.title.Switchable;
import com.matji.sandwich.widget.title.button.FollowButton.Followable;

/**
 * UI 상의 유저 셀 (유저 기본 정보가 보이는 곳)
 * 
 * @author mozziluv
 *
 */
public class UserCell extends Cell implements Followable {
    private User user;

    private Button follow;
    private Button messageList;

    private Session session;

    private FollowingListener followingListener;
    private RelativeLayout spinnerContainer;
    
    private Switchable switchable;

    /**
     * 기본 생성자 (Java Code)
     * 
     * @param context
     */
    public UserCell(Context context) {
        super(context, R.layout.cell_user);
        spinnerContainer = (RelativeLayout)findViewById(R.id.cell_user_InfoWrapper);
    }

    /**
     * 기본 생성자 (XML)
     * 
     * @param context
     * @param attr
     */
    public UserCell(Context context, AttributeSet attr) {
        super(context, R.layout.cell_user);
        spinnerContainer = (RelativeLayout)findViewById(R.id.cell_user_InfoWrapper);
        setId(R.id.UserCell);
    }

    /**
     * User 정보를 같이 전달 받을 때 사용하는 생성자.
     * 
     * @param context
     * @param user 이 Cell의 유저 데이터
     */
    public UserCell(Context context, User user, Switchable switchable) {
        super(context, R.layout.cell_user);
        spinnerContainer = (RelativeLayout)findViewById(R.id.cell_user_InfoWrapper);
        setSwitchable(switchable);
        setUser(user);
    }

    /**
     * 이 UserCell의 user 정보를 저장하고 해당 유저의 변하지 않는 정보들을 뷰에 뿌려준다.
     * 
     * @param user
     */
    public void setUser(User user) {
        this.user = user;

        ((CellProfileImageView) findViewById(R.id.profile)).setUserId(user.getId());
        ((CellProfileImageView) findViewById(R.id.profile)).showInsetBackground();
        ((TextView) findViewById(R.id.cell_user_nick)).setText(user.getNick());
        if (user.getMileage() != null) 
            ((TextView) findViewById(R.id.cell_user_point)).setText(user.getMileage().getTotalPoint()+"");
        else 
            ((TextView) findViewById(R.id.cell_user_point)).setText("0");
        ((TextView) findViewById(R.id.cell_user_area)).setText("KOREA");
        ((TextView) findViewById(R.id.cell_user_like_list)).setText(user.getLikeStoreCount()+"");
        ((TextView) findViewById(R.id.cell_user_like_list)).setOnClickListener(new LikeStoreListListener(getContext(), user));

        follow = (Button) findViewById(R.id.cell_user_follow);
        messageList = (Button) findViewById(R.id.cell_user_message_list);

        followingListener = new FollowingListener(((Identifiable) getContext()), getContext(), user, spinnerContainer) {

            @Override
            public void postUnfollowRequest() {
                UserCell.this.user.setFollowingCount(UserCell.this.user.getFollowerCount() - 1);
                refresh();
            }

            @Override
            public void postFollowRequest() {
                UserCell.this.user.setFollowingCount(UserCell.this.user.getFollowerCount() + 1);
                refresh();
            }
        };
        follow.setOnClickListener(followingListener);
        session = Session.getInstance(getContext());
        messageList.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                getContext().startActivity(new Intent(getContext(), MessageListActivity.class));
            }
        });

        refresh();
    }

    /**
     * Refresh 가능한 정보들을 refresh
     * 
     * @param user
     */
    public void refresh() {
        if (session.isLogin() && session.getCurrentUser().getId() == user.getId()) {
            follow.setVisibility(View.GONE);
            messageList.setVisibility(View.VISIBLE);
            messageList.setText(user.getReceivedMessageCount()+"");
            if (switchable != null) switchable.off();
        } else {
            follow.setText(
                    (followingListener.isExistFollowing()) ? 
                            R.string.cell_user_unfollow
                            : R.string.cell_user_follow);

            if (switchable != null) {
                if (followingListener.isExistFollowing()) {
                    switchable.on();
                } else {
                    switchable.off();
                }
            }

        }
    }

    /**
     * 
     */
    @Override
    protected Intent getIntent() {
        Intent intent = new Intent(getContext(), UserProfileActivity.class);
        intent.putExtra(UserProfileActivity.USER, (Parcelable) user);
        return intent;
    }
    
    public void setSwitchable(Switchable switchable) {
        this.switchable = switchable;
    }


    public void showLine() {
        findViewById(R.id.cell_user_line).setVisibility(View.VISIBLE);
        findViewById(R.id.cell_user_shadow).setVisibility(View.GONE);
    }

    @Override
    public void following() {
        followingListener.following();
    }
}