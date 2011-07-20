package com.matji.sandwich.widget;

import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.AccelerateInterpolator;

import com.matji.sandwich.widget.indicator.Indicator;
import com.matji.sandwich.widget.indicator.RoundLeftIndicator;
import com.matji.sandwich.widget.indicator.RoundCenterIndicator;
import com.matji.sandwich.widget.indicator.RoundRightIndicator;

public class RoundTabHost extends TabHost implements OnTabChangeListener {
    private static final long ANIMATION_DURATION = 300;
    private Context context;
    private String prevTabId;
    private View prevTabView;

    /**
     * TabHost기본 생성자
     *
     * @param context
     * @param attrs
     */
    public RoundTabHost(Context context, AttributeSet attrs) {
	super(context, attrs);
	this.context = context;

	setOnTabChangedListener(this);
    }

    /**
     * TabHost의 왼쪽 TabSpec을 추가한다.
     *
     * @param specLabel 추가할 TabSpec의 label
     * @param textRef 추가할 TabSpec에 들어갈 문자열의 참조 id
     * @param content 추가할 TabSpec이 가질 Intent
     */
    public void addLeftTab(String specLabel, int textRef, Intent content) {
	addTab(new RoundLeftIndicator(context, textRef), specLabel, content);
    }

    /**
     * TabHost의 가운데 TabSpec을 추가한다.
     *
     * @param specLabel 추가할 TabSpec의 label
     * @param textRef 추가할 TabSpec에 들어갈 문자열의 참조 id
     * @param content 추가할 TabSpec이 가질 Intent
     */
    public void addCenterTab(String specLabel, int textRef, Intent content) {
	addTab(new RoundCenterIndicator(context, textRef), specLabel, content);
    }

    /**
     * TabHost의 오른쪽 TabSpec을 추가한다.
     *
     * @param specLabel 추가할 TabSpec의 label
     * @param textRef 추가할 TabSpec에 들어갈 문자열의 참조 id
     * @param content 추가할 TabSpec이 가질 Intent
     */
    public void addRightTab(String specLabel, int textRef, Intent content) {
	addTab(new RoundRightIndicator(context, textRef), specLabel, content);
    }

    /**
     * TabHost에 TabSpec을 추가한다.
     *
     * @param indicator TabSpec의 모양을 명시하는 Indicator
     * @param specLabel TabSpec의 label
     * @param content TabSpec이 가질 Intent
     */
    public void addTab(Indicator indicator, String specLabel, Intent content) {
	TabHost.TabSpec spec = newTabSpec(specLabel);
	spec.setIndicator(indicator);
	spec.setContent(content);
	addTab(spec);
    }

    /**
     * 오른쪽에서 나타나는 애니메이션
     *
     * @return 오른쪽에서 나타나게 하는 애니메이션 객체
     */
    private Animation inFromRightAnimation() {
	return getAccelerateAnimation(+1.0f, 0.0f, 0.0f, 0.0f);
    }

    /**
     * 왼쪽에서 나타나는 애니메이션
     *
     * @return 왼쪽에서 나타나게 하는 애니메이션 객체
     */
    private Animation inFromLeftAnimation() {
	return getAccelerateAnimation(-1.0f, 0.0f, 0.0f, 0.0f);
    }
    
    /**
     * 왼쪽으로 사라지는 애니메이션
     *
     * @return 왼쪽으로 사라지게 하는 애니메이션 객체
     */
    private Animation outToLeftAnimation() {
	return getAccelerateAnimation(0.0f, -1.0f, 0.0f, 0.0f);
    }

    /**
     * 오른쪽으로 사라지는 애니메이션
     *
     * @return 오른쪽으로 사라지게 하는 애니메이션 객체
     */
    private Animation outToRightAnimation() {
	return getAccelerateAnimation(0.0f, +1.0f, 0.0f, 0.0f);
    }
    
    /**
     * 관성이 적용된 slide애니메이션 객체를 리턴한다.
     *
     * @param fromXDelta 움직일 X좌표의 시작점
     * @param toXDelta 움직일 X좌표의 끝점
     * @param fromYDelta 움직일 Y좌표의 시작점
     * @param toYDelta 움직일 Y좌표의 끝점
     *
     * @return 관성이 적용된 slide애니메이션 객체
     */
    private Animation getAccelerateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
	Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, fromXDelta,
						     Animation.RELATIVE_TO_PARENT, toXDelta,
						     Animation.RELATIVE_TO_PARENT, fromYDelta,
						     Animation.RELATIVE_TO_PARENT, toYDelta);
	animation.setDuration(ANIMATION_DURATION);
	animation.setInterpolator(new AccelerateInterpolator());
	return animation;
    }

    public void onTabChanged(String tabId) {
	View currentTabView = getCurrentView();
	if (prevTabId == null) {
	    prevTabId = tabId;
	    prevTabView = currentTabView;
	    return;
	}

	// tabId가 prevTabId보다 왼쪽에 있는 탭일 때
	if (tabId.compareTo(prevTabId) < 0) {
	    prevTabView.setAnimation(outToRightAnimation());
	    currentTabView.setAnimation(inFromLeftAnimation());
	} else {
	    prevTabView.setAnimation(outToLeftAnimation());
	    currentTabView.setAnimation(inFromRightAnimation());
	}
	prevTabId = tabId;
	prevTabView = currentTabView;
    }
}