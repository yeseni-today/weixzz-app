package com.finderlo.weixzz.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.finderlo.weixzz.Util.CallbackListener.HttpLoadPicCallbackListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Finderlo on 2016/8/3.
 */
public class HttpUtil {
    public static void loadPicFromUrl(final String addressUrl, final HttpLoadPicCallbackListener listener){
        if ("".equals(addressUrl)||addressUrl==null) return;
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(addressUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                    listener.onComplete(bitmap);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    listener.onError(new MalformedURLException("貌似输入的统一资源定位器是不对的"));
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onError(new IOException("打开链接输入流出了错？"));
                }

            }
        }).start();
    }
}
