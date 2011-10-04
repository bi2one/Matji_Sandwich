package com.matji.sandwich.widget;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.TabHost;

import com.matji.sandwich.R;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.Session.LoginListener;
import com.matji.sandwich.session.Session.LogoutListener;
import com.matji.sandwich.widget.indicator.MainIndicator;

public class MainTabHost extends TabHost implements LoginListener, LogoutListener {
    final HashMap<String, MainIndicator> indicators = new HashMap<String, MainIndicator>();

    public static final String LOGIN_TAB = "MainTabHost.login_tab";

    public MainTabHost(Context context) {
        super(context);
        init();
    }

    public MainTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        Session.getInstance(getContext()).addLoginListener(this);
        Session.getInstance(getContext()).addLogoutListener(this);

    }

    public void addTab(String specLabel, int drawableRef, int textRef, Intent content) {
        TabHost.TabSpec spec = newTabSpec(specLabel);
        indicators.put(specLabel, new MainIndicator(getContext(), drawableRef, textRef));
        spec.setIndicator(indicators.get(specLabel));
        spec.setContent(content);
        addTab(spec);
    }

    public void setTabLabel(String specLabel, int textRef) {
        indicators.get(specLabel).setLabel(textRef);
    }

    public void setTabLabel(String specLabel, String text) {
        indicators.get(specLabel).setLabel(text);
    }

    @Override
    public void preLogin() {}

    @Override
    public void postLogin() {
        setTabLabel(LOGIN_TAB, Session.getInstance(getContext()).getCurrentUser().getNick());
    }

    @Override
    public void preLogout() {}

    @Override
    public void postLogout() {
        setTabLabel(LOGIN_TAB, R.string.main_tab_config);
    }

    public void setAlarmCount(int count) {
        indicators.get(LOGIN_TAB).setCount(count);
    }
}