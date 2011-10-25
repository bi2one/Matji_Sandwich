package com.matji.sandwich.widget.dialog;

import android.content.Context;
import android.view.View;

import com.matji.sandwich.R;

//package com.matji.sandwich.widget.dialog;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.Window;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//
//import com.matji.sandwich.R;
//import com.matji.sandwich.session.Session;
//import com.matji.sandwich.session.SessionPrivateUtil;
//
//public class BrandingDialog extends Dialog {
//
//    private BrandingDialogListener listener;
//    
//    public BrandingDialog(Context context, int theme) {
//        super(context, theme);
//        init();
//    }
//
//    public BrandingDialog(Context context) {
//        super(context, R.style.Popup);
//        init();
//    }
//    
//    private void init() {
//        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View layout = inflater.inflate(R.layout.dialog_branding, null);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(layout);
//        findViewById(R.id.dialog_branding_close_btn).setOnClickListener(new View.OnClickListener() {
//            
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//        
//        ((CheckBox) findViewById(R.id.dialog_branding_checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            private SessionPrivateUtil privateUtil = Session.getInstance(getContext()).getPrivateUtil();
//            
//            @Override
//            public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
//                privateUtil.setIsCheckedPopupNotShown("BrandingDialog", isChecked);
//            }
//        });
//    }    
//     
//    public void setBrandingPopupListener(BrandingDialogListener listener) {
//        this.listener = listener;
//    }
//    
//    @Override
//    public void show() {
//        super.show();
//        listener.shown();        
//    }
//    
//    @Override
//    public void dismiss() {
//        super.dismiss();
//        listener.dismissed();
//        Session.getInstance(getContext()).getConcretePreferenceProvider().commit(getContext());
//    }
//    
//    public interface BrandingDialogListener {
//        void shown();
//        void dismissed();
//    }
//}


public class BrandingDialog extends PopupDialog {

    public BrandingDialog(Context context, String tag) {
        super(context, tag, R.layout.dialog_branding, false);
        init();
    }
    
    private void init() {
        View checkBox = findViewById(R.id.PopupCheckBox);
        checkBox.setMinimumWidth(checkBox.getMeasuredWidth()+20);
        
        findViewById(R.id.dialog_branding_close_btn).setOnClickListener(new View.OnClickListener() {
            
          @Override
          public void onClick(View v) {
              dismiss();
          }
      });       
    }
}