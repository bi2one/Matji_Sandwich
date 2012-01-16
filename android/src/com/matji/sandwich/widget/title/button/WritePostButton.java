package com.matji.sandwich.widget.title.button;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.matji.sandwich.LoginActivity;
import com.matji.sandwich.MainTabActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.SelectMapPositionActivity;
import com.matji.sandwich.WritePostActivity;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.dialog.SimpleDialog;
import com.matji.sandwich.widget.dialog.SimpleListDialog;

/**
 * 맛집 발견/이야기 쓰기 버튼
 * 
 * @author excgate
 *
 */
public class WritePostButton extends TitleImageButton implements SimpleListDialog.OnClickListener {
	private static final int FIND_STORE = 0;
	private static final int WRITE_POST = 1;
	private boolean fromMain;
	Session session;
	private Store store;
	private String tags;
	private Intent findStoreIntent;
	private Intent writePostIntent;


	public WritePostButton(Context context) {
		super(context);
		findStoreIntent = new Intent(context, SelectMapPositionActivity.class);
		writePostIntent = new Intent(context, WritePostActivity.class);
		fromMain = context instanceof MainTabActivity;
		writePostIntent.putExtra(WritePostActivity.FROM_MAIN, fromMain);
	}

	/**
	 * @see com.matji.sandwich.widget.title.button.TitleImageButton#init()
	 */
	public void init() {
		super.init();
		setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_navi_delicious_write));
		setFocusable(false);
		session = Session.getInstance(getContext());

	}

	public void setData(Store store, String tags) {
		this.store = store;
		this.tags = tags;
	}

	/**
	 * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
	 */
	public void onTitleItemClicked() {
		if (session.isLogin()) {
			String a[] = { MatjiConstants.string(R.string.dialog_action_button_write_store), 
					MatjiConstants.string(R.string.dialog_action_button_write_post)};
			if (store != null) {
				writePostIntent.putExtra(WritePostActivity.INTENT_STORE, (Parcelable)store);
				if (fromMain)
					((Activity) getContext()).startActivity(writePostIntent);
				else
					((Activity) getContext()).startActivityForResult(writePostIntent, BaseActivity.WRITE_POST_ACTIVITY);
			} else {
				SimpleListDialog listDialog = new SimpleListDialog(getContext(), null, a);
				listDialog.setOnClickListener(this);
				listDialog.show();
			}
		} else {
			Intent intent = new Intent(getContext(), LoginActivity.class);
			getContext().startActivity(intent);
		}
	}

	@Override
	public void onItemClick(SimpleDialog dialog, int position) {
		switch (position) {
		case FIND_STORE:
			((Activity) getContext()).startActivity(findStoreIntent);
			break;
		case WRITE_POST:
			if (tags != null)
				writePostIntent.putExtra(WritePostActivity.INTENT_STORE, tags);
			if (fromMain)
				((Activity) getContext()).startActivity(writePostIntent);
			else
				((Activity) getContext()).startActivityForResult(writePostIntent, BaseActivity.WRITE_POST_ACTIVITY);
			break;
		}

	}
}
