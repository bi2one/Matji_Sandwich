package com.matji.sandwich.listener;

import com.matji.sandwich.UserMainActivity;
import com.matji.sandwich.data.User;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 클릭 시 UserMainActivity로 이동하도록 하는 클릭 리스너.
 * 
 * @author mozziluv
 *
 */
public class GotoUserMainAction implements OnClickListener {

	private Context context;
	private User user;
	
	/**
	 * 기본 생성자
	 * 
	 * @param context
	 * @param user UserMainActivity에서 사용 할 User 정보
	 */
	public GotoUserMainAction(Context context, User user) {
		this.context = context;
		this.user = user;
	}

	/**
	 * 해당 리스너의 {@link User} 정보를 변경한다.
	 * 
	 * @param user 변경 할 {@link User} 정보
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * 현재 저장 된 {@link User}의 {@link UserMainActivity}로 이동한다.
	 */
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(context, UserMainActivity.class);
		intent.putExtra(UserMainActivity.USER, (Parcelable) user);
		context.startActivity(intent);
	}
}
