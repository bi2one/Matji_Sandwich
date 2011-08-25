package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.ProfileImageView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleUserAdapter extends MBaseAdapter {
    public SimpleUserAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RankingElement userElement;
        User user = (User) data.get(position);

        if (convertView == null) {
            userElement = new RankingElement();
            convertView = getLayoutInflater().inflate(R.layout.row_simple_user, null);

            userElement.position = (TextView) convertView.findViewById(R.id.row_simple_user_position);
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

        userElement.position.setVisibility(getPositionVisibility());
        if (getPositionVisibility() == View.VISIBLE)
            userElement.position.setText("" + (position + 1));
        userElement.profile.setUserId(user.getId());
        userElement.nickname.setText(user.getNick());
        userElement.likeCount.setText("" + user.getLikeStoreCount());
        userElement.postCount.setText("" + user.getPostCount());
        userElement.point.setText("" + user.getMileage().getTotalPoint());

        return convertView;
    }

    protected int getPositionVisibility() {
        return View.GONE;
    }
    
    private class RankingElement {
        TextView position;
        ProfileImageView profile;
        TextView nickname;
        TextView likeCount;
        TextView postCount;
        TextView point;
    }
}