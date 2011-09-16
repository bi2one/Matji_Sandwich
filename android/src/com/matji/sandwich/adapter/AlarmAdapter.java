package com.matji.sandwich.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.matji.sandwich.MessageListTabActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.data.Alarm;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.User;
import com.matji.sandwich.listener.GotoPostMainAction;
import com.matji.sandwich.listener.GotoUserMainAction;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.TimeUtil;

public class AlarmAdapter extends MBaseAdapter {

    public enum AlarmType {
        Following,
        Message,
        LikePost,
        Comment,        
    }

    private Toast toast;
    private int lastReadAlarmId;
    private Drawable iconNew;

    public AlarmAdapter(Context context) {
        super(context);
        iconNew = MatjiConstants.drawable(R.drawable.icon_new);
        iconNew.setBounds(0, 0, iconNew.getIntrinsicWidth(), iconNew.getIntrinsicHeight());
        toast = new Toast(context);
        toast = Toast.makeText(context, R.string.alarm_deleted_post, Toast.LENGTH_SHORT);
    }

    public void setLastReadAlarmId(int lastReadAlarmId) {
        this.lastReadAlarmId = lastReadAlarmId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        AlarmElement alarmElement;
        Alarm alarm = (Alarm) data.get(position);
        if (convertView == null) {
            alarmElement = new AlarmElement();
            convertView = getLayoutInflater().inflate(R.layout.row_alarm, null);

            alarmElement.noticon = (ImageView) convertView.findViewById(R.id.row_alarm_notic_icon);
            //            alarmElement.sentUser = (TextView) convertView.findViewById(R.id.row_alarm_sent_user);
            alarmElement.content = (TextView) convertView.findViewById(R.id.row_alarm_content);
            alarmElement.createdAt = (TextView) convertView.findViewById(R.id.row_alarm_created_at);

            convertView.setTag(alarmElement);
        } else {
            alarmElement = (AlarmElement) convertView.getTag();
        }

        /* Set User */
        User sentUser = alarm.getSentUser();
        String type = alarm.getAlarmType();
        Post post = alarm.getPost();

        switch (AlarmType.valueOf(type)) {
        case Following:
            alarmElement.noticon.setImageResource(R.drawable.icon_notic_follow);
            alarmElement.content.setText(
                    getContent(
                            R.string.row_alarm_follow, 
                            sentUser.getNick()));
            convertView.setOnClickListener(new GotoUserMainAction(context, sentUser));
            break;
        case Comment:
            alarmElement.noticon.setImageResource(R.drawable.icon_notic_reply);
            alarmElement.content.setText(
                    getContent(
                            R.string.row_alarm_reply, 
                            sentUser.getNick()));
            if (post == null) {
                convertView.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        toast.show();                        
                    }
                });
            } else {
                convertView.setOnClickListener(new GotoPostMainAction(context, post));
            }
            
            break;
        case LikePost:
            alarmElement.noticon.setImageResource(R.drawable.icon_notic_love);
            alarmElement.content.setText(
                    getContent(
                            R.string.row_alarm_like_post, 
                            sentUser.getNick()));
            if (post == null) {
                // TODO error message alert.
                convertView.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        toast.show();
                    }
                });
            } else {
                convertView.setOnClickListener(new GotoPostMainAction(context, post));
            }
            break;
        case Message:
            alarmElement.noticon.setImageResource(R.drawable.icon_notic_notic);
            alarmElement.content.setText(
                    getContent(
                            R.string.row_alarm_message, 
                            sentUser.getNick()));
            convertView.setOnClickListener(new OnClickListener() {  // 메세지함으로 이동하는 리스너
                
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MessageListTabActivity.class);
                    context.startActivity(intent);
                }
            });
            break;
        }

        alarmElement.createdAt.setText(
                TimeUtil.parseString(
                        "yyyy-MM-dd hh:mm", 
                        TimeUtil.getDateFromCreatedAt(alarm.getCreatedAt())));
        if (lastReadAlarmId < alarm.getId()) {
            alarmElement.createdAt.setCompoundDrawables(null, null, iconNew, null);
        } else {
            alarmElement.createdAt.setCompoundDrawables(null, null, null, null);
        }
        

        return convertView;
    }
    
    private SpannableStringBuilder getContent(int resId, String nick) {
        // TODO 나중에 좀더 범용적으로 사용할 수 있게 수정...
        
        String str = String.format(
                MatjiConstants.string(resId),
                nick);
        String[] contents = str.split("##");
        SpannableStringBuilder ssb = new SpannableStringBuilder(contents[0] + contents[1]);
        ssb.setSpan(
                new ForegroundColorSpan(MatjiConstants.color(R.color.matji_red)),
                contents[0].length(),
                contents[0].length() + contents[1].length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    private class AlarmElement {
        ImageView noticon;
        //        TextView sentUser;
        TextView content;
        TextView createdAt;
    }
}