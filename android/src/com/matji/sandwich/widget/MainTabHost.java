package com.matji.sandwich.widget;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.TabHost;

import com.matji.sandwich.R;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.indicator.MainIndicator;

public class MainTabHost extends TabHost {
    private Context context;
    final HashMap<String, MainIndicator> indicators = new HashMap<String, MainIndicator>();
    
    public static final String LOGIN_TAB = "login";
    private boolean doesExistLoginTab = false;
    
    public MainTabHost(Context context) {
        super(context);
        this.context = context;
    }

    public MainTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void addTab(String specLabel, int drawableRef, int textRef, Intent content) {
        if (specLabel.equals(LOGIN_TAB)) doesExistLoginTab = true; 
        TabHost.TabSpec spec = newTabSpec(specLabel);
        indicators.put(specLabel, new MainIndicator(context, drawableRef, textRef));
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
    
    public void login(User user) {
        if (doesExistLoginTab) setTabLabel(LOGIN_TAB, user.getNick());
    }
    
    public void logout() {
        if (doesExistLoginTab) setTabLabel(LOGIN_TAB, R.string.main_tab_config);
     }
}