package com.matji.sandwich.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.matji.sandwich.R;
import com.matji.sandwich.session.Session;

/**
 * Pop-up 다이얼로그.
 * CheckBox를 사용하기 위해선 CheckBox의 id를 PopupCheckBox로 한다.
 * 
 * @author mozziluv
 *
 */
public class PopupDialog extends Dialog {

    private Session session;
    private PopupListener listener;
    private String tag;
    private boolean canceledOnTouchOutside;

    public PopupDialog(Context context, String tag, int layoutRes) {
        this(context, tag, layoutRes, false, false);
    }
    
    public PopupDialog(Context context, String tag, int layoutRes, boolean isTranslucent) {
        this(context, tag, layoutRes, isTranslucent, false);
    }

    /**
     * 
     * @param context
     * @param tag 팝업을 구분하기 위한 태그
     * @param layoutRes
     * @param isTranslucent true 일 경우 배경 반투명.
     * @param canceledOnTouchOutside true 일 경우 화면 아무곳이나 터치 할 시 dismiss
     */
    public PopupDialog(Context context, String tag, int layoutRes, boolean isTranslucent, boolean canceledOnTouchOutside) {
        super(context, (isTranslucent) ? R.style.TranslucentPopup : R.style.Popup);
        this.tag = tag;
        this.canceledOnTouchOutside = canceledOnTouchOutside;
        init(layoutRes);
    }

    private void init(int layoutRes) {
        session = Session.getInstance(getContext());

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(layoutRes, null);
        setCanceledOnTouchOutside(canceledOnTouchOutside);
        layout.setOnTouchListener(new View.OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent evt) {
                if (canceledOnTouchOutside) dismiss();
                return false;
            }
        });
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layout);

        CheckBox checkBox = (CheckBox) findViewById(R.id.PopupCheckBox);
        if (!(checkBox == null)) { 
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
                    session.getPrivateUtil().setIsCheckedPopupNotShown(getTag(), isChecked);
                }
            });
        }
    }

    public String getTag() {
        return getClass()+"_"+tag;
    }

    public void setPopupListener(PopupListener listener) {
        this.listener = listener;
    }

    @Override
    public void show() {
        if (!session.getPrivateUtil().isCheckedPopupNotShown(getTag())) {
            super.show();
            if (listener != null) listener.shown();
        } else {
            dismiss();
        }
    }
    
    @Override
    public void dismiss() {
        super.dismiss();
        if (listener != null) listener.dismissed();
        session.getConcretePreferenceProvider().commit(getContext());
    }

    public interface PopupListener {
        void shown();
        void dismissed();
    }
}