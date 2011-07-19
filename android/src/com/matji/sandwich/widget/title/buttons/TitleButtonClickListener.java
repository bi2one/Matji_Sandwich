package com.matji.sandwich.widget.title.buttons;

import android.view.View;

/**
 * 타이틀바 버튼 클릭 이벤트 처리 리스너.
 * TitleButton 은 TitleItem 을 구현하므로 onClick(View v) 에서는 onTitleItemClicked() 를 호출하고,
 * TitleButton 에서 각 버튼의 행동을 구현한다.  
 * 
 * @author mozziluv
 *
 */
public class TitleButtonClickListener implements View.OnClickListener {

	@Override
	public void onClick(View v) {
		((TitleItem) v).onTitleItemClicked();
	}
}
