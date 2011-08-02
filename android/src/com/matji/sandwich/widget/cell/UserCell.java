package com.matji.sandwich.widget.cell;

import com.matji.sandwich.R;
import com.matji.sandwich.SharedMatjiData;
import com.matji.sandwich.UserProfileActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.ProfileImageView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * UI 상의 유저 셀 (유저 기본 정보가 보이는 곳)
 * 기본적으로 유저 정보 페이지 안에 들어 있는 뷰이므로,
 * SharedMatjiData의 top에 해당 유저의 데이터 모델이 저장되어 있어야 한다.
 * 
 * @author mozziluv
 *
 */
public class UserCell extends Cell {

	/**
	 * 기본 생성자 (Java Code)
	 * 
	 * @param context
	 */
	public UserCell(Context context) {
		super(context, R.layout.cell_user);
	}
	
	public UserCell(Context context, AttributeSet attr) {
		super(context, R.layout.cell_user);
	}

	/**
	 * 데이터 초기화
	 */
	protected void init() {
		super.init();
		
		User user = (User) SharedMatjiData.getInstance().top();
		((ProfileImageView) getRootView().findViewById(R.id.profile)).setUserId(user.getId());
		((TextView) getRootView().findViewById(R.id.cell_user_nick)).setText(user.getNick());
		if (user.getMileage() != null) 
			((TextView) getRootView().findViewById(R.id.cell_user_point)).setText(user.getMileage().getTotalPoint()+"");
		else 
			((TextView) getRootView().findViewById(R.id.cell_user_point)).setText("0");
		((TextView) getRootView().findViewById(R.id.cell_user_area)).setText("KOREA");
		((TextView) getRootView().findViewById(R.id.cell_user_like_list)).setText("165");
		
		refresh(user);
	}
	
	/**
	 * Refresh 가능한 정보들을 refresh
	 * 
	 * @param user
	 */
	public void refresh(User user) {
		Session session = Session.getInstance(getRootView().getContext());
		Button follow = (Button) getRootView().findViewById(R.id.cell_user_follow);
		Button messageList = (Button) getRootView().findViewById(R.id.cell_user_message_list);

		if (session.isLogin() && session.getCurrentUser().getId() == user.getId()) {
			follow.setVisibility(View.GONE);
			messageList.setVisibility(View.VISIBLE);
			messageList.setText("246");
		}
	}
	
	@Override
	protected Class<?> getDetailPageActivity() {
		// TODO Auto-generated method stub
		return UserProfileActivity.class;
	}
}