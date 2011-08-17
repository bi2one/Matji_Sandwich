package com.matji.sandwich.widget.search;

import com.matji.sandwich.R;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.InputBar;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

/**
 * 검색바 최상위 클래스.
 * 
 * @author mozziluv
 *
 */
public class SearchInputBar extends InputBar {

	private Searchable searchable;
	private Toast toast;
	private boolean isShowing;
	private boolean hasSearched = false;
	private View transparentView;
	
	public SearchInputBar(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	@Override
	protected void init() {
		super.init();
		toast = Toast.makeText(getContext(), R.string.writing_content_search, Toast.LENGTH_SHORT);
		confirmButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				textField.setText("");
			}
		});
		transparentView = createTransparentView();
		showKeyboard();
		setKeyboardState(true);
	}
	
	@Override
	protected void setTextFieldAttribute() {
		textField.setSingleLine();
		// 소프트 키보드를 검색 모드로 지정
		textField.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		textField.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int action, KeyEvent e) {
				if ((action == EditorInfo.IME_ACTION_DONE) ||
						(e != null && e.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					search();
				}
				return false;
			}
		});		
		textField.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus && isShowing) {
					if (hasSearched) {
						KeyboardUtil.hideKeyboard((Activity) getContext());
						hasSearched = false;
					} else {
						showKeyboard();
					}
				}
			}
		});
	}

	/**
	 * 키보드가 보이거나 숨겨졌을 때,
	 * 플래그를 변경하고 취소 버튼 현재 상태에 맞춰 보여주거나 숨긴다.
	 * 
	 * @param isShowing 키보드 상태
	 */
	public void setKeyboardState(boolean isShowing) {
		this.isShowing = isShowing;
		confirmButton.setVisibility((isShowing) ? View.VISIBLE : View.GONE);
		transparentView.setVisibility((isShowing) ? View.VISIBLE : View.GONE);
	}
	
	@Override
	protected void setConfirmButtonAttribute() {
		confirmButton.setText(MatjiConstants.string(R.string.default_string_cancel));
	}

	/**
	 * textField의 힌트를 파라미터로 전달받은 문자열로 지정한다.
	 * @param hint
	 */
	public void setHint(String hint) {
		textField.setHint(hint);
	}
	
	/**
	 * textField의 힌트를 파라미터로 전달받은 리소스로 지정한다.
	 * @param hintId
	 */
	public void setHint(int hintId) {
		textField.setHint(hintId);
	}

	/**
	 * search 메소드를 가진 {@link Searchable} 객체를 저장.
	 * 
	 * @param searchable
	 */
	public void setSearchable(Searchable searchable) {
		this.searchable = searchable;
	}	

	/**
	 * 현재 textField에 존재하는 문자열을 이용해 검색.
	 */
	public void search() {
		String keyword = getText().trim();

		if (keyword.equals("")) {
			toast.show();
		} else {
			searchable.search(keyword);
			hasSearched = true;
		}
	}
	
	/**
	 * 키보드를 보여주는 메소드.
	 * (제대로 동작하지 않아 Handler를 이용)
	 */
	private void showKeyboard() {
		Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			public void run() {
				textField.requestFocus();
				KeyboardUtil.showKeyboard((Activity) getContext(), textField);
			}
		}, 0);
	}
	
	public void setSearchView(FrameLayout searchViewWrapper) {
		searchViewWrapper.addView(transparentView);	
	}
	
	private View createTransparentView() {
		View transparentView = new View(getContext());
		transparentView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		transparentView.setBackgroundColor(MatjiConstants.color(R.color.matji_translucent));
		transparentView.setVisibility(View.GONE);
		transparentView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				return true;
			}
		});
		
		return transparentView;
	}
	
	public interface Searchable {
	    public void search(String keyword);
	}
}