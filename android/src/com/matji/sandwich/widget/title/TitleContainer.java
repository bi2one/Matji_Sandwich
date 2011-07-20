package com.matji.sandwich.widget.title;

import com.matji.sandwich.R;
import com.matji.sandwich.widget.title.button.TitleButton;

import android.content.Context;
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
	protected Context context;
	private LinearLayout leftContainer;
	private LinearLayout rightContainer;
	
	public TitleContainer(Context context) {
		super(context);
		init(context);
	}
	
	public TitleContainer(Context context, AttributeSet attr) {
		super(context, attr);
		init(context);
	}

	/**
	 * LayoutInflater 를 이용해 inflate 한다. 
	 * 
	 * @param context
	 */
	private void init(Context context) {
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.title_container, this);
		
		leftContainer = (LinearLayout) findViewById(R.id.title_container_left);
		rightContainer = (LinearLayout) findViewById(R.id.title_container_right);
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
		((TextView) findViewById(R.id.title_container_text)).setText(title);
	}
}