package com.matji.sandwich;

import java.util.ArrayList;

import com.matji.sandwich.adapter.GridImageAdapter;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.AttachFileHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class GridGalleryActivity extends Activity implements Requestable {
	private Intent intent; 
	private HttpRequest request;
	private HttpRequestManager manager;
	private GridView g;

	private int[] attachFileIds;
	private int store_id;
	private static final int IMAGE_REQUEST = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid_gallery);
		
		intent = getIntent();
		store_id = intent.getIntExtra("store_id", 0);
		request = new AttachFileHttpRequest(this);
		manager = new HttpRequestManager(this, this);
		g = (GridView) findViewById(R.id.grid_gallery);
		manager.request(this, request(), IMAGE_REQUEST);
	}

	public HttpRequest request() {
		((AttachFileHttpRequest) request).actionStoreList(store_id);
		return request;
	}
	
	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		// TODO Auto-generated method stub		
		attachFileIds = new int[data.size()];
		/* Set AttachFile ID */
		for (int i = 0; i < data.size(); i++) {
			attachFileIds[i] = ((AttachFile) data.get(i)).getId();
		}

		GridImageAdapter adapter = new GridImageAdapter(this);
		adapter.setAttachFileIds(attachFileIds);
		
		g.setAdapter(adapter);
		g.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				callImageViewer(position);
			}
		});
	}

	@Override
	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(this);
	}

	public void callImageViewer(int position) { 
		Intent viewerIntent = new Intent(this, ImageSliderActivity.class);
		viewerIntent.putExtra("attach_file_ids", attachFileIds);
		viewerIntent.putExtra("position", position);
		startActivity(viewerIntent);
	}
	/**========================================== 
	 * 		        Adapter class 
	 * ==========================================*/
//	public class ImageAdapter extends BaseAdapter {
//		private String imgData;
//		private String geoData;
//		private ArrayList<string> thumbsDataList;
//		private ArrayList<string> thumbsIDList;
//		
//		ImageAdapter(Context c){
//			mContext = c;
//			thumbsDataList = new ArrayList<string>();
//			thumbsIDList = new ArrayList<string>();
//			getThumbInfo(thumbsIDList, thumbsDataList);
//		}
//		
//		public final void callImageViewer(int selectedIndex){
//			Intent i = new Intent(mContext, ImagePopup.class);
//			String imgPath = getImageInfo(imgData, geoData, thumbsIDList.get(selectedIndex));
//			i.putExtra("filename", imgPath);
//			startActivityForResult(i, 1);
//		}
//		
//		public boolean deleteSelected(int sIndex){
//			return true;
//		}
//		
//		public int getCount() {
//			return thumbsIDList.size();
//		}
//
//		public Object getItem(int position) {
//			return position;
//		}
//
//		public long getItemId(int position) {	
//			return position;
//		}
//
//		public View getView(int position, View convertView, ViewGroup parent) {
//			ImageView imageView;
//			if (convertView == null){
//				imageView = new ImageView(mContext);
//				imageView.setLayoutParams(new GridView.LayoutParams(95, 95));
//				imageView.setAdjustViewBounds(false);
//				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//				imageView.setPadding(2, 2, 2, 2);
//			}else{
//				imageView = (ImageView) convertView;
//			}
//			BitmapFactory.Options bo = new BitmapFactory.Options();
//			bo.inSampleSize = 8;
//			Bitmap bmp = BitmapFactory.decodeFile(thumbsDataList.get(position), bo);
//			Bitmap resized = Bitmap.createScaledBitmap(bmp, 95, 95, true);
//			imageView.setImageBitmap(resized);
//			
//			return imageView;
//		}
//		
//		private void getThumbInfo(ArrayList<string> thumbsIDs, ArrayList<string> thumbsDatas){
//			String[] proj = {MediaStore.Images.Media._ID,
//							 MediaStore.Images.Media.DATA,
//							 MediaStore.Images.Media.DISPLAY_NAME,
//							 MediaStore.Images.Media.SIZE};
//			
//			Cursor imageCursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//					proj, null, null, null);
//			
//			if (imageCursor != null && imageCursor.moveToFirst()){
//				String title;
//				String thumbsID;
//				String thumbsImageID;
//				String thumbsData;
//				String data;
//				String imgSize;
//				
//				int thumbsIDCol = imageCursor.getColumnIndex(MediaStore.Images.Media._ID);
//				int thumbsDataCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
//				int thumbsImageIDCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
//				int thumbsSizeCol = imageCursor.getColumnIndex(MediaStore.Images.Media.SIZE);
//				int num = 0;
//				do {
//					thumbsID = imageCursor.getString(thumbsIDCol);
//					thumbsData = imageCursor.getString(thumbsDataCol);
//					thumbsImageID = imageCursor.getString(thumbsImageIDCol);
//					imgSize = imageCursor.getString(thumbsSizeCol);
//					num++;
//					if (thumbsImageID != null){
//						thumbsIDs.add(thumbsID);
//						thumbsDatas.add(thumbsData);
//					}
//				}while (imageCursor.moveToNext());
//			}
//			imageCursor.close();
//			return;
//		}
//		
//		private String getImageInfo(String ImageData, String Location, String thumbID){
//			String imageDataPath = null;
//			String[] proj = {MediaStore.Images.Media._ID,
//					 MediaStore.Images.Media.DATA,
//					 MediaStore.Images.Media.DISPLAY_NAME,
//					 MediaStore.Images.Media.SIZE};
//			Cursor imageCursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//					proj, "_ID='"+ thumbID +"'", null, null);
//			
//			if (imageCursor != null && imageCursor.moveToFirst()){
//				if (imageCursor.getCount() > 0){
//					int imgData = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
//					imageDataPath = imageCursor.getString(imgData);
//				}
//			}
//			imageCursor.close();
//			return imageDataPath;
//		}
//	}
}