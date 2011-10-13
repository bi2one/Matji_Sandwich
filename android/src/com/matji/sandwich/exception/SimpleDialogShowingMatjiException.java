package com.matji.sandwich.exception;

import android.content.Context;

import com.matji.sandwich.widget.dialog.SimpleAlertDialog;

public class SimpleDialogShowingMatjiException extends MatjiException {
    /**
     * 
     */
    private static final long serialVersionUID = 7773085410903268137L;

    public SimpleDialogShowingMatjiException(int msgRef) {
        super(msgRef);
    }
    
    public void performExceptionHandling(Context context) {
        SimpleAlertDialog dialog = new SimpleAlertDialog(context, getMsg());
        dialog.show();
    }
}
