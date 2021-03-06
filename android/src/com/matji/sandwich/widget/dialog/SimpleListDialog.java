package com.matji.sandwich.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class SimpleListDialog implements SimpleDialog, DialogInterface.OnClickListener {
    AlertDialog dialog;
    OnClickListener listener;

    public SimpleListDialog(Context context, String title, CharSequence[] items) {
        listener = new OnClickListener() {
            
            @Override
            public void onItemClick(SimpleDialog dialog, int position) {}
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(items, this).setCancelable(true);
        dialog = builder.create();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void onClick(DialogInterface dialog, int id) {
        cancel();
        listener.onItemClick(this, id);
    }

    public void show() {
        dialog.show();
    }

    public void cancel() {
        dialog.cancel();
    }

    public void setTitle(CharSequence title) {
        dialog.setTitle(title);
    }
    
    public void setTitle(int titleId) {
        dialog.setTitle(titleId);
    }
    
    public interface OnClickListener {
        public void onItemClick(SimpleDialog dialog, int position);
    }
}