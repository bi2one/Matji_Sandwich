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
        RankingElement rankingElement;
        User user = (User) data.get(position);

        if (convertView == null) {
            rankingElement = new RankingElement();
            convertView = getLayoutInflater().inflate(R.layout.row_simple_user, null);

            rankingElement.position = (TextView) convertView.findViewById(R.id.row_simple_user_position);
            rankingElement.profile = (ProfileImageView) convertView.findViewById(R.id.row_simple_user_profile);
            rankingElement.nickname = (TextView) convertView.findViewById(R.id.row_simple_user_nickname);
            rankingElement.likeCount = (TextView) convertView.findViewById(R.id.row_simple_user_like_count);
            rankingElement.postCount = (TextView) convertView.findViewById(R.id.row_simple_user_post_count);
            rankingElement.point = (TextView) convertView.findViewById(R.id.row_simple_user_point);

            convertView.setTag(rankingElement);
        } else {
            rankingElement = (RankingElement) convertView.getTag();
        }

        rankingElement.position.setVisibility(getPositionVisibility());
        if (getPositionVisibility() == View.VISIBLE)
            rankingElement.position.setText("" + (position + 1));
        rankingElement.profile.setUserId(user.getId());
        rankingElement.nickname.setText(user.getNick());
        rankingElement.likeCount.setText("" + user.getStoreCount());
        rankingElement.postCount.setText("" + user.getPostCount());
        rankingElement.point.setText("" + user.getMileage().getTotalPoint());

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