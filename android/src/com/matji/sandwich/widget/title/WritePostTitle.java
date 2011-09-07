//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.View.OnClickListener;

package com.matji.sandwich.widget.title;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.matji.sandwich.R;

public class WritePostTitle extends RelativeLayout implements View.OnClickListener {
    private OnClickListener listener;
    private Button completeButton;

    public WritePostTitle(Context context) {
        super(context);
        init();
    }

    public WritePostTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.title_write_post, this, true);
        completeButton = (Button)findViewById(R.id.title_write_post_complete);
        completeButton.setOnClickListener(this);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void onClick(View v) {
        if (listener != null)
            listener.onCompleteClick();
    }

    public interface OnClickListener {
        public void onCompleteClick();
    }
}
//
//public class WritePostTitle extends CompleteTitle implements OnClickListener {
//
//    public WritePostTitle(Context context) {
//        super(context);
//    }
//    
//    public WritePostTitle(Context context, AttributeSet attr) {
//        super(context, attr);
//    }
//   
//}