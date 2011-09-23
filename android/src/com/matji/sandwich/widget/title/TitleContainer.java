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
import com.matji.sandwich.util.MatjiConstants;

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
    private ImageView titleLogo;

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
        titleLogo = (ImageView) findViewById(R.id.title_container_logo);
        if (isExistLeftButton()) ((ImageView) findViewById(R.id.title_container_title_line)).setVisibility(View.VISIBLE);
    }

    /**
     * 오른쪽 컨테이너에 뷰를 추가한다.
     * 
     * @param v 추가할 뷰
     */
    protected final void addViewInRightContainer(View v) {
        ImageView line = new ImageView(getContext());
        line.setPadding(0, 0, 0, 0);
        line.setImageResource(R.drawable.navigationbar_bg_line);

        ImageView newIcon = new ImageView(getContext());
        int newIconXPos = MatjiConstants.dimenInt(R.dimen.default_new_icon_x_position);
        int newIconYPos = MatjiConstants.dimenInt(R.dimen.default_new_icon_y_position);
        newIcon.setPadding(newIconXPos, newIconYPos, 0, 0);
        newIcon.setImageResource(R.drawable.icon_new_alert);
        newIcon.setVisibility(View.GONE);

        RelativeLayout rl = new RelativeLayout(getContext());
        rl.addView(v);
        rl.addView(line);
        rl.addView(newIcon);

        v.setTag(R.string.tag_button_wrapper, rl);
        v.setTag(R.string.tag_new_icon, newIcon);

        rightContainer.addView(rl);
    }

    /**
     * 왼쪽 컨테이너에 뷰를 추가한다.
     * 
     * @param v 추가할 뷰
     */
    protected final void addViewInLeftContainer(View v) {
        leftContainer.addView(v);
    }

    public void setLogo() {
        titleContainer.setVisibility(View.GONE);
        titleLogo.setVisibility(View.VISIBLE);
    }

    /**
     * 타이틀을 지정한다.
     * 
     * @param title 타이틀로 사용 할 문자열
     */
    public void setTitle(String title) {
        titleContainer.setText(title);
        titleContainer.setVisibility(View.VISIBLE);
        titleLogo.setVisibility(View.GONE);
    }

    /**
     * 타이틀을 지정한다.
     *
     * @param titleRes 타이틀로 사용할 문자열의 아이디
     */
    public void setTitle(int titleRes) {
        setTitle(MatjiConstants.string(titleRes));
    }

    /**
     * 버튼들의 Clickable을 지정한다.
     * 
     * @param clickable
     */
    private void setChildrenClickable(boolean clickable) {
        for (int i = 0; i < rightContainer.getChildCount(); i++) {
            rightContainer.getChildAt(i).setClickable(clickable);
        }
        for (int i = 0; i < leftContainer.getChildCount(); i++) {
            leftContainer.getChildAt(i).setClickable(clickable);
        }	
    }

    /**
     * 버튼을 제거한다.
     * 버튼과 함께 버튼 옆의 line도 제거한다.
     * 
     * @param button 제거할 버튼
     */
    public void removeRightButton(View button) {
        rightContainer.removeView((View) button.getTag(R.string.tag_button_wrapper));
    }

    public void showNewIcon(View button) {
        ((View) button.getTag(R.string.tag_new_icon)).setVisibility(View.VISIBLE);
    }

    public void dismissNewIcon(View button) {
        ((View) button.getTag(R.string.tag_new_icon)).setVisibility(View.GONE);
    }

    /**
     * 버튼들을 클릭하지 못하도록 설정한다.
     */
    public void lock() {
        setChildrenClickable(false);
    }

    /**
     * 버튼들을 클릭할 수 있도록 설정한다.
     */
    public void unlock() {
        setChildrenClickable(true);
    }
}