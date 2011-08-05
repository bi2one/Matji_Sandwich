package com.matji.sandwich.widget.cell;

import com.matji.sandwich.R;
import com.matji.sandwich.UserProfileActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.ProfileImageView;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * UI 상의 유저 셀 (유저 기본 정보가 보이는 곳)
 * 
 * @author mozziluv
 *
 */
public class UserCell extends Cell {
	private User user;
	
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

	/**
	 * 이 UserCell의 user 정보를 저장하고 해당 유저의 변하지 않는 정보들을 뷰에 뿌려준다.
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;

		((ProfileImageView) getRootView().findViewById(R.id.profile)).setUserId(user.getId());
		((TextView) getRootView().findViewById(R.id.cell_user_nick)).setText(user.getNick());
		if (user.getMileage() != null) 
			((TextView) getRootView().findViewById(R.id.cell_user_point)).setText(user.getMileage().getTotalPoint()+"");
		else 
			((TextView) getRootView().findViewById(R.id.cell_user_point)).setText("0");
		((TextView) getRootView().findViewById(R.id.cell_user_area)).setText("KOREA");
		((TextView) getRootView().findViewById(R.id.cell_user_like_list)).setText("165");
		
		refresh();
	}
	
	/**
	 * Refresh 가능한 정보들을 refresh
	 * 
	 * @param user
	 */
	public void refresh() {
		Session session = Session.getInstance(getRootView().getContext());
		Button follow = (Button) getRootView().findViewById(R.id.cell_user_follow);
		Button messageList = (Button) getRootView().findViewById(R.id.cell_user_message_list);

		if (session.isLogin() && session.getCurrentUser().getId() == user.getId()) {
			follow.setVisibility(View.GONE);
			messageList.setVisibility(View.VISIBLE);
			messageList.setText("246");
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
}