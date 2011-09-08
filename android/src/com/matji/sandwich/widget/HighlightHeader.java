package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.matji.sandwich.R;

/**
 * 
 * @author mozziluv
 */
public class HighlightHeader extends LinearLayout {
	protected String title = "";
	private int count = -1;

    /**
     * Java Code 기본 생성자.
     * 
     * @param context
     * @param title {@link HighlightHeader}에서 보여 줄 타이틀
     */
    public HighlightHeader(Context context, String title) {
        super(context);
        this.title = title;
        init();
    }    
    
    /**
     * Java Code 기본 생성자.
     * 
     * @param context
     * @param title {@link HighlightHeader}에서 보여 줄 타이틀
     * @param count {@link HighlightHeader}에서 보여 줄 카운트
     */
    public HighlightHeader(Context context, String title, int count) {
        super(context);
        this.title = title;
        this.count = count;
        init();
    }

    /**
     * 기본 생성자. (XML)
     * 
     * @param context
     * @param attr
     * @param title {@link HighlightHeader}에서 보여 줄 타이틀
     */
	public HighlightHeader(Context context, AttributeSet attr) {
		super(context, attr);
		init();		
	}
	
	/**
	 * 이 {@link View}를 초기화시킨다.
	 * 생성자에서 입력받은 문자열을 TextView에 그린다.
	 */
	protected void init() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.row_highlight, this);
        ((TextView) findViewById(R.id.row_highlight_title)).setText(title);
        setCount();
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
     * 현재 타이틀 뒤에 전달받은 문자열을 추가한다.
     */
    public void appendTitleAfter(String appendString) {
        this.title = this.title + appendString;
        setTitle();
    }
    
    /**
     * 현재 타이틀 앞에 전달받은 문자열을 추가한다.
     */
    public void appendTitleBefore(String appendString) {
        this.title = appendString + this.title;
        setTitle();
    }    
	
	/**
	 * 파라미터로 전달받은 카운트를 저장하고, 현재 보여지는 카운트를 전달받은 카운트로 변경한다.
	 * 
	 * @param count 이 View에서 보여질 카운트
	 */
	public void setCount(int count) {
	    this.count = count;
	    setCount();
	}
	
    /**
     * 현재 저장된 문자열을 TextView에 저장한다.
     */
    private void setTitle() {
        ((TextView) findViewById(R.id.row_highlight_title)).setText(title);
    }   
    
    /**
     * 현재 저장된 카운트를 TextView에 저장한다.
     */
    private void setCount() {
        if (count >= 0) {
            ((TextView) findViewById(R.id.row_highlight_count)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.row_highlight_count)).setText(count+"");
        }
    }
}