package com.matji.sandwich.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.matji.sandwich.R;

public class BrandingDialog extends Dialog {

    private BrandingDialogListener listener;
    
    public BrandingDialog(Context context, int theme) {
        super(context, theme);
    }

    public BrandingDialog(Context context) {
        super(context);
    }

    public static class Builder {

        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Create the branding dialog
         */
        public BrandingDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final BrandingDialog dialog = new BrandingDialog(context, R.style.Branding);
            View layout = inflater.inflate(R.layout.dialog_branding, null);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(layout);
            dialog.findViewById(R.id.dialog_branding_close_btn).setOnClickListener(new View.OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            
            return dialog;
        }
    }
    
    public void setBrandingPopupListener(BrandingDialogListener listener) {
        this.listener = listener;
    }
    
    @Override
    public void show() {
        // TODO Auto-generated method stub
        super.show();
        listener.shown();        
    }
    
    @Override
    public void dismiss() {
        // TODO Auto-generated method stub
        super.dismiss();
        listener.dismissed();
    }
    
    public interface BrandingDialogListener {
        void shown();
        void dismissed();
    }
}