package com.matji.sandwich.widget.title.button;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;

/**
 * 타이틀바에 추가되는 모든 버튼들은 이 클래스를 확장한다.
 * 
 * @author mozziluv
 *
 */
public abstract class TitleImageButton extends ImageButton implements TitleItem {
	protected Context context;
	private int width;
	private int height;
	
	public TitleImageButton(Context context) {
		super(context);
		this.context = context;
		width = MatjiConstants.dimenInt(R.dimen.title_container_btn_min_width);
		height = MatjiConstants.dimenInt(R.dimen.title_container_height);
		
		init();
		setOnClickListener(new TitleButtonClickListener());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
		setLayoutParams(params);
		setPadding(0, 0, 0, 0);
		setBackgroundDrawable(null);
	}
	
	/**
	 * TitleButton 을 initialize 한다. 생성자에서 해야 할 일이 있는 클래스들은 오버라이드 해 작성한다.
	 */
	protected void init() {}
}