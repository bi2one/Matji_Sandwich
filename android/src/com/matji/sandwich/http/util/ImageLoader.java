package com.matji.sandwich.http.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.WeakHashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.matji.sandwich.R;
import com.matji.sandwich.http.request.HttpUtility;
import com.matji.sandwich.util.ImageUtil;
import com.matji.sandwich.util.MatjiConstants;

public class ImageLoader {
    public static enum UrlType {
        USER, ATTACH_FILE;

        public String toString() {
            switch(this) {
            case USER:
                return MatjiConstants.string(R.string.server_domain) + "users/profile";
            case ATTACH_FILE:
                return MatjiConstants.string(R.string.server_domain) + "attach_files/image";
            }
            return null;
        }

        public String getIdString() {
            switch(this) {
            case USER:
                return "user_id";
            case ATTACH_FILE:
                return "attach_file_id";
            }
            return null;
        }
    }

    public static enum ImageSize {
        SSMALL, SMALL, MEDIUM, LARGE, XLARGE;

        public String toString() {
            switch(this) {
            case SSMALL:
                return "ss";
            case SMALL:
                return "s";
            case MEDIUM:
                return "m";
            case LARGE:
                return "l";
            case XLARGE:
                return "xl";
            }
            return null;
        }
    }

    private static MemoryCache memoryCache=new MemoryCache();
    private static FileCache fileCache;
    private Map<ImageView, String> imageViews=Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    private Map<String, String> params = Collections.synchronizedMap(new HashMap<String, String>());
    private int stub_id = -1;
    private ImageConvertable convertable;
//    private boolean isScaleFile = true;
    private boolean isCacheEnable = true;

    public ImageLoader(Context context) {
        //Make the background thead low priority. This way it will not affect the UI performance
        photoLoaderThread.setPriority(Thread.NORM_PRIORITY-1);

        fileCache=new FileCache(context);
    }

    public ImageLoader(Context context, int stub_id){
        this(context);
        this.stub_id = stub_id;
    }

//    public void setScalable(boolean isScaleFile) {
//        this.isScaleFile = isScaleFile;
//    }

    public void setCacheEnable(boolean isCacheEnable) {
        this.isCacheEnable = isCacheEnable;
    }

    public void setImageConvertable(ImageConvertable convertable) {
        this.convertable = convertable;
    }

    private String createUrl(UrlType type, ImageSize size, int id) {
        params.clear();
        params.put(type.getIdString(), id + "");
        params.put("size", size.toString());
        Log.d("Matji", HttpUtility.getUrlStringWithQuery(type.toString(), params));
        return HttpUtility.getUrlStringWithQuery(type.toString(), params);
    }

    public void cancel(ImageView imageView) {
        if (!photosQueue.photosToLoad.isEmpty())
            photosQueue.Clean(imageView);
    }

    public void clear(UrlType type, int id) {
        clear(type, ImageSize.SSMALL, id);
        clear(type, ImageSize.SMALL, id);
        clear(type, ImageSize.MEDIUM, id);
        clear(type, ImageSize.LARGE, id);
        clear(type, ImageSize.XLARGE, id);
    }

    public void clear(UrlType type, ImageSize size, int id) {
        String url = createUrl(type, size, id);
        memoryCache.remove(url);
        fileCache.remove(url);
    }

    public void DisplayImage(Activity activity, UrlType type, ImageSize size, ImageView imageView, int id) {
        DisplayImage(createUrl(type, size, id), activity, imageView);
    }

    public void DisplayImage(String url, Activity activity, ImageView imageView)
    {
        imageViews.put(imageView, url);
        Bitmap bitmap = null;

        if (isCacheEnable)
            bitmap=memoryCache.get(url);

        if(bitmap!=null)
            imageView.setImageBitmap(bitmap);
        else
        {
            queuePhoto(url, activity, imageView);
            if (stub_id != -1) {
                imageView.setImageResource(stub_id);
            }
        }    
    }

    private void queuePhoto(String url, Activity activity, ImageView imageView)
    {
        //This ImageView may be used for other images before. So there may be some old tasks in the queue. We need to discard them. 
        photosQueue.Clean(imageView);
        PhotoToLoad p=new PhotoToLoad(url, imageView);
        synchronized(photosQueue.photosToLoad){
            photosQueue.photosToLoad.push(p);
            photosQueue.photosToLoad.notifyAll();
        }

        //start thread if it's not started yet
        if(photoLoaderThread.getState()==Thread.State.NEW)
            photoLoaderThread.start();
    }


    public File getFile(UrlType type, ImageSize size, int id) {
        return getFile(createUrl(type, size, id));
    }

    private File getFile(String url) {
        File f=fileCache.getFile(url);
        try {
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            InputStream is=conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            applyConvert(f);
            os.close();

            return f;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap getBitmap(String url) 
    {
        File f = fileCache.getFile(url);
        Bitmap b = null;
        if (isCacheEnable) {
            //from SD cache
            b = ImageUtil.decodeFile(f, true);
        }

        if(b!=null) {
            return b;
        }

        //from web
        try {
            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            InputStream is=conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            bitmap = applyConvert(f);
            return bitmap;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    private Bitmap applyConvert(File f) {
        Bitmap bitmap = ImageUtil.decodeFile(f, false);
        if (convertable != null) {
            bitmap = convertable.convert(bitmap);
            try {
                FileOutputStream out = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.close();
            } catch(IOException e) { }
        }
        return bitmap;
    }

    //decodes image and scales it to reduce memory consumption
    // private Bitmap decodeFile(File f){
    //     try {
    //         //decode image size
    //         BitmapFactory.Options o = new BitmapFactory.Options();
    //         o.inJustDecodeBounds = true;
    //         BitmapFactory.decodeStream(new FileInputStream(f),null,o);

    //         //Find the correct scale value. It should be the power of 2.
    //         final int REQUIRED_SIZE=70;
    //         int width_tmp=o.outWidth, height_tmp=o.outHeight;
    //         int scale=1;
    //         if (isScaleFile) {
    //             while(true){
    //                 if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
    //                     break;
    //                 width_tmp/=2;
    //                 height_tmp/=2;
    //                 scale*=2;
    //             }
    //         }

    //         //decode with inSampleSize
    //         BitmapFactory.Options o2 = new BitmapFactory.Options();
    //         o2.inSampleSize=scale;
    //         Bitmap result = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
    //         return result;
    //     } catch (FileNotFoundException e) {}
    //     return null;
    // }

    //Task for the queue
    private class PhotoToLoad
    {
        public String url;
        public ImageView imageView;
        public PhotoToLoad(String u, ImageView i){
            url=u; 
            imageView=i;
        }
    }

    PhotosQueue photosQueue=new PhotosQueue();

    public void stopThread()
    {
        photoLoaderThread.interrupt();
    }

    //stores list of photos to download
    class PhotosQueue
    {
        private Stack<PhotoToLoad> photosToLoad=new Stack<PhotoToLoad>();

        //removes all instances of this ImageView
        public void Clean(ImageView image)
        {
            for(int j=0 ;j<photosToLoad.size();){
                if(photosToLoad.get(j).imageView==image)
                    photosToLoad.remove(j);
                else
                    ++j;
            }
        }
    }

    class PhotosLoader extends Thread {
        public void run() {
            try {
                while(true)
                {
                    //thread waits until there are any images to load in the queue
                    if(photosQueue.photosToLoad.size()==0)
                        synchronized(photosQueue.photosToLoad){
                            photosQueue.photosToLoad.wait();
                        }
                    if(photosQueue.photosToLoad.size()!=0)
                    {
                        PhotoToLoad photoToLoad;
                        synchronized(photosQueue.photosToLoad){
                            photoToLoad=photosQueue.photosToLoad.pop();
                        }
                        Bitmap bmp=getBitmap(photoToLoad.url);
                        memoryCache.put(photoToLoad.url, bmp);
                        String tag=imageViews.get(photoToLoad.imageView);
                        if(tag!=null && tag.equals(photoToLoad.url)){
                            BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad.imageView);
                            Activity a = (Activity)photoToLoad.imageView.getContext();

                            a.runOnUiThread(bd);
                        }
                    }
                    if(Thread.interrupted())
                        break;
                }
            } catch (InterruptedException e) {
                //allow thread to exit
            }
        }
    }

    PhotosLoader photoLoaderThread=new PhotosLoader();

    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        ImageView imageView;
        public BitmapDisplayer(Bitmap b, ImageView i){bitmap=b;imageView=i;}
        public void run()
        {
            if(bitmap!=null)
                imageView.setImageBitmap(bitmap);
            else {
                if (stub_id != -1) {
                    imageView.setImageResource(stub_id);
                }
            }
        }
    }

    public static void clearCache(Context context) {
        memoryCache.clear();
        if (fileCache == null) {
            (new FileCache(context)).clear();
        }
    }

    public interface ImageConvertable {
        public Bitmap convert(Bitmap bitmap);
    }
}