package com.matji.sandwich.widget.title.button;

import com.matji.sandwich.R;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;

/**
 * 타이틀바에 추가되는 모든 버튼들은 이 클래스를 확장한다.
 * 
 * @author mozziluv
 *
 */
public abstract class TitleButton extends ImageButton implements TitleItem {
	protected Context context;
	private int width;
	private int height;
	
	public TitleButton(Context context) {
		super(context);
		this.context = context;
		width = getResources().getDimensionPixelSize(R.dimen.title_container_height);
		height = getResources().getDimensionPixelSize(R.dimen.title_container_height);
		
		init();
		setOnClickListener(new TitleButtonClickListener());
		LayoutParams params = new LayoutParams(width, height);
		setLayoutParams(params);
	}
	
	/**
	 * TitleButton 을 initialize 한다. 생성자에서 해야 할 일이 있는 클래스들은 오버라이드 해 작성한다.
	 */
	public void init() {}
}