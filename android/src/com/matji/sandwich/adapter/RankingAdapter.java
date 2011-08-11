package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.User;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RankingAdapter extends MBaseAdapter {  
    private Context context;

    public RankingAdapter(Context context) {
	super(context);
	this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
	RankingElement rankingElement;
	User user = (User) data.get(position);

	if (convertView == null) {
	    rankingElement = new RankingElement();
	    convertView = getLayoutInflater().inflate(R.layout.adapter_ranking, null);

	    rankingElement.nickname = (TextView) convertView.findViewById(R.id.adapter_ranking_nickname);

	    convertView.setTag(rankingElement);
	} else {
	    rankingElement = (RankingElement) convertView.getTag();
	}
	
	rankingElement.nickname.setText(user.getNick());
	
	return convertView;
    }

    private class RankingElement {
	TextView nickname;
    }    
}