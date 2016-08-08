package com.finderlo.weixzz.Util;

import android.content.Context;
import android.util.Log;

import com.finderlo.weixzz.Database.DatabaseTool;
import com.finderlo.weixzz.SinaAPI.openapi.models.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Finderlo on 2016/8/3.
 */
public class Util {
    private static final String TAG = "Util";

    /**
     *将px值转换为dip或dp值,保证尺寸大小不变
     *@param pxValue
     * @param context scale
     *                DisplayMetrics中属性density
     *
     * @return
     **/
    public static int px2dip(Context context,float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale +0.5f);
    }

    /**
     *将dip或dp值转换为px值,保证尺寸大小不变
     *@param dipValue
     * @param context scale
     *                DisplayMetrics中属性density2
     *
     * @return
     **/
    public static int dip2px(Context context,float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue*scale +0.5f);
    }

    /**
     *将px值转换为sp值,保证文字大小不变
     *@param pxValue
     * @param context scale
     *                DisplayMetrics类中属性scaledDensity
     *
     * @return
     **/
    public static int px2sp(Context context,float pxValue){
        final float fontscale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue/fontscale +0.5f);
    }

    /**
     *将dip或dp值转换为px值,保证尺寸大小不变
     *@param spValue
     * @param context scale
     *                DisplayMetrics类中属性scaledDensity
     *
     * @return
     **/
    public static int sp2px(Context context,float spValue){
        final float fontscale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue*fontscale +0.5f);
    }

    /**
     *处理返回的微博json数据,将其存储到数据库中
     **/
    public static void handleJSONStringData(Context context,String data){
        JSONArray array;

        try {
            array = new JSONObject(data).getJSONArray("statuses");
            for (int i = 0; i < array.length(); i++) {
                String statusjsonString = array.getString(i);
                Status status = Status.parse(statusjsonString);
                DatabaseTool.getInstance(context).insertStatus(status,statusjsonString);
                Log.d(TAG, statusjsonString);
            }


            Log.i(TAG, "handleJSONStringData: complete");

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "onComplete: 转换为json数组失败", e);
        }
    }
    /**
     *用于将String对象转换为boolean对象
     * 转换规则：
     **/
    public static boolean stringToBoolean(String string){
        if ("true".equals(string)){
            return true;
        }
        return false;
    }

    /**
     *用于将String对象转换为boolean对象
     * 转换规则：
     **/
    public static String booleanToString(boolean flag){
        if (flag){
            return "true";
        }
        return "false";
    }
}
