package com.matji.sandwich.widget.cell;

import com.matji.sandwich.R;
import com.matji.sandwich.SharedMatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.ProfileImageView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * UI 상의 유저 셀 (유저 기본 정보가 보이는 곳)
 * 기본적으로 유저 정보 페이지 안에 들어 있는 뷰이므로,
 * SharedMatjiData의 top에 해당 유저의 데이터 모델이 저장되어 있어야 한다.
 * 
 * @author mozziluv
 *
 */
public class UserCell extends RelativeLayout {

	/**
	 * 기본 생성자 (Java Code)
	 * 
	 * @param context
	 */
	public UserCell(Context context) {
		super(context);
		init();
	}

	/**
	 * 기본 생성자 (XML)
	 * 
	 * @param context
	 * @param attr
	 */
	public UserCell(Context context, AttributeSet attr) {
		super(context, attr);
		init();
	}
	
	/**
	 * 데이터 초기화
	 */
	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		inflater.inflate(R.layout.cell_user, this);
		
		User user = (User) SharedMatjiData.getInstance().top();
		((ProfileImageView) findViewById(R.id.profile)).setUserId(user.getId());
		((TextView) findViewById(R.id.cell_user_nick)).setText(user.getNick());
		((TextView) findViewById(R.id.cell_user_point)).setText(user.getMileage().getTotalPoint()+"");
		((TextView) findViewById(R.id.cell_user_area)).setText("KOREA");
		((TextView) findViewById(R.id.cell_user_like_list)).setText("165");
		refresh(user);
	}
	
	/**
	 * Refresh 가능한 정보들을 refresh
	 * 
	 * @param user
	 */
	public void refresh(User user) {
		Session session = Session.getInstance(getContext());
		Button follow = (Button) findViewById(R.id.cell_user_follow);
		Button messageList = (Button) findViewById(R.id.cell_user_follow);

		if (session.isLogin() && session.getCurrentUser().getId() == user.getId()) {
			follow.setVisibility(View.GONE);
			messageList.setVisibility(View.VISIBLE);
			messageList.setText("246");
		} else {
		}
	}
}