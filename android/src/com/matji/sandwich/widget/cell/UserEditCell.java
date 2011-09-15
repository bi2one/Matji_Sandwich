package com.matji.sandwich.widget.cell;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.matji.sandwich.ActivityStartable;
import com.matji.sandwich.R;
import com.matji.sandwich.Refreshable;
import com.matji.sandwich.UserNickEditActivity;
import com.matji.sandwich.UserProfileTabActivity;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;

/**
 * 
 * @author mozziluv
 *
 */
public class UserEditCell extends Cell implements OnClickListener {
    
    private Session session;
    
    private CellEditProfileImageView profile;
    private TextView nickText;
    private TextView useridText;
    private TextView usermailText;
    private Button editNickButton;

    private ArrayList<Refreshable> refreshables;
    
    /**
     * 기본 생성자 (Java Code)
     * 
     * @param context
     */
    public UserEditCell(Context context) {
        super(context, R.layout.cell_user_edit);
    }

    /**
     * 기본 생성자 (XML)
     * 
     * @param context
     * @param attr
     */
    public UserEditCell(Context context, AttributeSet attr) {
        super(context, R.layout.cell_user_edit);
        setId(R.id.UserEditCell);
    }

    /**
     * User 정보를 같이 전달 받을 때 사용하는 생성자.
     * 
     * @param context
     * @param user 이 Cell의 유저 데이터
     */
    public UserEditCell(Context context, User user) {
        super(context, R.layout.cell_user_edit);
        setUser(user);
    }

    @Override
    protected void init() {
        super.init();
        
        session = Session.getInstance(getContext());
        refreshables = new ArrayList<Refreshable>();

        profile = (CellEditProfileImageView) findViewById(R.id.edit_profile);
        nickText = (TextView) findViewById(R.id.cell_user_edit_nick);
        useridText = (TextView) findViewById(R.id.cell_user_edit_userid);
        usermailText = (TextView) findViewById(R.id.cell_user_edit_usermail);
        editNickButton = (Button) findViewById(R.id.cell_user_edit_nick_btn);
        editNickButton.setOnClickListener(this);
    }

    /**
     * 이 UserCell에 user 정보를 저장하고 해당 유저의 변하지 않는 정보들을 뷰에 뿌려준다.
     * 
     * @param user
     */
    public void setUser(User user) {
        profile.setUserId(user.getId());
        profile.showInsetBackground();
        nickText.setText(user.getNick());
        useridText.setText(user.getUserid());
        usermailText.setText(user.getEmail());
    }

    /**
     * Refresh 가능한 정보들을 refresh
     * 
     * @param user
     */
    public void refresh() {
        setUser(session.getCurrentUser());
        for (Refreshable refreshable : refreshables) {
            refreshable.refresh(session.getCurrentUser());
        }
    }

    /**
     * 
     */
    @Override
    public void gotoDetailPage() {
    }

    
    public void showLine() {
        findViewById(R.id.cell_user_line).setVisibility(View.VISIBLE);
        findViewById(R.id.cell_user_shadow).setVisibility(View.GONE);
    }

    public void onEditNickButtonClicked(View v) {
        Intent intent = new Intent(getContext(), UserNickEditActivity.class);
        getContext().startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == editNickButton.getId()) {
            onEditNickButtonClicked(v);
        }
    }
    
    public void addRefreshable(Refreshable refreshable) {
        refreshables.add(refreshable);
    }

}