package com.matji.sandwich.widget;

import android.widget.TabHost;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.widget.indicator.Indicator;
import com.matji.sandwich.widget.indicator.RoundLeftIndicator;

public class RoundTabHost extends TabHost {
    private Context context;

    /**
     * TabHost기본 생성자
     *
     * @param context
     */
    public RoundTabHost(Context context) {
	super(context);
	this.context = context;
    }

    /**
     * TabHost기본 생성자
     *
     * @param context
     * @param attrs
     */
    public RoundTabHost(Context context, AttributeSet attrs) {
	super(context, attrs);
	this.context = context;
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
	addTab(new RoundLeftIndicator(context, textRef), specLabel, content);
	// addTab(new RoundCenterIndicator(context, textRef), specLabel, content);
    }

    /**
     * TabHost의 오른쪽 TabSpec을 추가한다.
     *
     * @param specLabel 추가할 TabSpec의 label
     * @param textRef 추가할 TabSpec에 들어갈 문자열의 참조 id
     * @param content 추가할 TabSpec이 가질 Intent
     */
    public void addRightTab(String specLabel, int textRef, Intent content) {
	addTab(new RoundLeftIndicator(context, textRef), specLabel, content);
	// addTab(new RoundRightIndicator(context, textRef), specLabel, content);
    }

    /**
     * TabHost에 TabSpec을 추가한다.
     *
     * @param indicator TabSpec의 모양을 명시하는 Indicator
     * @param specLabel TabSpec의 label
     * @param content TabSpec이 가질 Intent
     */
    private void addTab(Indicator indicator, String specLabel, Intent content) {
	TabHost.TabSpec spec = newTabSpec(specLabel);
	spec.setIndicator(indicator);
	spec.setContent(content);
	addTab(spec);
    }
}