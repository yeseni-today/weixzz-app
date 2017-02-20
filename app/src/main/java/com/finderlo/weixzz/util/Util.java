package com.finderlo.weixzz.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.finderlo.weixzz.dao.HttpClientUtils;
import com.finderlo.weixzz.dao.UrlConstants;
import com.finderlo.weixzz.dao.WeiboParameters;
import com.finderlo.weixzz.model.model.StatusModel;
import com.finderlo.weixzz.model.model.UserModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Finderlo on 2016/8/3.
 */
public class Util {
    private static final String TAG = "Util";

@Nullable
    public static UserModel findUserByUid(String uid){
        WeiboParameters parameters = new WeiboParameters();
        parameters.put("uid",uid);
        try {
            return new Gson()
                    .fromJson(
                            HttpClientUtils.doGetRequstWithAceesToken
                                    (UrlConstants.USER_SHOW,parameters),UserModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * for sort
     *
     * @param
     * @return
     **/
    public static int expireTimeInDays(long time) {
        return (int) TimeUnit.MILLISECONDS.toDays(time - System.currentTimeMillis());
    }

    public static void sort(ArrayList<StatusModel> data) {
        if (data==null) return;

        for (int i = 0; i < data.size() - 1; i++) {
            int m = i;
            while (!compareTime(data.get(m),data.get(m+1))) {
                swap(data, m, m+1);
                m--;
                if (m<0) {
                    break;
                }
            }
        }
    }
    private static void swap(ArrayList<StatusModel> statuses, int a, int b) {
        StatusModel temp;StatusModel temp1;
        temp = statuses.get(a);temp1 = statuses.get(b);
        statuses.remove(a);statuses.add(a,temp1);
        statuses.remove(b);statuses.add(b,temp);
    }
    private static boolean compareTime(StatusModel statusModel, StatusModel t1) {
        long time1 = Date.parse(statusModel.created_at);
        long time2 = Date.parse(t1.created_at);
        return time1 > time2;
    }


    /**
     * 用于获取当前屏幕的宽度dpi
     *
     * @param a
     * @return dpi
     **/
    public static int getScreenWidthDpi(Activity a) {
        WindowManager windowManager = (WindowManager) a.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;//屏幕宽度pixels
        float density = displayMetrics.density;
        int srceenWidth = (int) (width / density);
        Log.d(TAG, "getScreenWidthDpi: 当前屏幕的宽度Dpi:" + srceenWidth);
        return srceenWidth;
    }

    public static int getScreenHightDpi(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;//屏幕宽度pixels
        float density = displayMetrics.density;
        int srceenHeight = (int) (height / density);
        Log.d(TAG, "getScreenWidthDpi: 当前屏幕的高度Dpi:" + srceenHeight);
        return srceenHeight;
    }

    /**
     * 将px值转换为dip或dp值,保证尺寸大小不变
     *
     * @param pxValue
     * @param context scale
     *                DisplayMetrics中属性density
     * @return
     **/
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值,保证尺寸大小不变
     *
     * @param dipValue
     * @param context  scale
     *                 DisplayMetrics中属性density2
     * @return
     **/
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值,保证文字大小不变
     *
     * @param pxValue
     * @param context scale
     *                DisplayMetrics类中属性scaledDensity
     * @return
     **/
    public static int px2sp(Context context, float pxValue) {
        final float fontscale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontscale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值,保证尺寸大小不变
     *
     * @param spValue
     * @param context scale
     *                DisplayMetrics类中属性scaledDensity
     * @return
     **/
    public static int sp2px(Context context, float spValue) {
        final float fontscale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontscale + 0.5f);
    }

    /**
     * 用于将String对象转换为boolean对象
     * 转换规则：
     **/
    public static boolean stringToBoolean(String string) {
        if ("true".equals(string)) {
            return true;
        }
        return false;
    }

    /**
     * 用于将String对象转换为boolean对象
     * 转换规则：
     **/
    public static String booleanToString(boolean flag) {
        if (flag) {
            return "true";
        }
        return "false";
    }


}
