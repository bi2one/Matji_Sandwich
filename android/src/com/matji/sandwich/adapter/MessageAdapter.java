package com.matji.sandwich.adapter;

import java.util.HashMap;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Message;
import com.matji.sandwich.listener.GotoUserMainAction;
import com.matji.sandwich.util.TimeUtil;

public class MessageAdapter extends MBaseAdapter {
    
    private HashMap<Integer, Boolean> isFoldMap;
    
    public MessageAdapter(Context context) {
        super(context);
        this.context = context;
        isFoldMap = new HashMap<Integer, Boolean>();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        MessageElement messageElement;
        Message message = (Message) data.get(position);

        if (convertView == null) {
            messageElement = new MessageElement();
            convertView = getLayoutInflater().inflate(R.layout.row_message, null);

            messageElement.subjectList = (TextView) convertView.findViewById(R.id.row_message_subject_list);
            messageElement.nick = (TextView) convertView.findViewById(R.id.row_message_nick);
            messageElement.createdAtList = (TextView) convertView.findViewById(R.id.row_message_created_at_list);
            messageElement.createdAt = (TextView) convertView.findViewById(R.id.row_message_created_at);
            messageElement.message = (TextView) convertView.findViewById(R.id.row_message_message);
            convertView.setTag(messageElement);

            convertView.findViewById(R.id.row_message_subject_wrapper).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    folding(position, ((View) v.getParent()));
                }
            });
        } else {
            messageElement = (MessageElement) convertView.getTag();
        }

        messageElement.subjectList.setText(message.getMessage());
        messageElement.nick.setText(message.getSentUser().getNick());
        messageElement.nick.setOnClickListener(new GotoUserMainAction(context, message.getSentUser()));
        
        String createdAt = TimeUtil.parseString(
                "yyyy-MM-dd hh:mm", 
                TimeUtil.getDateFromCreatedAt(message.getCreatedAt()));
        
        messageElement.createdAtList.setText(createdAt);
        messageElement.createdAt.setText(createdAt);
        messageElement.message.setText(message.getMessage());

        return convertView;
    }

    public void folding(int position, View rootView) {
        if (isFold(position)) {
            unfold(position, rootView);
        } else {
            fold(position, rootView);
        }
    }
    
    public boolean isFold(int position) {
        if (isFoldMap.get(position) == null) {
            isFoldMap.put(position, true);
        }
        
        return isFoldMap.get(position);
    }
    
    public void unfold(int position, View v) {
        isFoldMap.put(position, false);
        ((ImageView) v.findViewById(R.id.row_message_flow)).setImageResource(R.drawable.icon_flow_bottom);
        v.findViewById(R.id.row_message_message_wrapper).setVisibility(View.VISIBLE);
    }
    
    public void fold(int position, View v) {
        isFoldMap.put(position, true);
        ((ImageView) v.findViewById(R.id.row_message_flow)).setImageResource(R.drawable.icon_flow);
        v.findViewById(R.id.row_message_message_wrapper).setVisibility(View.GONE);
    }

    private class MessageElement {
        TextView subjectList;
        TextView nick; 
        TextView createdAtList;
        TextView createdAt;
        TextView message;
     }
}