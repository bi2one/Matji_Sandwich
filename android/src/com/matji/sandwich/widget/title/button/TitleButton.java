package com.matji.sandwich.widget.title.button;

import com.matji.sandwich.util.DisplayUtil;

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
	private static final int WIDTH = DisplayUtil.PixelFromDP(40);
	private static final int HEIGHT = DisplayUtil.PixelFromDP(40);
	
	public TitleButton(Context context) {
		super(context);
		this.context = context;
		init();
		setOnClickListener(new TitleButtonClickListener());
		LayoutParams params = new LayoutParams(WIDTH, HEIGHT);
		setLayoutParams(params);
	}
	
	/**
	 * TitleButton 을 initialize 한다. 생성자에서 해야 할 일이 있는 클래스들은 오버라이드 해 작성한다.
	 */
	public void init() {}
}