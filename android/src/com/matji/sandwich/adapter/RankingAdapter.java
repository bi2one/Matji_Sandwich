package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.User;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.widget.ProfileImageView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RankingAdapter extends MBaseAdapter {  
    private Context context;
    private MatjiImageDownloader downloader;
    private int profileSize;

    public RankingAdapter(Context context) {
	super(context);
	this.context = context;
	downloader = new MatjiImageDownloader(context);
	profileSize = (int) MatjiConstants.dimen(R.dimen.profile_size);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
	RankingElement rankingElement;
	User user = (User) data.get(position);

	if (convertView == null) {
	    rankingElement = new RankingElement();
	    convertView = getLayoutInflater().inflate(R.layout.adapter_ranking, null);

	    rankingElement.position = (TextView) convertView.findViewById(R.id.adapter_ranking_position);
	    rankingElement.profile = (ProfileImageView) convertView.findViewById(R.id.adapter_ranking_profile);
	    rankingElement.nickname = (TextView) convertView.findViewById(R.id.adapter_ranking_nickname);
	    rankingElement.likeCount = (TextView) convertView.findViewById(R.id.adapter_ranking_like_count);
	    rankingElement.postCount = (TextView) convertView.findViewById(R.id.adapter_ranking_post_count);
	    rankingElement.point = (TextView) convertView.findViewById(R.id.adapter_ranking_point);

	    convertView.setTag(rankingElement);
	} else {
	    rankingElement = (RankingElement) convertView.getTag();
	}
	
	rankingElement.position.setText("" + (position + 1));
	rankingElement.profile.setUserId(user.getId());
	rankingElement.nickname.setText(user.getNick());
	rankingElement.likeCount.setText("" + user.getStoreCount());
	rankingElement.postCount.setText("" + user.getPostCount());
	rankingElement.point.setText("" + user.getMileage().getTotalPoint());
	
	return convertView;
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