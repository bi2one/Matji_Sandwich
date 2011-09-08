package com.matji.sandwich.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.matji.sandwich.R;

/**
 * 
 * @author mozziluv
 */
public class SubtitleHeader extends LinearLayout {
	protected String title = "";
	
    /**
     * Java Code 기본 생성자.
     * 
     * @param context
     * @param title {@link SubtitleHeader}에서 보여 줄 타이틀
     */
    public SubtitleHeader(Context context, String title) {
        super(context);
        this.title = title;
        init();
    }
	
	/**
	 * 이 {@link View}를 초기화시킨다.
	 * 생성자에서 입력받은 문자열을 TextView에 그린다.
	 */
	protected void init() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.header_subtitle, this);
        ((TextView) findViewById(R.id.header_subtitle)).setText(title);
	}
	
	/**
	 * 파라미터로 전달받은 문자열을 저장하고, 현재 보여지는 문자열을 전달받은 문자열로 변경한다.
	 * 
	 * @param title 이 View에서 보여질 문자열
	 */
	public void setTitle(String title) {
	    this.title = title;
	    setTitle();
	}
   
    /**
     * 현재 저장된 문자열을 TextView에 저장한다.
     */
    private void setTitle() {
        ((TextView) findViewById(R.id.row_highlight_title)).setText(title);
    }   
}