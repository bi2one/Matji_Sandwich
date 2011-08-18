package com.matji.sandwich;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.util.Log;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.widget.RoundTabHost;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.widget.RelativeLayoutThatDetectsSoftKeyboard;
import com.matji.sandwich.widget.indicator.Indicator;
import com.matji.sandwich.widget.indicator.Checkable;

public class WritePostTabActivity extends BaseTabActivity implements OnTabChangeListener,
								     RelativeLayoutThatDetectsSoftKeyboard.Listener {
    public static final String TAB_ID_POST = "WritePostTabActivity.tab_id_post";
    public static final String TAB_ID_STORE = "WritePostTabActivity.tab_id_store";
    public static final String TAB_ID_PICTURE = "WritePostTabActivity.tab_id_picture";
    public static final String TAB_ID_TAG = "WritePostTabActivity.tab_id_tag";
    private RelativeLayoutThatDetectsSoftKeyboard mainView;
    private RoundTabHost tabHost;
    private int contentHeightWithoutKeyboard;
    private int contentHeightWithKeyboard;
    private int keyboardHeight;
    
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_write_post_tab);

	mainView = (RelativeLayoutThatDetectsSoftKeyboard)findViewById(R.id.activity_write_post_tab);
	mainView.setListener(this);
	
	tabHost = (RoundTabHost)getTabHost();
	tabHost.addLeftTab(TAB_ID_POST,
			   R.string.default_string_post,
			   new Intent(this, WritePostActivity.class));
	tabHost.addCenterCheckTab(TAB_ID_STORE,
				  R.string.default_string_store,
				  new Intent(this, WritePostStoreActivity.class));
	tabHost.addCenterCheckTab(TAB_ID_PICTURE,
				  R.string.default_string_picture,
				  new Intent(this, RankingAllListActivity.class));
	tabHost.addRightCheckTab(TAB_ID_TAG,
				 R.string.default_string_tag,
				 new Intent(this, RankingAllListActivity.class));
	tabHost.setOnTabChangedListener(this);
    }

    public void onTabChanged(String tabId) {
	View currentView = tabHost.getCurrentView();
	
	if (tabId.equals(TAB_ID_STORE)) {
	    KeyboardUtil.hideKeyboard(this);
	} else if (tabId.equals(TAB_ID_POST)) {
	    View postText = currentView.findViewById(R.id.activity_write_post_text);
	    KeyboardUtil.showKeyboard(this, postText);
	}
    }

    public void onChecked(String tabId) {
	Indicator indicator = tabHost.getIndicator(tabId);
	if (indicator instanceof Checkable) {
	    ((Checkable)indicator).setCheck(true);
	}
    }

    public void onSoftKeyboardShown(boolean isShowing) {
	if (isShowing) {
	    contentHeightWithoutKeyboard = mainView.getHeight();
	} else {
	    contentHeightWithKeyboard = mainView.getHeight();
	}

	if (keyboardHeight <= 0 && contentHeightWithoutKeyboard > 0 && contentHeightWithKeyboard > 0)
	    keyboardHeight = contentHeightWithoutKeyboard - contentHeightWithKeyboard;

	// if (!isShowing) {
	//     postFooterContentLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, keyboardHeight));

	//     postFooterContentLayout.setVisibility(View.VISIBLE);

	// } else {
	//     postFooterContentLayout.setVisibility(View.GONE);
	// }
    }

    public int getKeyboardHeight() {
	return keyboardHeight;
    }
}