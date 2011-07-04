package com.matji.sandwich.overlay;

import java.util.ArrayList;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Projection;
import com.matji.sandwich.R;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.StoreTabActivity;

public class StoreItemizedOverlay extends ItemizedOverlay {
    private final int POPUP_OVERLAY_OFFSET_X = -28;
    private final int POPUP_OVERLAY_OFFSET_Y = -30;
    private View ballonView;
    private MapView mMapView;
    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
    private Context mContext;
    private BaseMapActivity mActivity;
    private TextPaint mTextPaint;
    private String mCount;
    private Bitmap mMarkerLocked; // 미발굴 
    private Bitmap mMarkerUnLocked; // 발굴 
    private Bitmap mMarkerBookmarked; // 즐겨찾
    private View popupOverlay;
    private Paint mPaint;
    private int TEXT_OFFSET_X=0;
    private int TEXT_OFFSET_Y=0;
    private int UNLOCK_BITMAP_OFFSET_X;
    private int UNLOCK_BITMAP_OFFSET_Y;
    private int LOCK_BITMAP_OFFSET_X;
    private int LOCK_BITMAP_OFFSET_Y;
    private StoreOverlayItem lastPopupItem;
    public enum MARKER_TYPE {MARKER_TYPE_UNLOCKED, MARKER_TYPE_LOCKED, MARKER_TYPE_BOOKMARKED};

    public StoreItemizedOverlay(Context context, BaseMapActivity activity, MapView mapview) {
	super(boundCenterBottom(context.getResources().getDrawable(R.drawable.marker_01)));
	
	this.mContext = context;
	this.mMapView = mapview;
	this.mActivity = activity;
		
	mMarkerLocked= BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_15_19);
	mMarkerUnLocked = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_01);

	UNLOCK_BITMAP_OFFSET_X = mMarkerUnLocked.getWidth()/2;
	UNLOCK_BITMAP_OFFSET_Y = mMarkerUnLocked.getHeight();
	LOCK_BITMAP_OFFSET_X = mMarkerLocked.getWidth()/2;
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
	lastPopupItem = (StoreOverlayItem)getItem(index);
	GeoPoint itemPoint = lastPopupItem.getPoint();

	LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	popupOverlay = (View)inflater.inflate(R.layout.store_popup_overlay, null);
	MapView.LayoutParams layoutParam = new MapView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
								    ViewGroup.LayoutParams.WRAP_CONTENT,
								    itemPoint,
								    POPUP_OVERLAY_OFFSET_X,
								    POPUP_OVERLAY_OFFSET_Y,
								    MapView.LayoutParams.BOTTOM|MapView.LayoutParams.LEFT);
	layoutParam.mode = MapView.LayoutParams.MODE_MAP;
	TextView title = (TextView)popupOverlay.findViewById(R.id.popup_item_title);
	TextView snippet = (TextView)popupOverlay.findViewById(R.id.popup_item_snippet);
	
	title.setText(lastPopupItem.getTitle());
	snippet.setText(lastPopupItem.getSnippet());
	
	popupOverlay.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
		    Store store = ((StoreOverlayItem)getItem(index)).getStore();
		    Intent intent = new Intent(mActivity, StoreTabActivity.class);
		    mActivity.startActivityWithMatjiData(intent, store);
		}
	    });

	mMapView.addView(popupOverlay, layoutParam);
	mMapView.getController().animateTo(itemPoint);

	return false;
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

	    if(store.isLocked()) {
		canvas.drawBitmap(mMarkerLocked,
				  pt.x - LOCK_BITMAP_OFFSET_X,
				  pt.y - LOCK_BITMAP_OFFSET_Y,
				  mPaint);
	    } else if(jjim_count<100) {
		if(jjim_count>-1 && jjim_count<10) {
		    TEXT_OFFSET_X = 5;
		    TEXT_OFFSET_Y = 5;
		} else if(jjim_count>9 && jjim_count<100) {
		    TEXT_OFFSET_X = 10;
		    TEXT_OFFSET_Y = 5;
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
		canvas.drawBitmap(mMarkerUnLocked,pt.x,pt.y, mPaint);
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
	    this.count = store.getLikeCount();
	    this.store = store;
	}

	protected int getCount() {
	    return count;
	}

	protected Store getStore() {
	    return store;
	}

	public boolean equals(StoreOverlayItem item) {
	    return getPoint().equals(item.getPoint()) && getTitle().equals(item.getTitle());
	}
    }
}
