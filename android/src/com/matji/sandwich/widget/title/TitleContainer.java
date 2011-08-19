package com.matji.sandwich.widget.title;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.widget.title.button.TitleButton;

/**
 * Custom Titlebar Container
 * 
 * @author mozziluv
 *
 */
public abstract class TitleContainer extends RelativeLayout {
    protected abstract boolean isExistLeftButton();
    
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
		if (isExistLeftButton()) ((ImageView) findViewById(R.id.title_container_title_line)).setVisibility(View.VISIBLE);
	}

	/**
	 * 오른쪽 끝에 버튼을 추가한다.
	 * line도 함께 추가해준다.
	 * 
	 * @param resourceId Drawable image 의 resource id
	 * @param listener Button 을 클릭 했을 때 이벤트를 처리할 OnClickListener
	 */
	protected final void addRightButton(TitleButton button) {
	    ImageView line = new ImageView(getContext());
        line.setPadding(0, 0, 0, 0);
        line.setImageResource(R.drawable.navigationbar_bg_line);
	    rightContainer.addView(line);
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
	public void setTitle(String title) {
		titleContainer.setText(title);
	}

	/**
	 * 타이틀을 지정한다.
	 *
	 * @param titleRes 타이틀로 사용할 문자열의 아이디
	 */
	public void setTitle(int titleRes) {
		titleContainer.setText(titleRes);
	}

	/**
	 * 버튼들의 Clickable을 지정한다.
	 * 
	 * @param clickable
	 */
	private void childrenClickable(boolean clickable) {
		for (int i = 0; i < rightContainer.getChildCount(); i++) {
			rightContainer.getChildAt(i).setClickable(clickable);
		}
		for (int i = 0; i < leftContainer.getChildCount(); i++) {
			leftContainer.getChildAt(i).setClickable(clickable);
		}	
	}

	/**
	 * 버튼들을 클릭하지 못하도록 설정한다.
	 */
	public void lock() {
		childrenClickable(false);
	}

	/**
	 * 버튼들을 클릭할 수 있도록 설정한다.
	 */
	public void unlock() {
		childrenClickable(true);
	}
}