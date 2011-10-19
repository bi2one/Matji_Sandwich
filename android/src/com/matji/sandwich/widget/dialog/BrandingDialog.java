package com.matji.sandwich.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.matji.sandwich.R;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.SessionPrivateUtil;

public class BrandingDialog extends Dialog {

    private BrandingDialogListener listener;
    
    public BrandingDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    public BrandingDialog(Context context) {
        super(context);
        init();
    }
    
    private void init() {
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
            
            ((CheckBox) dialog.findViewById(R.id.dialog_branding_checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                private SessionPrivateUtil privateUtil = Session.getInstance(context).getPrivateUtil();
                
                @Override
                public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
                    privateUtil.setIsCheckedBrandingNotShown(isChecked);
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
        super.show();
        listener.shown();        
    }
    
    @Override
    public void dismiss() {
        super.dismiss();
        listener.dismissed();
        Session.getInstance(getContext()).getConcretePreferenceProvider().commit(getContext());
    }
    
    public interface BrandingDialogListener {
        void shown();
        void dismissed();
    }
}