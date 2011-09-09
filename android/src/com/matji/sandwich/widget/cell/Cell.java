package com.matji.sandwich.widget.cell;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 
 * @author mozziluv
 */
public abstract class Cell extends RelativeLayout {
	protected abstract Intent getIntent();
	private int layout_id;
	/**
	 * 기본 생성자(Java Code)
	 * 
	 * @param context
	 * @param id root layout id
	 */
	public Cell(Context context, int id) {
		super(context);
		this.layout_id = id;
		init();
	}

	public Cell(Context context, AttributeSet attr, int id) {
		super(context, attr);
		this.layout_id = id;
		init();		
	}
	
	/**
	 * 이 ViewContainer가 클릭 가능하도록 설정하고,
	 * OnClickListener를 설정한다.
	 * Cell은 DetailPage로 보내주는 역할을 한다.
	 */
	protected void init() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(layout_id, this);
		setClickable(true);
		setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				gotoDetailPage();
			}
		});
	}

	/**
	 * DetailPageActivity를 실행하는 메소드.
	 */
	public void gotoDetailPage() {
	    getRootView().getContext().startActivity(getIntent());
	}
}
