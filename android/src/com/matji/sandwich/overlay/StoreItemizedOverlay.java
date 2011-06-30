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



public class StoreItemizedOverlay extends ItemizedOverlay<OverlayItem>
{
	@Override
	    public boolean onTouchEvent(MotionEvent event, MapView mapView) {
	    // TODO Auto-generated method stub
	    //Log.i("Test","wow");
	    removeBallon();
	    return super.onTouchEvent(event, mapView);
	}
	
	private View ballonView;
	private MapView mMapView;
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private TextPaint mTextPaint;
	private String mCount;
	//private Drawable mDrawable;
	private Bitmap mMarkerLocked; // 미발굴 
	private Bitmap mMarkerUnLocked; // 발굴 
	private Bitmap mMarkerBookmarked; // 즐겨찾
	private Paint mPaint;
	private int TEXT_OFFSET_X=0;
	private int TEXT_OFFSET_Y=0;
	private int BITMAP_OFFSET_X =0;
	private int BITMAP_OFFSET_Y = 0;
	public enum MARKER_TYPE {MARKER_TYPE_UNLOCKED, MARKER_TYPE_LOCKED, MARKER_TYPE_BOOKMARKED};

	public StoreItemizedOverlay(Context context, MapView mapview) {
		super(boundCenterBottom(context.getResources().getDrawable(R.drawable.marker_01)));
		
		this.mContext = context;
		this.mMapView = mapview;
		
		//removeBallon();
		
		mMarkerLocked= BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_15_19);
		mMarkerUnLocked = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_01);
		
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
	    
	    String tagText = "";
	    ArrayList<Tag> tags = store.getTags();
	    if (tags != null){
	    	for(int index = 0; index < tags.size(); index++){
		    	tagText += tags.get(index).getTag();
		    }
	    }
	    
	    
	    StoreOverlayItem overlayitem = new StoreOverlayItem(point, store.getName(), tagText, store.getLikeCount());
	    mOverlays.add(overlayitem);
	    mPopulate();
	}
	
	public void mPopulate()
	{
	    //removeBallon();

	    populate();
	}
	
	@Override
	    protected OverlayItem createItem(int index) {
	    // TODO Auto-generated method stub
	    //	Log.i(TAG,"ocreateItem");
	    return mOverlays.get(index);
	}
	
	@Override
	    public int size() {
	    // TODO Auto-generated method stub
	    //Log.i(TAG,"size()");
	    return mOverlays.size();
	}
	
	@Override
	    protected boolean onTap(final int index) {
	    // TODO Auto-generated method stub
	    //Log.i(TAG,"index: "+index);
//	    GeoPoint itemPt = getItem(index).getPoint();
//	    OverlayItem item = getItem(index);
//	    
//	    LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	    
//	    LinearLayout ll = (LinearLayout)inflater.inflate(R.layout.popup_overlay, null);
//	    
//	    MapView.LayoutParams lp = 
//		new MapView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
//					 itemPt,-28,-30,MapView.LayoutParams.BOTTOM|MapView.LayoutParams.LEFT);
//	    
//	    TextView _item_title = (TextView)ll.findViewById(R.id.popup_item_title);
//	    TextView _item_snippet = (TextView)ll.findViewById(R.id.popup_item_snippet);
//	    lp.mode = MapView.LayoutParams.MODE_MAP;
//	    
//	    _item_title.setText(item.getTitle());
//	    _item_snippet.setText(item.getSnippet());
//	    
//	    
//	    ballonView = ll;
//	    ballonView.setOnClickListener(new OnClickListener() {
//		    
//		    @Override
//			public void onClick(View v) {
//			// TODO Auto-generated method stub
//			Intent intent = new Intent();
//			intent.setClass(mContext, MatjiStoreActivity.class);
//			try {
//			    intent.putExtra("store_id", mListStore.get(index).getId());
//			} catch (JSONException e) {
//			    // TODO Auto-generated catch block
//			    e.printStackTrace();
//			}
//			startActivity(intent);
//			
//		    }
//		});
//	    
//	    mMapView.addView(ll, lp);
//	    mMapView.getController().animateTo(itemPt);
	    
	    
	    return super.onTap(index);
	}
	
	public void removeBallon() {
	    if(ballonView != null)
		mMapView.removeView(ballonView);
	}
	
	@Override
	public boolean onTap(GeoPoint p, MapView mapView) {
	    // TODO Auto-generated method stub
	    //removeBallon();
	    //Log.i(TAG,"lastfocuseditemIndex: "+getLastFocusedIndex());
	    
	    return super.onTap(p, mapView);
	}



@Override
    public void draw(Canvas canvas, MapView mapview, boolean arg2) {
		//super.draw(canvas,mapview,arg2);
	    // TODO Auto-generated method stub
		//Log.i(TAG,"on draw ItemizedOverlay");
		
	    int count = mOverlays.size();
	    
	    for(int i=0; i<count; i++)
		{
		    Point pt = new Point();
		    Projection projection = mapview.getProjection();
		    projection.toPixels(getItem(i).getPoint(), pt);
		    
		    int jjim_count = ((StoreOverlayItem)getItem(i)).getCount();
		    
		    if(jjim_count<100)
			{
			    if(jjim_count>-1 && jjim_count<10)
				{
				    TEXT_OFFSET_X = 5;
				    TEXT_OFFSET_Y = 5;
				    BITMAP_OFFSET_X = mMarkerUnLocked.getWidth()/2;
				    BITMAP_OFFSET_Y = mMarkerUnLocked.getHeight();
				}
			    else if(jjim_count>9 && jjim_count<100)
				{
				    TEXT_OFFSET_X = 10;
				    TEXT_OFFSET_Y = 5;
				    BITMAP_OFFSET_X = mMarkerUnLocked.getWidth()/2;
				    BITMAP_OFFSET_Y = mMarkerUnLocked.getHeight();
				}
			    canvas.drawBitmap(mMarkerUnLocked,pt.x-BITMAP_OFFSET_X,pt.y-BITMAP_OFFSET_Y, mPaint);
			    
				canvas.drawText(String.valueOf(jjim_count), pt.x + mMarkerUnLocked.getWidth() / 2 - BITMAP_OFFSET_X-TEXT_OFFSET_X,
						pt.y + mMarkerUnLocked.getHeight() / 2 - BITMAP_OFFSET_Y+TEXT_OFFSET_Y, mTextPaint);
			}
		    else
			{
			    canvas.drawBitmap(mMarkerUnLocked,pt.x,pt.y, mPaint);
			}
		    
		    
		}
		
    
	}


	public ArrayList<OverlayItem> getOverlayItems(){
		return mOverlays;
	}


	private class StoreOverlayItem extends OverlayItem                                                                                                                             
	{                                                                                                                                                                              
	    private int count;                                                                                                                                                      
	    public StoreOverlayItem(GeoPoint point, String title, String snippet) {                                                                                                    
	        super(point, title, snippet);                                                  
	        // TODO Auto-generated constructor stub                                                                                                                                
	    }                                                                                                                                                                          
	                                                                                                                                                                               
	    public StoreOverlayItem(GeoPoint point, String title, String snippet, int count) {                                                                                       
	        super(point, title, snippet);                                                                                                                                          
	        this.count = count;                                                                                                                                                    
	        // TODO Auto-generated constructor stub                                                                                                                                
	    }                                                                                                                                                                          
	                                                                                                                                                                               
	    protected int getCount()                                                                                                                                                
	    {                                                                                                                                                                          
	        return count;                                                                                                                                                          
	    }                                                                                                                                                                          
	}       

}
