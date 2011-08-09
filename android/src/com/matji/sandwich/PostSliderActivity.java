//package com.matji.sandwich;
//
//import android.view.View;
//import android.content.Intent;
//
//import com.matji.sandwich.session.Session;
//import com.matji.sandwich.session.SessionIndex;
//import com.matji.sandwich.widget.PostListView;
//import com.matji.sandwich.widget.MyPostListView;
//
//public class PostSliderActivity extends SliderActivity {
//	private int[] viewIds = {
//			R.id.ListView1,
//			R.id.ListView2,
//			R.id.ListView3,
//	};
//	
//	private int[] titleIds = {
//			R.string.search_post,
//			R.string.all_post,
//			R.string.near_post,
//	};
//	
//	public static final int INDEX_NEAR_POST = 2;
//	
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		switch (requestCode) {
//		case WRITE_POST_ACTIVITY:
//			if (resultCode == RESULT_OK) {
//				getCurrentPage().requestReload();
//			}
//			break;
//		
//		case POST_MAIN_ACTIVITY:
//			if (resultCode == RESULT_OK) {
//				if (data != null) {
//					int position = data.getIntExtra("position", -1);
//					if (position >= 0) {
////						((PostListView) getCurrentPage()).delete(position);
//					}
//				}
//			}
//			break;
//		}
//	}
//	
//	private void onWriteButtonClicked() {
//		if (loginRequired()) {
//			startActivityForResult(new Intent(getApplicationContext(), WritePostActivity.class), WRITE_POST_ACTIVITY);
//		}
//	}
//
//	@Override
//	protected int getLayoutId() {
//		return R.layout.activity_post_slider;
//	}
//
//	@Override
//	protected int getDefaultPageCount() {
//		return viewIds.length;
//	}	
//	
//	@Override
//	protected String getSliderIndex() {
//		return SessionIndex.POST_SLIDER_INDEX;
//	}
//
//
//	@Override
//	protected int[] getViewIds() {
//		return viewIds;
//	}
//
//	@Override
//	protected int[] getTitleIds() {
//		return titleIds;
//	}
//
//	@Override
//	protected View getPrivateView() {
//		MyPostListView privateView = new MyPostListView(this, null);
//		privateView.setScrollContainer(false);
//		privateView.setUserId(Session.getInstance(this).getCurrentUser().getId());
//		privateView.setTag(R.string.title, getResources().getString(R.string.my_post).toString());
//		privateView.setActivity(this);
//		privateView.setCanRepeat(true);
//
//		return privateView;
//	}
//}
