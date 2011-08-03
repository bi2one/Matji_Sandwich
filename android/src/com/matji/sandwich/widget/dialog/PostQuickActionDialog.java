package com.matji.sandwich.widget.dialog;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;

public class PostQuickActionDialog implements OnClickListener {
	private QuickActionDialog quickaction;
	private Session session;

	private Post post;

	public PostQuickActionDialog(Context context, Post post) {
		quickaction = new QuickActionDialog(context);
		session = Session.getInstance(context);

		ActionItem commentAction = new ActionItem();
		commentAction.setIcon(MatjiConstants.drawable(R.drawable.btn_home));
		quickaction.addActionItem(commentAction);

		ActionItem likeAction = new ActionItem();
		likeAction.setIcon(MatjiConstants.drawable(R.drawable.btn_like));
		quickaction.addActionItem(likeAction);

		if (isMine(post)) {
						ActionItem editAction = new ActionItem();
			editAction.setIcon(MatjiConstants.drawable(R.drawable.btn_notification));
			quickaction.addActionItem(editAction);

			ActionItem deleteAction = new ActionItem();
			deleteAction.setIcon(MatjiConstants.drawable(R.drawable.btn_delete));
			quickaction.addActionItem(deleteAction);
		}
		
		quickaction.setOnActionItemClickListener(new QuickActionDialog.OnActionItemClickListener() {

			@Override
			public void onItemClick(int pos) {
				if (pos == 0) {
					Log.d("Matji", "comment button click");
				} else if (pos == 1) {
					Log.d("Matji", "like button click");
				} else if (pos == 2) {
					Log.d("Matji", "edit button click");
				} else if (pos == 3) {
					Log.d("Matji", "delete button click");
				}
			}
		});
	}

	public boolean isMine(Post post) {
		return session.isLogin() && session.getCurrentUser().getId() == post.getUserId();
	}

	@Override
	public void onClick(View v) {
		quickaction.show(v);
	}
}
