package com.matji.sandwich.util;

import android.util.Log;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Matrix;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;

// import com.matji.sandwich.exception.MatjiException;
// import com.matji.sandwich.exception.BitmapCompressMatjiException;
// import com.matji.sandwich.exception.BitmapCompressIOMatjiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

public class ImageUtil {
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float pixels, int inset) {
	Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
	Canvas canvas = new Canvas(output);

	final int color = 0xff424242;
	final Paint paint = new Paint();
	final Rect rect = new Rect(inset, inset, bitmap.getWidth()-inset, bitmap.getHeight()-inset);
	final RectF rectF = new RectF(rect);
	final float roundPx = pixels;

        paint.setAntiAlias(true);
	canvas.drawARGB(0, 0, 0, 0);
	paint.setColor(color);
	canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

	paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	canvas.drawBitmap(bitmap, rect, rect, paint);

	return output;
    }
	
    public static Bitmap getBitmap(Drawable drawable) {
	Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
	Canvas canvas = new Canvas(bitmap);
	drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
	drawable.draw(canvas);
		
	return bitmap;
    }
	
    public static Drawable getDrawable(Bitmap bitmap) {
	return (Drawable) (new BitmapDrawable(bitmap));
    }

    public static File decodeFileToFile(File file, int compressRatio, boolean isScalable) {
	Bitmap bitmap = decodeFile(file, isScalable);
        File result = null;
        FileOutputStream resultStream = null;

    	if (bitmap == null) {
    	    return null;
    	}

        try {
            result = File.createTempFile("matji_", "_compressed.jpg");
            resultStream = new FileOutputStream(result);
        } catch(IOException e) {
    	    Log.d("Matji", e.toString());
    	    return null;
        }
	
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressRatio, resultStream);
            resultStream.flush();
            resultStream.close();
            return result;
        } catch(IOException e) {
    	    Log.d("Matji", e.toString());
    	    return null;
        }
    }

    public static Bitmap decodeFile(File f, boolean isScalable){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            if (isScalable) {
                while(true){
                    if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                        break;
                    width_tmp/=2;
                    height_tmp/=2;
                    scale*=2;
                }
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            Bitmap result = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
	    int rotateAngle = getRotateAngle(f);
	    if (rotateAngle != 0) {
		result = rotate(result, rotateAngle);
	    }
            return result;
        } catch (FileNotFoundException e) {}
        return null;
    }

    private static int getRotateAngle(File file) {
	ExifInterface exif = null;
	try {
	    exif = new ExifInterface(file.getAbsolutePath());
	} catch(IOException e) {
	    Log.d("Matji", e.toString());
	    return 0;
	}
	
	int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					       ExifInterface.ORIENTATION_NORMAL);
	switch(orientation) {
	case ExifInterface.ORIENTATION_ROTATE_90:
	    return 90;
	case ExifInterface.ORIENTATION_ROTATE_180:
	    return 180;
	case ExifInterface.ORIENTATION_ROTATE_270:
	    return 270;
	}
	return 0;
    }

    public static Bitmap rotate(Bitmap bitmap, int angle) {
	Matrix matrix = new Matrix();
	matrix.postRotate(angle);
	return Bitmap.createBitmap(bitmap,
				   0,
				   0,
				   bitmap.getWidth(),
				   bitmap.getHeight(),
				   matrix,
				   true);
    }
}