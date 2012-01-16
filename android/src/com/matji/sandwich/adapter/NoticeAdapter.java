package com.matji.sandwich.adapter;

import java.util.HashMap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Notice;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.TimeUtil;

public class NoticeAdapter extends MBaseAdapter {

    private HashMap<Integer, Boolean> hasFoldedMap;

    private int lastReadNoticeId = 0;
    private Drawable iconNew;

    public NoticeAdapter(Context context) {
        super(context);
        this.context = context;
        hasFoldedMap = new HashMap<Integer, Boolean>();
        iconNew = MatjiConstants.drawable(R.drawable.icon_new);
        iconNew.setBounds(0, 0, iconNew.getIntrinsicWidth(), iconNew.getIntrinsicHeight());
    }

    public void setLastReadNoticeId(int lastReadNoticeId) {
        this.lastReadNoticeId = lastReadNoticeId;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final NoticeElement noticeElement;
        Notice notice = (Notice) data.get(position);

        if (convertView == null) {
            noticeElement = new NoticeElement();
            convertView = getLayoutInflater().inflate(R.layout.row_notice, null);

            noticeElement.subjectList = (TextView) convertView.findViewById(R.id.row_notice_subject_list);
            noticeElement.createdAtList = (TextView) convertView.findViewById(R.id.row_notice_created_at_list);
            noticeElement.subject = (TextView) convertView.findViewById(R.id.row_notice_subject);
            noticeElement.createdAt = (TextView) convertView.findViewById(R.id.row_notice_created_at);
            noticeElement.notice = (TextView) convertView.findViewById(R.id.row_notice_notice);
            noticeElement.subjectWrapper = (View) convertView.findViewById(R.id.row_notice_subject_wrapper).getParent();
            noticeElement.flow = (ImageView) noticeElement.subjectWrapper.findViewById(R.id.row_notice_flow); 
            noticeElement.noticeWrapper = noticeElement.subjectWrapper.findViewById(R.id.row_notice_notice_wrapper);

            convertView.setTag(noticeElement);

        } else {
            noticeElement = (NoticeElement) convertView.getTag();
        }

        noticeElement.subjectWrapper.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                folding(position, noticeElement);
                dismissNew(noticeElement.createdAtList);
            }
        });

        noticeElement.subjectList.setText(notice.getSubject());

        String createdAt = TimeUtil.parseString(
                "yyyy-MM-dd hh:mm", 
                TimeUtil.getDateFromCreatedAt(notice.getCreatedAt()));

        noticeElement.createdAtList.setText(createdAt);

        if (lastReadNoticeId == 0) {
            if (position == 0) showNew(noticeElement.createdAtList);
            else dismissNew(noticeElement.createdAtList);
        } else {
            if (lastReadNoticeId < notice.getId()) {
                showNew(noticeElement.createdAtList);
            } else {
            	dismissNew(noticeElement.createdAtList);
            }
        }

        noticeElement.subject.setText(notice.getSubject());
        noticeElement.createdAt.setText(createdAt);
        noticeElement.notice.setText(notice.getContent());
        if (hasFolded(position)) {
            fold(noticeElement);
        } else {
            unfold(noticeElement);
        }

        return convertView;
    }

    /**
     * 접혀 있으면 펼치고, 펼쳐져 있으면 접고...
     * 
     * @param position 접고 펼칠 아이템의 위치
     * @param holder 조작할 뷰홀더
     */
    public void folding(int position, NoticeElement holder) {
        if (hasFolded(position)) {
            unfold(position, holder);
        } else {
            fold(position, holder);
        }
    }

    /**
     * 해당 위치의 아이템이 접혀져 있는지 맵에서 찾아본 후 결과 값 리턴
     * 
     * @param position 접혀져 있는 지 확인 할 아이템의 위치
     * @return 졉혀져 있을 때 TRUE
     */
    public boolean hasFolded(int position) {
        if (hasFoldedMap.get(position) == null) {
            hasFoldedMap.put(position, true);
        }

        return hasFoldedMap.get(position);
    }

    public void unfold(int position, NoticeElement holder) {
        hasFoldedMap.put(position, false);
        unfold(holder);
    }

    public void unfold(NoticeElement holder) {
        holder.flow.setImageResource(R.drawable.icon_flow_bottom);
        holder.noticeWrapper.setVisibility(View.VISIBLE);
    }

    public void fold(int position, NoticeElement holder) {
        hasFoldedMap.put(position, true);
        fold(holder);
    }

    public void fold(NoticeElement holder) {
        holder.flow.setImageResource(R.drawable.icon_flow);
        holder.noticeWrapper.setVisibility(View.GONE);
    }

    private void showNew(TextView v) {
         v.setCompoundDrawables(null, null, iconNew, null);
    }
 
    private void dismissNew(TextView v) {
         v.setCompoundDrawables(null, null, null, null);
    }
    
    private class NoticeElement {
        TextView subjectList;
        TextView createdAtList;
        TextView subject;
        TextView createdAt;
        TextView notice;
        ImageView flow;
        View noticeWrapper;
        View subjectWrapper;
    }
}