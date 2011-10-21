package com.matji.sandwich.widget.dialog;

import android.content.Context;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;

public class SimpleNotificationPopup extends PopupDialog {

    public SimpleNotificationPopup(Context context, String tag, CharSequence content) {
        super(context, tag, R.layout.dialog_simple_popup, true, true);
        init(content);
    }
    
    public SimpleNotificationPopup(Context context, String tag, int contentRes) {
        this(context, tag, MatjiConstants.string(contentRes));        
    }
    
    public SimpleNotificationPopup(Context context, String tag, String content) {
        this(context, tag, (CharSequence) content);
    }
    
    private void init(CharSequence content) {
        ((TextView) findViewById(R.id.dialog_simple_popup_title)).setText(R.string.default_string_notification);
        ((TextView) findViewById(R.id.dialog_simple_popup_content)).setText(content);
    }
}