package com.finderlo.weixzz.Util;

import android.app.Application;
import android.content.Context;

/**
 * Created by Finderlo on 2016/8/1 0001.
 */
public class WeiXzzApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static Context getContext(){
        return sContext;
    }
}
