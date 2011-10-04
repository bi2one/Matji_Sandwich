package com.matji.sandwich.widget.tag;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;

/*
 * TODO 이게 TagCloudView보다 상위 개념인듯..
 * 나중에 바꿔야 할 것 같다.......
 * 
 */
/**
 * 모든 태그를 보여주기 위한 뷰 More를 눌렀을 때, RequestNext를 하도록 한다.
 * 
 * <pre>
 * 최초 생성일	2011.10.05
 * 최종 수정일	2011.10.05
 * 최종 수정자	mozziluv
 * </pre>
 * 
 * @author mozziluv
 * @version 1.0
 * 
 */
public abstract class TagListView extends TagCloudView implements Requestable {

	private int page = 1;
	private int limit = 50;

	private HttpRequestManager manager;
	private TextView more;
	
	private boolean hasNext;

	protected abstract HttpRequest request();

	/**
	 * 기본 생성자 (XML)
	 * 
	 * @param context
	 * @param attr
	 */
	public TagListView(Context context, AttributeSet attr) {
		super(context, attr);
		init();
	}

	protected void init() {
		manager = HttpRequestManager.getInstance();
		more = new TextView(getContext());
		more.setText(R.string.tag_list_more);
		more.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT));
		more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				requestNext();
			}
		});
	}

	/**
	 * 전달받은 태그를 가지고 Tag Cloud 를 보여준다.
	 * 
	 * @param tags
	 *            이 뷰에서 보여질 태그들
	 */
	public void show(ArrayList<Tag> tags) {
		initializeView();

		if (tags.size() > 0) {
			add(tags);
		} else {
			addView(new TagView(getContext(), "태그가 존재하지 않습니다."));
		}
	}

	private void initializeView() {
		removeAllViews();
		setBackgroundDrawable(getResources().getDrawable(R.drawable.txtbox));
	}

	public void add(ArrayList<Tag> tags) {
		for (Tag tag : tags) {
			add(tag);
		}
		removeView(more);
		
		if (hasNext)
			addView(more);
	}

	public void add(Tag tag) {
		// more를 따로 add해 주어야 함.
		TagView tagView = new TagView(getContext(), tag);
		tagView.setOnClickListener(getListener(tag));
		addView(tagView);
	}

	private void initPage() {
		page = 1;
	}

	private void nextPage() {
		page++;
	}

	protected void requestNext() {
		Log.d("Matji", "requestNext()");
		manager.request(getContext(), (ViewGroup) this, request(),
				HttpRequestManager.REQUEST_NEXT, this);
		nextPage();
	}

	protected void requestReload() {
		initPage();
	}

	@Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		if (data.size() == 0 || data.size() < limit) {
			hasNext = false;
		} else {
			hasNext = true;
		}
		
		ArrayList<Tag> tags = new ArrayList<Tag>();
		for (MatjiData d : data) {
			tags.add((Tag) d);
		}

		if (tag == HttpRequestManager.REQUEST_RELOAD) {
			show(tags);
		} else if (tag == HttpRequestManager.REQUEST_NEXT) {
			add(tags);
		}
    }

	@Override
	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(getContext());
	}

	protected int getPage() {
		return page;
	}

	protected int getLimit() {
		return limit;
	}
}