package com.matji.sandwich.widget.title.button;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.os.Environment;
import android.graphics.Bitmap;

import com.matji.sandwich.R;
import com.matji.sandwich.MainTabActivity;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.SaveToGalleryUtil;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Screenshot을 찍는 버튼 
 * 
 * @author bizone
 *
 */
public class ScreenShotButton extends TitleImageButton {
    private View mainView;
    private File cacheDir;
    private SimpleAlertDialog successDialog;
    
    public ScreenShotButton(Context context) {
	super(context);
    }

    /**
     * @see com.matji.sandwich.widget.title.button.TitleImageButton#init()
     */
    public void init() {
	super.init();
	setImageDrawable(context.getResources().getDrawable(R.drawable.icon_navi_phonesave));

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            cacheDir=new File(Environment.getExternalStorageDirectory(), "YummyStoryScreenShots");
        else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();

	successDialog = new SimpleAlertDialog(context, R.string.screen_shot_button_success);
    }
    
    public void setView(View mainView) {
	this.mainView = mainView;
    }
    
    /**
     * @see com.matji.sandwich.widget.title.button.TitleItem#onTitleItemClicked()
     */
    public void onTitleItemClicked() {
	Log.d("Matji", "ScreenShotButton Clicked");

	Bitmap bitmap;
	mainView.setDrawingCacheEnabled(true);
	bitmap = Bitmap.createBitmap(mainView.getDrawingCache());
	mainView.setDrawingCacheEnabled(false);

	OutputStream fout = null;
	File imageFile = new File(cacheDir, String.valueOf(mainView.toString().hashCode()));

	try {
	    fout = new FileOutputStream(imageFile);
	    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
	    fout.flush();
	    fout.close();
	} catch(FileNotFoundException e) {
	    e.printStackTrace();
	} catch(IOException e) {
	    e.printStackTrace();
	}

	SaveToGalleryUtil.save(context,
			       imageFile,
			       imageFile.toString(),
			       imageFile.toString(),
			       imageFile.lastModified(),
			       SaveToGalleryUtil.Orientation.TOP,
			       null,
			       "image/jpeg");

	successDialog.show();
    }
}
