package com.finderlo.weixzz.Util;

import android.content.Context;
import android.util.Log;

import com.finderlo.weixzz.Database.StatusDatabaseTool;
import com.finderlo.weixzz.SinaAPI.openapi.models.Status;
import com.finderlo.weixzz.SinaAPI.openapi.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
                StatusDatabaseTool.getInstance(context).insertStatus(status,statusjsonString);

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
