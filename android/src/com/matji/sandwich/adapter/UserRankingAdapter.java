package com.matji.sandwich.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.data.User;
import com.matji.sandwich.listener.GotoUserMainAction;
import com.matji.sandwich.widget.ProfileImageView;

public class UserRankingAdapter extends MBaseAdapter {
    public UserRankingAdapter(Context context) {
        super(context);
        this.context = context;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        RankingElement userElement;
        User user = (User) data.get(position);

        if (convertView == null) {
            userElement = new RankingElement();
            convertView = getLayoutInflater().inflate(R.layout.row_ranked_user, null);

            userElement.wrapper = convertView.findViewById(R.id.row_ranked_user_wrapper);
            userElement.position = (TextView) convertView.findViewById(R.id.row_ranked_user_rank);
            userElement.profile = (ProfileImageView) convertView.findViewById(R.id.profile);
            userElement.nickname = (TextView) convertView.findViewById(R.id.row_ranked_user_nickname);
            userElement.likeCount = (TextView) convertView.findViewById(R.id.row_ranked_user_like_count);
            userElement.postCount = (TextView) convertView.findViewById(R.id.row_ranked_user_post_count);
            userElement.point = (TextView) convertView.findViewById(R.id.row_ranked_user_point);

            userElement.profile.showInsetBackground();

            convertView.setTag(userElement);
        } else {
            userElement = (RankingElement) convertView.getTag();
        }

        userElement.wrapper.setOnClickListener(new GotoUserMainAction(context, user));
        userElement.position.setText("" + (position + 1));
        if (position == 0) {
            userElement.position.setBackgroundResource(R.drawable.ranking_bg_red);
        } else {
            userElement.position.setBackgroundResource(R.drawable.ranking_bg_yellow);
        }
        userElement.profile.setUserId(user.getId());
        userElement.nickname.setText(user.getNick());
        userElement.likeCount.setText("" + user.getLikeStoreCount());
        userElement.postCount.setText("" + user.getPostCount());
        userElement.point.setText("" + user.getMileage().getTotalPoint());

        return convertView;
    }
    
    private class RankingElement {
        View wrapper;
        TextView position;
        ProfileImageView profile;
        TextView nickname;
        TextView likeCount;
        TextView postCount;
        TextView point;
    }
}