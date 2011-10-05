package com.matji.sandwich.util;

import android.util.Log;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
// import com.matji.sandwich.exception.MatjiException;
// import com.matji.sandwich.exception.BitmapCompressMatjiException;
// import com.matji.sandwich.exception.BitmapCompressIOMatjiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public static File compressFile(File file) {
        File result = null;
        FileOutputStream resultStream = null;
        FileInputStream fileStream = null;
        try {
            result = File.createTempFile("matji_", "_compressed.jpg");
            resultStream = new FileOutputStream(result);
            fileStream = new FileInputStream(file);
        } catch(IOException e) {
	    Log.d("Matji", e.toString());
	    return null;
        }

        Bitmap bitmap = BitmapFactory.decodeStream(fileStream);
	if (bitmap == null) {
	    return null;
	}

        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, resultStream);
            resultStream.flush();
            resultStream.close();
            return result;
        } catch(IOException e) {
	    Log.d("Matji", e.toString());
	    return null;
        }
    }
}