package com.matji.sandwich.http.util;

import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size=1024;
        try {
            byte[] bytes=new byte[buffer_size];
            for(;;) {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public static String getCorrectUrl(String url) {
        String correctUrl = url;
        if (!url.startsWith("http://") 
                && !url.startsWith("https://") 
                && !url.equals("")) {
            correctUrl = "http://" + correctUrl;
        }

        return correctUrl;        
    }
}