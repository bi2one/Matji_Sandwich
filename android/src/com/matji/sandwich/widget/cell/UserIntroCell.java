package com.matji.sandwich.widget.cell;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.UserProfileTabActivity;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.util.MatjiConstants;

/**
 * UI 상의 유저 인트로 셀.
 * 기본적으로 유저 정보 페이지 안에 들어 있는 뷰이므로,
 * SharedMatjiData의 top에 해당 유저의 데이터 모델이 저장되어 있어야 한다.
 * 
 * @author mozziluv
 *
 */
public class UserIntroCell extends Cell {
    private User user;
    /**
     * 기본 생성자 (Java Code)
     * 
     * @param context
     */
    public UserIntroCell(Context context) {
        super(context, R.layout.cell_user_intro);
    }

    /**
     * User 정보를 같이 전달 받을 때 사용하는 생성자.
     * 
     * @param context
     * @param user 이 Cell의 유저 데이터
     */
    public UserIntroCell(Context context, User user) {
        super(context, R.layout.cell_user_intro);
        setUser(user);
    }

    public void setUser(User user) {
        this.user = user;
        
        String intro = user.getIntro();
        if (intro.equals("")) {
            intro = MatjiConstants.string(R.string.not_exist_intro);
        }
        ((TextView) getRootView().findViewById(R.id.cell_user_intro)).setText(intro);
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
    //	/**
    //	 * Refresh 가능한 정보들을 refresh
    //	 * 
    //	 * @param user
    //	 */
    //	public void refresh(User user) {
    //		Session session = Session.getInstance(getContext());
    //		Button follow = (Button) findViewById(R.id.cell_user_follow);
    //		Button messageList = (Button) findViewById(R.id.cell_user_message_list);
    //
    //		if (session.isLogin() && session.getCurrentUser().getId() == user.getId()) {
    //			follow.setVisibility(View.GONE);
    //			messageList.setVisibility(View.VISIBLE);
    //			messageList.setText("246");
    //		} else {
    //		}
    //	}
}