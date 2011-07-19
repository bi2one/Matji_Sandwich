package com.matji.sandwich.widget;

import com.matji.sandwich.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleContainer extends RelativeLayout {
	private Context context;
	private LinearLayout leftContainer;
	private LinearLayout rightContainer;
	
	
	/**
	 * 코드 상에서 호출하는 생성자
	 * 
	 * @param context
	 */	
	public TitleContainer(Context context) {
		super(context);
		init(context);
	}
	
	/**
	 * xml 에서 정의시에 호출되는 생성자
	 * 
	 * @param context
	 * @param attr
	 */
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
	 * 파라미터로 전달받은 resource id를 이용해 ImageButton 을 생성하고, listener를 달아 리턴한다.
	 * 
	 * @param resourceId Button 의 이미지로 사용할 drawable resource id
	 * @param listener Button 을 클릭 했을 때 이벤트를 처리할 OnClickListener
	 * @return 생성된 ImageButton
	 */
	private ImageButton createButton(int resourceId, OnClickListener listener) {
		ImageButton button = new ImageButton(context);
		button.setImageResource(resourceId);
		button.setOnClickListener(listener);
		button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_title_bg));
		
		return button;
	}
	
	/**
	 * 오른쪽 끝에 버튼을 추가한다.
	 * 
	 * @param resourceId Drawable image 의 resource id
	 * @param listener Button 을 클릭 했을 때 이벤트를 처리할 OnClickListener
	 */
	public void addRightButton(int resourceId, OnClickListener listener) {
		rightContainer.addView(createButton(resourceId, listener));
	}

	/**
	 * 왼쪽 끝에 버튼을 추가한다.
	 * 
	 * @param resourceId Drawable image 의 resource id
	 * @param listener Button 을 클릭 했을 때 이벤트를 처리할 OnClickListener
	 */
	public void addLeftButton(int resourceId, OnClickListener listener) {
		leftContainer.addView(createButton(resourceId, listener));
	}

	/**
	 * 타이틀을 지정한다.
	 * 
	 * @param title 타이틀로 사용 할 문자열
	 */
	public void setTitle(String title) {
		((TextView) findViewById(R.id.title_container_text)).setText(title);
	}
}