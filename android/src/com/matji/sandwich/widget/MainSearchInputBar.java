package com.matji.sandwich.widget;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.search.SearchInputBar;

import android.content.Context;
import android.util.AttributeSet;

/**
 * SearchActivity에서 사용하는 SearchInputBar.
 * 
 * @author mozziluv
 *
 */
public class MainSearchInputBar extends SearchInputBar {

	public static final int STORE = 0;
	public static final int POST = 1;
	public static final int USER = 2;

	public MainSearchInputBar(Context context, AttributeSet attr) {
		super(context, attr);
		setBackgroundResource(R.drawable.tabbtn_bg);
		textField.setText("");
	}

	/**
	 * 검색 타입을 변경한다.
	 * 
	 * @param hintType 변경 할 검색 타입
	 * @param searchable 해당 검색 타입에 대한 {@link Searchable} 인터페이스 구현 클래스
	 */
	public void changeSearchType(int hintType, Searchable searchable) {
		setHintType(hintType);
		setSearchable(searchable);		
	}
	
	/**
	 * 각 타입마다 힌트를 다르게 설정.
	 * 
	 * @param hintType
	 */
	public void setHintType(int hintType){
		switch (hintType) {
		case STORE:
			textField.setHint(MatjiConstants.string(R.string.hint_search_stor_tag_menu));
			break;
		case POST:
			textField.setHint(MatjiConstants.string(R.string.hint_search_post));
			break;
		case USER:
			textField.setHint(MatjiConstants.string(R.string.hint_search_user));
			break;
		}
	}
}
