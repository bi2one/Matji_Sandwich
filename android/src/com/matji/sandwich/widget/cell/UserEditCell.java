package com.matji.sandwich.widget.cell;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.data.User;

/**
 * 
 * @author mozziluv
 *
 */
public class UserEditCell extends Cell {
	private User user;

	private CellEditProfileImageView profile;
	private TextView useridText;
	private TextView usermailText;
	private Button editNickButton;

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

		profile = (CellEditProfileImageView) findViewById(R.id.edit_profile);
		useridText = (TextView) findViewById(R.id.cell_user_edit_nick);
		usermailText = (TextView) findViewById(R.id.cell_user_edit_usermail);
		editNickButton = (Button) findViewById(R.id.cell_user_edit_nick_btn);
	}

	/**
	 * 이 UserCell에 user 정보를 저장하고 해당 유저의 변하지 않는 정보들을 뷰에 뿌려준다.
	 * 
	 * @param user
	 */
	 public void setUser(User user) {
		this.user = user;
		profile.setUserId(user.getId());
		profile.showInsetBackground();
		useridText.setText(user.getUserid());
		usermailText.setText(user.getEmail());
	 }

	 /**
	  * Refresh 가능한 정보들을 refresh
	  * 
	  * @param user
	  */
	 public void refresh() {

	 }

	 /**
	  * 
	  */
	 @Override
	 protected Intent getIntent() {
		 return null;
	 }

	 public void showLine() {
		 findViewById(R.id.cell_user_line).setVisibility(View.VISIBLE);
		 findViewById(R.id.cell_user_shadow).setVisibility(View.GONE);
	 }
}