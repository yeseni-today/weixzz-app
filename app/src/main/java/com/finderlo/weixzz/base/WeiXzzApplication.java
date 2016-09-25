package com.finderlo.weixzz.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.finderlo.weixzz.utility.Util;

/**
 * Created by Finderlo on 2016/8/1 0001.
 */
public class WeiXzzApplication extends Application {

    private static final String TAG = WeiXzzApplication.class.getSimpleName();
    private static Context sContext;

    @Override
    public void onCreate() {
        sContext = getApplicationContext();
        super.onCreate();
        Log.d(TAG, "onCreate: "+ Util.booleanToString(sContext==null));
    }

    public static Context getContext(){
        return sContext;
    }
}
