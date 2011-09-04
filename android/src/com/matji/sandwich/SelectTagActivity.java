package com.matji.sandwich;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;
import android.view.View;
import android.view.MotionEvent;
import android.graphics.drawable.ColorDrawable;

import com.matji.sandwich.widget.RelativeLayoutThatDetectsSoftKeyboard;
import com.matji.sandwich.widget.title.CompletableTitle;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.util.adapter.ArrayListPrintAdapter;

import java.util.ArrayList;

public class SelectTagActivity extends BaseActivity implements OnItemClickListener,
							       RelativeLayoutThatDetectsSoftKeyboard.Listener,
							       CompletableTitle.Completable {
    public static final String DATA_TAGS = "SelectTagActivity.tags";
    private Context context;
    private RelativeLayoutThatDetectsSoftKeyboard mainView;
    private View listViewWrapper;
    private ListView listView;
    private EditText editText;
    private String[] tagList;
    private CompletableTitle titleBar;
    private Intent lastIntent;

    public int setMainViewId() {
	return R.id.activity_select_tag;
    }
    
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_tag);
	
	context = getApplicationContext();
	tagList = MatjiConstants.stringArray(R.array.select_tags);

	mainView = (RelativeLayoutThatDetectsSoftKeyboard)getMainView();
	mainView.setListener(this);
	
	titleBar = (CompletableTitle)findViewById(R.id.activity_select_title);
	listView = (ListView)findViewById(R.id.activity_select_tag_listview);
	listViewWrapper = findViewById(R.id.activity_select_tag_listview_wrapper);
	editText = (EditText)findViewById(R.id.activity_select_tag_text);

	listView.setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
	listView.setDividerHeight(1);
	listView.setAdapter(new ArrayAdapter<String>(this,
						     R.layout.row_post_tag,
						     R.id.row_post_tag_text,
						     tagList));
	listView.setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
	listView.setOnItemClickListener(this);
	titleBar.setTitle(R.string.select_tag_title);
	titleBar.setCompletable(this);
	listView.requestFocus();

	lastIntent = getIntent();
	String lastTags = lastIntent.getStringExtra(DATA_TAGS);
	if (lastTags != null) {
	    editText.setText(lastTags);
	}
    }

    public void onSoftKeyboardShown(boolean isShowing) {
	if (isShowing) {
	    listViewWrapper.setVisibility(View.VISIBLE);
	    listView.setOnTouchListener(new View.OnTouchListener() {
		    public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
			    KeyboardUtil.hideKeyboard(SelectTagActivity.this);
			}
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

    public void complete() {
	String tags = editText.getText().toString();
	Intent result = new Intent();
	result.putExtra(DATA_TAGS, tags);
	setResult(RESULT_OK, result);
	finish();
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