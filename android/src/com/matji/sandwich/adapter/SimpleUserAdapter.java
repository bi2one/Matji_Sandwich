package com.matji.sandwich.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.data.User;
import com.matji.sandwich.listener.GotoUserMainAction;
import com.matji.sandwich.widget.ProfileImageView;

public class SimpleUserAdapter extends MBaseAdapter {
    
    private OnClickListener listener;
    
    public SimpleUserAdapter(Context context) {
        super(context);
        this.context = context;
    }
    
    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        RankingElement userElement;
        User user = (User) data.get(position);

        if (convertView == null) {
            userElement = new RankingElement();
            convertView = getLayoutInflater().inflate(R.layout.row_simple_user, null);
            
            userElement.wrapper = convertView.findViewById(R.id.row_simple_user_wrapper);
            userElement.profile = (ProfileImageView) convertView.findViewById(R.id.profile);
            userElement.nickname = (TextView) convertView.findViewById(R.id.row_simple_user_nickname);
            userElement.likeCount = (TextView) convertView.findViewById(R.id.row_simple_user_like_count);
            userElement.postCount = (TextView) convertView.findViewById(R.id.row_simple_user_post_count);
            userElement.point = (TextView) convertView.findViewById(R.id.row_simple_user_point);

            userElement.profile.showInsetBackground();

            convertView.setTag(userElement);
        } else {
            userElement = (RankingElement) convertView.getTag();
        }

//        userElement.wrapper.setOnClickListener(new GotoUserMainAction(context, user));
        userElement.profile.setUserId(user.getId());
        userElement.nickname.setText(user.getNick());
        userElement.likeCount.setText("" + user.getLikeStoreCount());
        userElement.postCount.setText("" + user.getPostCount());
        userElement.point.setText("" + user.getMileage().getTotalPoint());


        if (listener == null) {
            userElement.wrapper.setOnClickListener(new GotoUserMainAction(context, user));
        } else {
            userElement.wrapper.setTag(data.get(position));
            userElement.wrapper.setOnClickListener(listener);
        }
        return convertView;
    }

    protected int getPositionVisibility() {
        return View.GONE;
    }

    private class RankingElement {
        View wrapper;
        ProfileImageView profile;
        TextView nickname;
        TextView likeCount;
        TextView postCount;
        TextView point;
    }
}