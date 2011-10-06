package com.matji.sandwich.overlay;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.matji.sandwich.R;
import com.matji.sandwich.StoreMainActivity;
// import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.util.MatjiConstants;

public class StoreItemizedOverlay extends ItemizedOverlay {
    private int POPUP_OVERLAY_OFFSET_Y = (int) MatjiConstants.dimen(R.dimen.popup_max_height);
    private View ballonView;
    private MapView mMapView;
    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
    private Context mContext;
    // private BaseMapActivity mActivity;
    private TextPaint mTextPaint;
    private String mCount;
    private Bitmap mMarkerLocked; // 미발굴 
    private Bitmap mMarkerUnLocked; // 발굴
    private Bitmap mMarkerBookmarked; // 즐겨찾기
    private View popupOverlay;
    private Paint mPaint;
    private int TEXT_OFFSET_X=0;
    private int TEXT_OFFSET_Y=0;
    private int UNLOCK_BITMAP_OFFSET_X;
    private int UNLOCK_BITMAP_OFFSET_Y;
    private int LOCK_BITMAP_OFFSET_X;
    private int LOCK_BITMAP_OFFSET_Y;
    private StoreOverlayItem lastPopupItem;
    private OverlayClickListener overlayClickListener;
    public enum MARKER_TYPE {MARKER_TYPE_UNLOCKED, MARKER_TYPE_LOCKED, MARKER_TYPE_BOOKMARKED};

    public StoreItemizedOverlay(Context context, MapView mapview) {
        super(boundCenterBottom(context.getResources().getDrawable(R.drawable.marker_01)));

        this.mContext = context;
        this.mMapView = mapview;
        // this.mActivity = activity;

        mMarkerLocked= BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_02);
        mMarkerUnLocked = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_01);

        UNLOCK_BITMAP_OFFSET_X = mMarkerUnLocked.getWidth()/2;
        UNLOCK_BITMAP_OFFSET_Y = mMarkerUnLocked.getHeight();
        LOCK_BITMAP_OFFSET_X = mMarkerLocked.getWidth()/2-2;
        LOCK_BITMAP_OFFSET_Y = mMarkerLocked.getHeight();

        //this.mBitmap100 = BitmapFactory.decodeResource(context.getResources(), R.drawable);

        mPaint = new Paint();
        mPaint.setAlpha(255);
        mPaint.setAntiAlias(true);

        mTextPaint = new TextPaint();
        mTextPaint.setARGB(255,255,255,255);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(18);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public void setOverlayClickListener(OverlayClickListener listener) {
	overlayClickListener = listener;
    }

    public void addOverlay(Store store)
    {
        if (size() == 0) {
            mMapView.getOverlays().add(this);
        }

        GeoPoint point = new GeoPoint(store.getLat(), store.getLng());
        StoreOverlayItem overlayitem = new StoreOverlayItem(point, store.getName(), store.getTagString(), store);
        mOverlays.add(overlayitem);
    }

    public void mPopulate() {
        setLastFocusedIndex(-1);
        populate();
    }

    @Override
    protected OverlayItem createItem(int index) {
        return mOverlays.get(index);
    }

    @Override
    public int size() {
        return mOverlays.size();
    }

    private void overlayRemove(StoreOverlayItem item) {
        StoreOverlayItem targetItem;
        for (int i = 0; i < mOverlays.size(); i++) {
            targetItem = (StoreOverlayItem)mOverlays.get(i);
            if (item.equals(targetItem)) {
                mOverlays.remove(targetItem);
            }
        }
    }

    private void moveOverlayToLast(StoreOverlayItem item) {
        if (item != null) {
            overlayRemove(item);
            mOverlays.add(0, item);
            mPopulate();
        }
    }

    protected boolean onTap(final int index) {
	updatePopupOverlay((StoreOverlayItem)getItem(index));
        return false;
    }

    public void updateLastPopupOverlay(Store store) {
	if (lastPopupItem != null) {
	    lastPopupItem.setStore(store);
	    updatePopupOverlay(lastPopupItem);
	}
    }

    private void updatePopupOverlay(StoreOverlayItem storeItem) {
	removePopupOverlay();
	lastPopupItem = storeItem;
	GeoPoint itemPoint = lastPopupItem.getPoint();

	LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupOverlay = (View)inflater.inflate(R.layout.store_popup_overlay, null);

        Store popupStore = lastPopupItem.getStore();
	int yOffset = 0;
	
	if (popupStore.hasLocked()) {
	    yOffset = -(POPUP_OVERLAY_OFFSET_Y + LOCK_BITMAP_OFFSET_Y);
	} else {
	    yOffset = -(POPUP_OVERLAY_OFFSET_Y + UNLOCK_BITMAP_OFFSET_Y);
	}
	
        MapView.LayoutParams layoutParam = new MapView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
								    ViewGroup.LayoutParams.WRAP_CONTENT,
								    itemPoint,
								    0, yOffset,
								    MapView.LayoutParams.CENTER_HORIZONTAL);
        layoutParam.mode = MapView.LayoutParams.MODE_MAP;

        TextView title = (TextView)popupOverlay.findViewById(R.id.popup_item_title);
        TextView likeCount = (TextView)popupOverlay.findViewById(R.id.popup_item_like_count);
        TextView postCount = (TextView)popupOverlay.findViewById(R.id.popup_item_post_count);

        title.setText(lastPopupItem.getTitle());
        likeCount.setText("" + popupStore.getLikeCount());
        postCount.setText("" + popupStore.getPostCount());

        popupOverlay.setOnClickListener(new StoreItemClickListener(storeItem.getStore(),
								   overlayClickListener));
        
        popupOverlay.setLayoutParams(layoutParam);
        popupOverlay.setMinimumHeight((int) MatjiConstants.dimen(R.dimen.popup_min_height));
        popupOverlay.setMinimumWidth((int) MatjiConstants.dimen(R.dimen.popup_min_width));
        int padding = (int) MatjiConstants.dimen(R.dimen.default_distance);
        int paddingLeft = (int) MatjiConstants.dimen(R.dimen.popup_padding)*2;
        int paddingRight = (int) MatjiConstants.dimen(R.dimen.popup_padding_right)+paddingLeft;
        popupOverlay.setPadding(paddingLeft, padding, paddingRight, padding);
        mMapView.addView(popupOverlay);
        mMapView.getController().animateTo(itemPoint);
    }

    public boolean onTouchEvent(MotionEvent event, MapView mapView) {
        removePopupOverlay();
        return super.onTouchEvent(event, mapView);
    }

    public void removePopupOverlay() {
        if(popupOverlay != null) {
            moveOverlayToLast(lastPopupItem);
            mMapView.removeView(popupOverlay);
            popupOverlay = null;
        }
    }

    @Override
    public void draw(Canvas canvas, MapView mapview, boolean arg2) {
        //super.draw(canvas,mapview,arg2);
        // TODO Auto-generated method stub
        //Log.i(TAG,"on draw ItemizedOverlay");

        int count = mOverlays.size();

        for(int i=0; i<count; i++) {
            Point pt = new Point();
            Projection projection = mapview.getProjection();
            projection.toPixels(getItem(i).getPoint(), pt);

            int jjim_count = ((StoreOverlayItem)getItem(i)).getCount();
            Store store = ((StoreOverlayItem)getItem(i)).getStore();

            if(store.hasLocked()) {
                canvas.drawBitmap(mMarkerLocked,
                        pt.x - LOCK_BITMAP_OFFSET_X,
                        pt.y - LOCK_BITMAP_OFFSET_Y,
                        mPaint);
            } else if(jjim_count<100) {
                if(jjim_count>-1 && jjim_count<10) {
                    TEXT_OFFSET_X = 5;
                    TEXT_OFFSET_Y = 0;
                } else if(jjim_count>9 && jjim_count<100) {
                    TEXT_OFFSET_X = 10;
                    TEXT_OFFSET_Y = 0;
                }
                canvas.drawBitmap(mMarkerUnLocked,
                        pt.x - UNLOCK_BITMAP_OFFSET_X,
                        pt.y - UNLOCK_BITMAP_OFFSET_Y,
                        mPaint);

                canvas.drawText(String.valueOf(jjim_count),
                        pt.x - TEXT_OFFSET_X,
                        pt.y - (UNLOCK_BITMAP_OFFSET_Y / 2) + TEXT_OFFSET_Y,
                        mTextPaint);
            } else {
                canvas.drawBitmap(mMarkerUnLocked,pt.x, pt.y, mPaint);
            }
        }
    }

    public ArrayList<OverlayItem> getOverlayItems(){
        return mOverlays;
    }

    private class StoreOverlayItem extends OverlayItem {
        private int count;
        private Store store;

        public StoreOverlayItem(GeoPoint point, String title, String snippet) {
            super(point, title, snippet);
        }

        public StoreOverlayItem(GeoPoint point, String title, String snippet, Store store) {
            super(point, title, snippet);
	    setStore(store);
        }

	public void setStore(Store store) {
	    this.store = store;
	    this.count = store.getLikeCount();
	}

        public int getCount() {
            return count;
        }

        public Store getStore() {
            return store;
        }

        public boolean equals(StoreOverlayItem item) {
            return getPoint().equals(item.getPoint()) && getTitle().equals(item.getTitle());
        }
    }

    public class StoreItemClickListener implements View.OnClickListener {
	private Store store;
	private OverlayClickListener listener;
	
	public StoreItemClickListener(Store store, OverlayClickListener listener) {
	    this.store = store;
	    this.listener = listener;
	}
	
	public void onClick(View v) {
	    if (listener != null) 
		listener.onOverlayClick(v, store);
	}
    }
}
