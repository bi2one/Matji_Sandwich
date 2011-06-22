package com.matji.sandwich;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.AttachFileHttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.RelativeLayoutThatDetectsSoftKeyboard;
import com.matji.sandwich.widget.SwipeView;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class WritePostActivity extends BaseMapActivity implements Requestable, RelativeLayoutThatDetectsSoftKeyboard.Listener, MatjiLocationListener, FileUploadProgressListener {
	private static final int POST_WRITE_REQUEST = 1;
	private static final int IMAGE_UPLOAD_REQUEST = 2;
	private static final int LAT_SPAN = (int)(0.005 * 1E6);
	private static final int LNG_SPAN = (int)(0.005 * 1E6);
	private static final int THUMBNAIL_SIZE = 128;
	private int TAKE_CAMERA = 1;					// 카메라 리턴 코드값 설정
	private int TAKE_GALLERY = 2;				// 앨범선택에 대한 리턴 코드값 설정
	static final String[] IMAGE_PROJECTION = {      
		MediaStore.Images.ImageColumns.DATA, 
		MediaStore.Images.Thumbnails.DATA
	};
	private ArrayList<String> uploadImages;
	private ArrayList<Bitmap> thumbImages;
	private HttpRequestManager manager;
	private PostHttpRequest postHttpRequest;
	private Session session;
	private int keyboardHeight;
	private int contentHeightWithoutKeyboard;
	private int contentHeightWithKeyboard;
	private EditText postField;
	private EditText tagsField;
	private RelativeLayoutThatDetectsSoftKeyboard contentWrapper;
	private LinearLayout postFooterContentLayout;
	private SwipeView footerSwipeView;
	private GpsManager mGpsManager;
	private Location prevLocation;
	private MapView mapView;
	private LinearLayout thumbnailsContainer;
	private Context mContext;
	private int uploadImageIndex;
	private int postId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getApplicationContext();

		setContentView(R.layout.activity_write_post);

		mapView = (MapView) findViewById(R.id.post_user_map);
		postField = (EditText) findViewById(R.id.post_field);
		tagsField = (EditText) findViewById(R.id.tags_field);
		footerSwipeView = (SwipeView)findViewById(R.id.post_footer_content_swipe_view);
		footerSwipeView.setTouchScollEnabled(false);
		thumbnailsContainer = (LinearLayout) findViewById(R.id.thumb_images_container);

		uploadImages = new ArrayList<String>();
		thumbImages = new ArrayList<Bitmap>();

		manager = new HttpRequestManager(mContext, this);
		session = Session.getInstance(this);
		mGpsManager = new GpsManager(mContext, this);
		mGpsManager.start();

		contentWrapper = (RelativeLayoutThatDetectsSoftKeyboard)findViewById(R.id.contentWrapper);
		contentWrapper.setListener(this);

		postFooterContentLayout = (LinearLayout)findViewById(R.id.post_footer_content);
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mGpsManager.stop();
	}

	private void hideKeyboard(){
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);	
	}

	private void showKeyboard(View view){
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, 0);
	}

	public void onImgButtonClicked(View v) {
		hideKeyboard();
		tagsField.setVisibility(View.GONE);
		footerSwipeView.snapToPage(0);

	}


	public void onMapButtonClicked(View v) {
		hideKeyboard();
		tagsField.setVisibility(View.GONE);
		footerSwipeView.snapToPage(1);
	}

	public void onPostButtonClicked(View v) {
		if (session.isLogin() == false) return;
		
		postHttpRequest = new PostHttpRequest(getApplicationContext());
		if(postField.getText().toString().trim().equals("")) {
			Toast.makeText(getApplicationContext(), "Writing Content!", Toast.LENGTH_SHORT).show();
		} else {
			if(!tagsField.getText().toString().trim().equals("")) {
				postHttpRequest.actionNew(postField.getText().toString().trim()
						,tagsField.getText().toString().trim(), "ANDROID");							
			} else {
				postHttpRequest.actionNew(postField.getText().toString().trim(),"", "ANDROID");
			}
		}
		manager.request(this, postHttpRequest, POST_WRITE_REQUEST);
//		User me = session.getCurrentUser();
//		me.setPostCount(me.getPostCount() + 1);
		
	}

	
	private void startUploadImage(){
		uploadImageIndex = 0;
		uploadImage(uploadImageIndex);
	}
	
	
	private void uploadImage(int index){
		if (index >= uploadImages.size()) return;
		
		String path = Environment.getExternalStorageDirectory().toString();
		File file = new File(path, "uploadimage.jpg");
		
		Bitmap bmp = getOriginalImage(uploadImages.get(index), 4);
		FileOutputStream out;
		
		try {
			out = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			
			AttachFileHttpRequest request = new AttachFileHttpRequest(mContext);
			request.setFileUploadProgressListener(this);
			request.actionUpload(file, postId);
			manager.request(this, request, IMAGE_UPLOAD_REQUEST);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	public void onTagButtonClicked(View v){

		if (tagsField.getVisibility() == View.GONE){
			tagsField.setVisibility(View.VISIBLE);
			tagsField.requestFocus();
			showKeyboard(tagsField);
		}
		else {
			tagsField.setVisibility(View.GONE);
			postField.requestFocus();
			showKeyboard(postField);
		}
	}


	public void onTwitterButtonClicked(View v){

	}

	public void onFacebookButtonClicked(View w){

	}


	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch(tag) {
		case POST_WRITE_REQUEST:
			if (uploadImages.size() > 0 && data != null && data.size() > 0){
				postId = ((Post)(data.get(0))).getId();
				startUploadImage();
			}else {
				Log.d("Matji", "Upload failed");
			
			}

			break;
		case IMAGE_UPLOAD_REQUEST:
			++uploadImageIndex;
			if (uploadImageIndex >= uploadImages.size()){
				setResult(RESULT_OK);
				finish();
			}else {
				uploadImage(uploadImageIndex);
			}
			break;
		}
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(getApplicationContext());
	}

	@Override
	protected String titleBarText() {
		return "WritePostActivity";
	}

	@Override
	protected boolean setTitleBarButton(Button button) {
		// TODO Auto-generated method stub
		button.setText("Send");
		return true;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		// TODO Auto-generated method stub
		onPostButtonClicked(view);		
	}


	public void onSoftKeyboardShown(boolean isShowing) {
		// TODO Auto-generated method stub
		if (isShowing){
			contentHeightWithoutKeyboard = contentWrapper.getHeight();
		}else {
			contentHeightWithKeyboard = contentWrapper.getHeight();
		}

		if (keyboardHeight <= 0 && contentHeightWithoutKeyboard > 0 && contentHeightWithKeyboard > 0)
			keyboardHeight = contentHeightWithoutKeyboard - contentHeightWithKeyboard;

		if (!isShowing){
			postFooterContentLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, keyboardHeight));

			postFooterContentLayout.setVisibility(View.VISIBLE);

		}else {
			postFooterContentLayout.setVisibility(View.GONE);
		}
	}


	private void setCenter(Location loc) {
		int geoLat = (int)(loc.getLatitude() * 1E6);
		int geoLng = (int)(loc.getLongitude() * 1E6);
		GeoPoint geoPoint = new GeoPoint(geoLat, geoLng);
		mapView.getController().animateTo(geoPoint);
		mapView.getController().zoomToSpan(LAT_SPAN, LNG_SPAN);
	}

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

		if (prevLocation != null) {
			if (prevLocation.getAccuracy() <= location.getAccuracy()) {
				mGpsManager.stop();
			}
		}

		prevLocation = location;
		setCenter(location);
	}


	public void onLocationExceptionDelivered(MatjiException e) {
		// TODO Auto-generated method stub

	}


	public void onSelectPhotoButtonClicked(View v) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, TAKE_GALLERY);
	}


	public void onTakePhotoButtonClicked(View v) {
		Intent intent = new Intent();
		intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, TAKE_CAMERA);
	}

	private Bitmap getThumbnailImage(String imagePath, int dstWidth, int dstHeight){
		Bitmap src = getOriginalImage(imagePath, 8);
		return Bitmap.createScaledBitmap(src, dstWidth, dstHeight, true);	
	}


	private Bitmap getOriginalImage(String imagePath, int inSampleSize){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = inSampleSize;
		return BitmapFactory.decodeFile(imagePath, options);
	}


	@SuppressWarnings("unused")
	private Bitmap getOriginalImage(String imagePath){
		return getOriginalImage(imagePath, 1);
	}


	@SuppressWarnings("unused")
	private Uri getUriFromRealPath(String realPath){
		File file = new File(realPath);
		return Uri.fromFile(file);
	}


	private void invalidateThumbToContainerView(){
		thumbnailsContainer.removeAllViews();

		int index = 0;
		for (Bitmap bm : thumbImages){
			LayoutParams params = new LinearLayout.LayoutParams(THUMBNAIL_SIZE, THUMBNAIL_SIZE);
			ImageView iv = new ImageView(mContext);
			iv.setPadding(5, 0, 5, 0);
			iv.setLayoutParams(params);
			iv.setImageBitmap(bm);
			iv.setTag(new Integer(index));
			iv.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int index = ((Integer)v.getTag()).intValue();
					removeUploadImage(index);
					thumbnailsContainer.removeView(v);
				}
			});

			thumbnailsContainer.addView(iv);
			index++;
		}
	}	


	private void removeUploadImage(int index){
		thumbImages.remove(index);
		uploadImages.remove(index);

		invalidateThumbToContainerView();
	}


	private void addUploadImage(String imageRealPath){
		if (!uploadImages.contains(imageRealPath)){
			uploadImages.add(imageRealPath);
			thumbImages.add(getThumbnailImage(imageRealPath, THUMBNAIL_SIZE, THUMBNAIL_SIZE));

			invalidateThumbToContainerView();
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){

			if(requestCode == TAKE_CAMERA){
				String imageRealPath = null;
				final Uri uriImages = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;        
				//final Uri uriImagesthum = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
				try{
					final Cursor cursorImages = getContentResolver().query(uriImages, IMAGE_PROJECTION, null, null, null);
					if(cursorImages != null && cursorImages.moveToLast()){         
						imageRealPath = cursorImages.getString(0);
						cursorImages.close();
					} 
				}catch(Exception e){}

				addUploadImage(imageRealPath);

			}else if(requestCode == TAKE_GALLERY){
				String imageRealPath = null;
				Uri currImageURI = data.getData();
				imageRealPath = getRealPathFromURI(currImageURI);
				addUploadImage(imageRealPath);
			}
		}
	}


	private String getRealPathFromURI(Uri contentUri){
		String [] proj={MediaStore.Images.Media.DATA};
		Cursor cursor = managedQuery( contentUri, proj, null, null, null); 
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);

	}


	public void onFileWritten(int tag, int totalBytes, int readBytes) {
		// TODO Auto-generated method stub
		Log.d("Matji", "total : "+ totalBytes + "  writeBytes: " + readBytes);
		
	}
}
