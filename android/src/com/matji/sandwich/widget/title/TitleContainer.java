package com.matji.sandwich.widget.title;

import com.matji.sandwich.R;
import com.matji.sandwich.widget.title.button.TitleButton;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Custom Titlebar Container
 * 
 * @author mozziluv
 *
 */
public class TitleContainer extends RelativeLayout {
	private LinearLayout leftContainer;
	private LinearLayout rightContainer;
	private TextView titleContainer;
	
	public TitleContainer(Context context) {
		super(context);
		init();
	}
	
	public TitleContainer(Context context, AttributeSet attr) {
		super(context, attr);
		init();
	}

	/**
	 * LayoutInflater 를 이용해 inflate 한다. 
	 * 
	 */
	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.title_container, this);
		
		leftContainer = (LinearLayout) findViewById(R.id.title_container_left);
		rightContainer = (LinearLayout) findViewById(R.id.title_container_right);
		titleContainer = (TextView) findViewById(R.id.title_container_text);
	}
	
	/**
	 * 오른쪽 끝에 버튼을 추가한다.
	 * 
	 * @param resourceId Drawable image 의 resource id
	 * @param listener Button 을 클릭 했을 때 이벤트를 처리할 OnClickListener
	 */
	protected final void addRightButton(TitleButton button) {
		rightContainer.addView(button);
	}

	/**
	 * 왼쪽 끝에 버튼을 추가한다.
	 * 
	 * @param resourceId Drawable image 의 resource id
	 * @param listener Button 을 클릭 했을 때 이벤트를 처리할 OnClickListener
	 */
	protected final void addLeftButton(TitleButton button) {
		leftContainer.addView(button);
	}

	/**
	 * 타이틀을 지정한다.
	 * 
	 * @param title 타이틀로 사용 할 문자열
	 */
	protected void setTitle(String title) {
		titleContainer.setText(title);
	}
	
	/**
	 * 타이틀의 Background 를 지정한다.
	 * 
	 * @param bg 타이틀의 Background 로 사용 할 Drawable 이미지
	 */
	protected void setTitleBackground(Drawable bg) {
		titleContainer.setBackgroundDrawable(bg);
	}
}