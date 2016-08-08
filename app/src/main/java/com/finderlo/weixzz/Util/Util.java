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
