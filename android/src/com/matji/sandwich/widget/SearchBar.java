package com.matji.sandwich.widget;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.matji.sandwich.R;
import com.matji.sandwich.Searchable;
import com.matji.sandwich.util.KeyboardUtil;

public class SearchBar extends LinearLayout implements OnClickListener, OnEditorActionListener {
	private Context context;
	private EditText searchField;
	private Searchable searchableView;
	
	public SearchBar(Context context, Searchable searchableView) {
		super(context);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.cell_search, this);
		this.searchableView = searchableView;
		searchField = (EditText) getRootView().findViewById(R.id.search_field);
		getRootView().findViewById(R.id.search_btn).setOnClickListener(this);
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		if ((actionId == EditorInfo.IME_ACTION_DONE) ||
				(event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
			search(searchField.getText().toString().trim());
		}		
		return false;
	}

	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.search_btn:
			search(searchField.getText().toString().trim());
			break;
		}
	}

	private void search(String keyword) {
		if (keyword.equals("")) {
			Toast.makeText(context, R.string.default_string_writing_content, Toast.LENGTH_SHORT).show();
		} else {
			KeyboardUtil.hideKeyboard((Activity)context);
			searchableView.search(keyword);
		}
	}
}