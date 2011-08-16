package com.matji.sandwich.adapter;

import com.matji.sandwich.ActivityStartable;
import com.matji.sandwich.PostActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.listener.GotoStoreMainAction;
import com.matji.sandwich.listener.GotoUserMainAction;
import com.matji.sandwich.widget.ProfileImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * {@link Post} Adapter.
 * 
 * @author mozziluv
 *
 */

public class SimplePostAdapter extends MBaseAdapter {

    private GotoUserMainAction action1;
    private GotoStoreMainAction action2;

    /**
     * 기본 생성자.
     * 
     * @param context
     */
    public SimplePostAdapter(Context context) {
        super(context);
        init();
    }

    /**
     * 초기화 메소드.
     */
    protected void init() {}
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SimplePostElement simplePostElement;

        if (convertView == null) {
            simplePostElement = new SimplePostElement();
            convertView = getLayoutInflater().inflate(R.layout.row_simple_post, null);

            simplePostElement.profile = (ProfileImageView) convertView.findViewById(R.id.profile);
            simplePostElement.nick = (TextView) convertView.findViewById(R.id.row_simple_post_nick);
            simplePostElement.at= (TextView) convertView.findViewById(R.id.row_simple_post_at);
            simplePostElement.storeName = (TextView) convertView.findViewById(R.id.row_simple_post_store_name);
            simplePostElement.post = (TextView) convertView.findViewById(R.id.row_simple_post_post);
            convertView.setTag(simplePostElement);
        } else {
            simplePostElement = (SimplePostElement) convertView.getTag();
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListItemClick(position);
            }
        });

        setActions(simplePostElement, position);
        setViewData(simplePostElement, position);

        return convertView;

    }



    /**
     * holder의 각 View에 대해 리스너를 등록한다.
     * 
     * @param holder 뷰가 위치한 holder
     * @param position 해당 holder의 position
     */
    private void setActions(SimplePostElement holder, final int position) {
        action1 = new GotoUserMainAction(context, ((Post) data.get(position)).getUser());
        action2 = new GotoStoreMainAction(context, ((Post) data.get(position)).getStore());

        holder.profile.setOnClickListener(action1);
        holder.nick.setOnClickListener(action1);
        holder.storeName.setOnClickListener(action2);
        holder.post.setLinksClickable(false);
    }

    /**
     * {@link Post}내의 정보를 View에 뿌려준다.
     * 
     * @param holder 정보를 뿌려줄 view holder
     * @param position 해당 holder의 position
     */
    private void setViewData(SimplePostElement holder, int position) {
        Post post = (Post) data.get(position);

        Store store = post.getStore();
        User user = post.getUser();

        if (store != null) {
            holder.at.setVisibility(View.VISIBLE);
            holder.storeName.setText(" "+store.getName());
        }
        else {
            holder.at.setVisibility(View.GONE);
            holder.storeName.setText("");
        }

        holder.profile.setUserId(user.getId());
        holder.nick.setText(user.getNick()+" ");
        holder.post.setText(post.getPost().trim());
    }

    /**
     * 리스트 아이템을 클릭했을 때,
     * {@link PostActivity}로 이동한다.
     * 
     * @param position 클릭된 아이템의 positoin
     */
    private void onListItemClick(int position) {
        Post post = (Post) data.get(position);
        if (post.getActivityId() == 0) {
            Intent intent = new Intent(context, PostActivity.class);
            intent.putExtra(PostActivity.POSTS, data);
            intent.putExtra(PostActivity.POSITION, position);
            if (((Activity) context).getParent().getParent() instanceof BaseTabActivity) {
                BaseTabActivity parent = (BaseTabActivity) ((Activity) context).getParent();
                parent.tabStartActivityForResult(intent, BaseActivity.POST_ACTIVITY, (ActivityStartable) context);
            } else {
                ((Activity) context).startActivityForResult(intent, BaseActivity.POST_ACTIVITY);
            }
        }
    }
    /**
     * {@link Post} 뷰 홀더
     * 
     * @author mozziluv
     *
     */
    private class SimplePostElement {
        ProfileImageView profile;
        TextView nick;
        TextView at;
        TextView storeName;
        TextView post;
    }
}