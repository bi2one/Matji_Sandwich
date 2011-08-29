package com.matji.sandwich;

import android.os.Bundle;
import android.content.Context;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;
import android.view.View;
import android.view.MotionEvent;
import android.graphics.drawable.ColorDrawable;
import android.text.TextWatcher;
import android.text.Editable;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.adapter.ArrayListPrintAdapter;
import com.matji.sandwich.session.SessionWritePostUtil;

import java.util.ArrayList;

public class WritePostTagActivity extends BaseActivity implements OnItemClickListener,
								  WritePostTabActivity.KeyboardListener,
								  TextWatcher {
    private Context context;
    private SessionWritePostUtil sessionUtil;
    private WritePostTabActivity parentActivity;
    private View listViewWrapper;
    private ListView listView;
    private EditText editText;
    private String[] tagList;

    public int setMainViewId() {
	return R.id.activity_write_post_tag;
    }
    
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_write_post_tag);
	
	context = getApplicationContext();
	tagList = MatjiConstants.stringArray(R.array.write_post_tags);
	parentActivity = (WritePostTabActivity)getParent();
	sessionUtil = new SessionWritePostUtil(context);

	listView = (ListView)findViewById(R.id.activity_write_post_tag_listview);
	listViewWrapper = findViewById(R.id.activity_write_post_tag_listview_wrapper);
	editText = (EditText)findViewById(R.id.activity_write_post_tag_text);

	listView.setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
	listView.setDividerHeight(1);
	listView.setAdapter(new ArrayAdapter<String>(this,
						     R.layout.row_post_tag,
						     R.id.row_post_tag_text,
						     tagList));
	listView.setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
	listView.setOnItemClickListener(this);
	editText.addTextChangedListener(this);
    }

    public void afterTextChanged(Editable s) { }
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    public void onTextChanged(CharSequence s, int start, int before, int count) {
	if(s.toString().trim().equals("")) {
	    parentActivity.onChecked(WritePostTabActivity.TAB_ID_TAG, false);
	} else {
	    parentActivity.onChecked(WritePostTabActivity.TAB_ID_TAG, true);
	}
    }

    public void onKeyboardStateChanged(boolean isShowing) {
	if (isShowing) {
	    listViewWrapper.setVisibility(View.VISIBLE);
	    listView.setOnTouchListener(new View.OnTouchListener() {
		    public boolean onTouch(View view, MotionEvent motionEvent) {
			return true;
		    }
		});
	} else {
	    listViewWrapper.setVisibility(View.GONE);
	    listView.setOnTouchListener(new View.OnTouchListener() {
		    public boolean onTouch(View view, MotionEvent motionEvent) {
			return false;
		    }
		});
	}
    }

    protected void onResume() {
	super.onResume();
	parentActivity.setKeyboardListener(this);
	KeyboardUtil.hideKeyboard(this);
    }

    protected void onPause() {
	super.onPause();
	parentActivity.removeKeyboardListener();
	sessionUtil.setTags(editText.getText().toString());
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	addTag(tagList[position]);
    }

    private void addTag(String tag) {
	String prevString = editText.getText().toString().trim();
	if (prevString.indexOf(tag) != -1) {
	    return ;
	}

	String nextString;
	
	if (prevString.equals(""))
	    nextString = tag;
	else
	    nextString = editText.getText().toString() + ", " + tag;
	
	editText.setText(nextString);
	editText.setSelection(nextString.length());
    }
}