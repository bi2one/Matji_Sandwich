package com.matji.sandwich.widget.cell;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.Refreshable;
import com.matji.sandwich.UserNickEditActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.dialog.SimpleDialog;
import com.matji.sandwich.widget.dialog.SimpleListDialog;

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

    private SimpleListDialog selectImgDialog;
    private UserEditCell.OnItemClickListener listener;

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

        final CharSequence[] items = {
                MatjiConstants.string(R.string.default_string_camera),
                MatjiConstants.string(R.string.default_string_album),
                MatjiConstants.string(R.string.default_string_cancel),
        };
        
        selectImgDialog = new SimpleListDialog(getContext(), MatjiConstants.string(R.string.cell_user_edit_img), items);
        profile = (CellEditProfileImageView) findViewById(R.id.edit_profile);
        nickText = (TextView) findViewById(R.id.cell_user_edit_nick);
        useridText = (TextView) findViewById(R.id.cell_user_edit_userid);
        usermailText = (TextView) findViewById(R.id.cell_user_edit_usermail);
        editNickButton = (Button) findViewById(R.id.cell_user_edit_nick_btn);

        
        listener = new OnItemClickListener() {
            
            @Override
            public void onCameraClicked() {
                Log.d("Matji", "onCameraClicked");
            }
            
            @Override
            public void onAlbumClicked() {
                Log.d("Matji", "onAlbumClicked");
            }
        };
        selectImgDialog.setOnClickListener(new SimpleListDialog.OnClickListener() {

            @Override
            public void onItemClick(SimpleDialog dialog, int position) {
                if (position == 0) {
                    listener.onCameraClicked();
                } else if (position == 1) {
                    listener.onAlbumClicked();
                } else if (position == 2) {
                    selectImgDialog.cancel();
                }
            }
        });
        profile.setOnClickListener(this);
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

    public void reloadProfile() {
	profile.reload();
    }

    public void setEditProfileClickListener(UserEditCell.OnItemClickListener listener) {    
        this.listener = listener;
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

    @Override
    public void gotoDetailPage() {}


    public void showLine() {
        findViewById(R.id.cell_user_line).setVisibility(View.VISIBLE);
        findViewById(R.id.cell_user_shadow).setVisibility(View.GONE);
    }

    public void onProfileClicked(View v) {
        selectImgDialog.show();
    }

    public void onEditNickButtonClicked(View v) {
        Intent intent = new Intent(getContext(), UserNickEditActivity.class);
        getContext().startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == profile.getId()) {
            onProfileClicked(v);
        } else if (v.getId() == editNickButton.getId()) {
            onEditNickButtonClicked(v);
        }
    }

    public void addRefreshable(Refreshable refreshable) {
        refreshables.add(refreshable);
    }
    
    public interface OnItemClickListener {
        public void onCameraClicked();
        public void onAlbumClicked();
    }
}