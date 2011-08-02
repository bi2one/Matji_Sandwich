package com.matji.sandwich.widget.tag;

import java.util.ArrayList;
import java.util.Collections;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.FlowLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * 태그 클라우드를 위한 뷰.
 * 태그의 count 별로 크기, 색상 조절을 하여 보여준다.
 * 해당 뷰 내의 텍스트 들은 TagView 에서 지정된다.
 * 
 * <pre>
 * 최초 생성일	2011.07.28
 * 최종 수정일	2011.07.28 
 * 최종 수정자	mozziluv
 * </pre>
 * 
 * @author mozziluv
 * @version 1.0	
 * @link {@link TagView}
 * 
 */
public class TagCloudView extends FlowLayout {
	private final int LINE_HEIGHT = (int) MatjiConstants.dimen(R.dimen.tag_line_height);		// TagCloudView 의 각 line 별 높이

	/**
	 * TagCloudView 에서 사용 되는 TextView.
	 * Tag 를 전달 했을 때, 이 뷰에서 자동으로 처리해 Tag Cloud 를 해준다. 
	 * 
	 * @author mozziluv
	 * @version 1.0
	 */
	class TagView extends TextView {
		private final int PADDING = (int) MatjiConstants.dimen(R.dimen.default_distance);
		private final int MAX_TAG_LENGTH = 10;				// 최대 태그 글자 수
		private final int NUMBER_OF_OPTION = 5;				// color, size 등 옵션 갯수
		private final int[] TAG_TEXT_COLOR = new int[] {	// 태그 색
				MatjiConstants.color(R.color.tag_color1),
				MatjiConstants.color(R.color.tag_color2),
				MatjiConstants.color(R.color.tag_color3),
				MatjiConstants.color(R.color.tag_color4),
				MatjiConstants.color(R.color.tag_color5),
		};
		private final int[] TAG_TEXT_SIZE = new int[] {		// 태그 크기
				(int) MatjiConstants.dimen(R.dimen.text_small),
				(int) MatjiConstants.dimen(R.dimen.text_small),
				(int) MatjiConstants.dimen(R.dimen.text_medium),
				(int) MatjiConstants.dimen(R.dimen.text_large),
				(int) MatjiConstants.dimen(R.dimen.text_xlarge),
		};

		/**
		 * 태그가 존재 할 때, 태그를 넘겨주어 자동으로 옵션이 정해진 TextView 를 넘긴다.
		 * 
		 * @param context
		 * @param tag
		 */
		public TagView(Context context, Tag tag) {
			super(context);
			init(tag);
		}

		/**
		 * 태그가 존재하지 않을 때, 메세지를 출력하도록 한다.
		 * 
		 * @param context
		 * @param str
		 */
		public TagView(Context context, String str) {
			super(context);
			init(str);
		}

		/**
		 * 초기화 메소드.
		 * 기본 크기 지정 및 콘텐츠의 정렬을 실행한다.
		 * 
		 */
		protected void init() {
			setMinHeight(LINE_HEIGHT);
			setMaxHeight(LINE_HEIGHT);
			setGravity(Gravity.CENTER);
			setPadding(PADDING, PADDING, PADDING, PADDING);
		}

		/**
		 * 생성자 파라미터로 태그를 넘겼을 때, 태그 count 에 따라 색과 크기를 지정한 후 저장하고 초기화 한다.
		 * 
		 * @param tag 이 View 에서 보여 줄 Tag class
		 */
		private void init(Tag tag) {
			String shortTag = "";
			if (tag.getTag() != null)
				shortTag = getShortTag(tag.getTag().getTag());
			int length = shortTag.length();
			int color = TAG_TEXT_COLOR[getIndex(tag.getCount())];
			int size = TAG_TEXT_SIZE[getIndex(tag.getCount())];

			SpannableString ss = new SpannableString(shortTag);
			//글자색 변경
			ss.setSpan(new ForegroundColorSpan(color), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			//글자크기 변경
			ss.setSpan(new AbsoluteSizeSpan(size), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			setText(ss);
			init();
		}

		/**
		 * 생성자 파라미터로 문자열을 넘겼을 때, 해당 문자열을 저장하고 초기화한다.
		 * 
		 * @param str 이 View 에서 보여 줄 String
		 */
		protected void init(String str) {
			setText(str);
			init();
		}

		/**
		 * 태그의 글자 수가 MAX_TAG_LENGTH 보다 많을 경우 줄여준다.
		 * 
		 * @param tag 길이를 조정할 태그
		 * @return 길이가 조정 된 태그
		 */
		private String getShortTag(String tag) {
			if (tag.length() > MAX_TAG_LENGTH) {
				return tag.substring(0, MAX_TAG_LENGTH) + "...";
			} else return tag;
		}

		/**
		 * 파라미터로 전달받은 count 를 가지고 
		 * TAG_TEXT_COLOR, TAG_TEXT_SIZE 의 item 을 얻기 위한 index 를 계산해 리턴한다. 
		 * 
		 * @param count 옵션 index 를 구할 태그의 count
		 * @return count 에 맞는 index
		 */
		private int getIndex(int count) {
			if (--count < 0) count = 0;
			return (count >= NUMBER_OF_OPTION)? NUMBER_OF_OPTION-1 : count;
		}
	}

	/**
	 * 기본 생성자 (Java Code)
	 * 
	 * @param context
	 */
	public TagCloudView(Context context, ArrayList<Tag> tags) {
		super(context);
		init(tags);
	}

	/**
	 * 기본 생성자 (XML)
	 * 
	 * @param context
	 * @param attr
	 */
	public TagCloudView(Context context, AttributeSet attr) {
		super(context, attr);
	}

	/**
	 * 전달받은 태그를 가지고 Tag Cloud 를 보여준다.
	 *  
	 * @param tags 이 뷰에서 보여질 태그들
	 */
	public void init(ArrayList<Tag> tags) {
		setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_box));
		Collections.shuffle(tags);	// shuffling for tag cloud

		if (tags.size() > 0) {
			for (Tag tag : tags) {
				addView(new TagView(getContext(), tag));
			}
		} else {
			addView(new TagView(getContext(), "태그가 존재하지 않습니다."));
		}
	}

	/**
	 * 라인의 수-1개 만큼 줄을 그어준다.
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int line = getMeasuredHeight() / LINE_HEIGHT;

		Paint p = new Paint();
		p.setColor(getResources().getColor(R.color.matji_light_gray));
		for (int i = 1; i < line; i++)
			canvas.drawLine(0, (LINE_HEIGHT+1)*i, getMeasuredWidth(), (LINE_HEIGHT+1)*i, p);

		super.onDraw(canvas);
	}
}