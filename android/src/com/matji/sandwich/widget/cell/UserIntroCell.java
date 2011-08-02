package com.matji.sandwich.widget.cell;

import android.content.Context;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.SharedMatjiData;
import com.matji.sandwich.UserProfileActivity;
import com.matji.sandwich.data.User;

/**
 * UI 상의 유저 인트로 셀.
 * 기본적으로 유저 정보 페이지 안에 들어 있는 뷰이므로,
 * SharedMatjiData의 top에 해당 유저의 데이터 모델이 저장되어 있어야 한다.
 * 
 * @author mozziluv
 *
 */
public class UserIntroCell extends Cell {

	/**
	 * 기본 생성자 (Java Code)
	 * 
	 * @param context
	 */
	public UserIntroCell(Context context) {
		super(context, R.layout.cell_user_intro);
		init();
	}

	/**
	 * 데이터 초기화
	 */
	protected void init() {
		super.init();
		User user = (User) SharedMatjiData.getInstance().top();
		((TextView) getRootView().findViewById(R.id.cell_user_intro)).setText(user.getIntro());
//		refresh(user);
	}

	@Override
	protected Class<?> getDetailPageActivity() {
		// TODO Auto-generated method stub
		return UserProfileActivity.class;
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