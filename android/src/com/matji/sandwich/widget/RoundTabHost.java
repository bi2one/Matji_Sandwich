package com.matji.sandwich.widget;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.matji.sandwich.widget.indicator.Indicator;
import com.matji.sandwich.widget.indicator.RoundCenterCheckIndicator;
import com.matji.sandwich.widget.indicator.RoundCenterIndicator;
import com.matji.sandwich.widget.indicator.RoundLeftCheckIndicator;
import com.matji.sandwich.widget.indicator.RoundLeftIndicator;
import com.matji.sandwich.widget.indicator.RoundRightCheckIndicator;
import com.matji.sandwich.widget.indicator.RoundRightIndicator;
import com.matji.sandwich.util.AnimationUtil;

public class RoundTabHost extends TabHost implements OnTabChangeListener {
    private static final long ANIMATION_DURATION = 300;
    private Context context;
    private String prevTabId;
    private View prevTabView;
    private boolean isAnimationOn;
    private HashMap<String, Indicator> indicatorPool;

    /**
     * TabHost기본 생성자
     *
     * @param context
     * @param attrs
     */
    public RoundTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        indicatorPool = new HashMap<String, Indicator>();

        setOnTabChangedListener(this);
        setAnimationable(false);
    }

    public void setAnimationable(boolean isAnimationOn) {
        this.isAnimationOn = isAnimationOn;
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
     * TabHost의 왼쪽 TabSpec을 추가한다.
     *
     * @param specLabel 추가할 TabSpec의 label
     * @param textRef 추가할 TabSpec에 들어갈 문자열의 참조 id
     * @param content 추가할 TabSpec이 가질 Intent
     */
    public void addLeftCheckTab(String specLabel, int textRef, Intent content) {
        addTab(new RoundLeftCheckIndicator(context, textRef), specLabel, content);
    }

    /**
     * TabHost의 가운데 TabSpec을 추가한다.
     *
     * @param specLabel 추가할 TabSpec의 label
     * @param textRef 추가할 TabSpec에 들어갈 문자열의 참조 id
     * @param content 추가할 TabSpec이 가질 Intent
     */
    public void addCenterCheckTab(String specLabel, int textRef, Intent content) {
        addTab(new RoundCenterCheckIndicator(context, textRef), specLabel, content);
    }

    /**
     * TabHost의 오른쪽 TabSpec을 추가한다.
     *
     * @param specLabel 추가할 TabSpec의 label
     * @param textRef 추가할 TabSpec에 들어갈 문자열의 참조 id
     * @param content 추가할 TabSpec이 가질 Intent
     */
    public void addRightCheckTab(String specLabel, int textRef, Intent content) {
        addTab(new RoundRightCheckIndicator(context, textRef), specLabel, content);
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
        indicatorPool.put(specLabel, indicator);
        spec.setIndicator(indicator);
        spec.setContent(content);
        addTab(spec);
    }

    public void clearAllTabs() {
        super.clearAllTabs();
        indicatorPool.clear();
    }

    public Indicator getIndicator(String specLabel) {
        return indicatorPool.get(specLabel);
    }

    /**
     * 탭이 바뀔 때, 탭뷰에 애니메이션을 걸어준다.
     *
     * @param tabId TabSpec등록시 명시한 탭의 label
     */
    public void onTabChanged(String tabId) {
        if (isAnimationOn) {
            View currentTabView = getCurrentView();
            if (prevTabId == null) {
                prevTabId = tabId;
                prevTabView = currentTabView;
                return;
            }

            // tabId가 prevTabId보다 왼쪽에 있는 탭일 때
            if (tabId.compareTo(prevTabId) < 0) {
                prevTabView.setAnimation(AnimationUtil.outToRightAnimation());
                currentTabView.setAnimation(AnimationUtil.inFromLeftAnimation());
            } else {
                prevTabView.setAnimation(AnimationUtil.outToLeftAnimation());
                currentTabView.setAnimation(AnimationUtil.inFromRightAnimation());
            }
            prevTabId = tabId;
            prevTabView = currentTabView;
        }
    }
}