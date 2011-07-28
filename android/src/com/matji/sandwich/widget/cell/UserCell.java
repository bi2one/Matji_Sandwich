package com.matji.sandwich.widget.cell;

import com.matji.sandwich.R;
import com.matji.sandwich.SharedMatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.ProfileImageView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

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
	
	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.cell_user, this);
		
		User user = (User) SharedMatjiData.getInstance().top();
		((ProfileImageView) findViewById(R.id.profile)).setUserId(user.getId());		
	}
}
