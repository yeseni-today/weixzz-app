/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.finderlo.weixzz.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.provider.Settings;
import android.util.Log;

import com.finderlo.weixzz.SinaAPI.openapi.StatusesAPI;
import com.finderlo.weixzz.XzzConstants;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.Set;

/**
 * 该类定义了微博授权时所需要的参数。
 * 
 * @author SINA
 * @since 2013-10-07
 */
public class AccessTokenKeeper {
    private static final String PREFERENCES_NAME = "com_weixzz_token";

    private static final String KEY_UID           = "uid";
    private static final String KEY_ACCESS_TOKEN  = "access_token";
    private static final String KEY_EXPIRES_IN    = "expires_in";
    private static final String KEY_REFRESH_TOKEN    = "refresh_token";
    private static final String TAG = "AccessTokenKeeper";

    private static Oauth2AccessToken sOauth2AccessToken;
    private static StatusesAPI sStatusesAPI;
    private static Context sContext;

    public static void initAccessTokenKeeper(Context context){
        sContext = context.getApplicationContext();
        sOauth2AccessToken = readAccessToken(sContext);
        if (sOauth2AccessToken!=null){
            sStatusesAPI = new StatusesAPI(sContext, XzzConstants.APP_KEY, sOauth2AccessToken);
        }

    }

    public static void setOauth2AccessToken(Oauth2AccessToken oauth2AccessToken){
        sOauth2AccessToken = oauth2AccessToken;
    }

    public static Oauth2AccessToken getOauth2AccessToken() {
        if (sOauth2AccessToken == null) {
            Log.e(TAG, "getOauth2AccessToken: ", new Exception("Token is null || 授权信息为空"));
            return null;
        }
        return sOauth2AccessToken;
    }

    /**
     * 保存 Token 对象到 SharedPreferences。
     * 
     * @param context 应用程序上下文环境
     * @param token   Token 对象
     */
    public static void writeAccessToken(Context context, Oauth2AccessToken token) {
        if (null == context || null == token) {
            return;
        }
        
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putString(KEY_UID, token.getUid());
        editor.putString(KEY_ACCESS_TOKEN, token.getToken());
        editor.putString(KEY_REFRESH_TOKEN, token.getRefreshToken());
        editor.putLong(KEY_EXPIRES_IN, token.getExpiresTime());
        editor.commit();

        initAccessTokenKeeper(context);
    }

    /**
     * 从 SharedPreferences 读取 Token 信息。
     * 
     * @param context 应用程序上下文环境
     * 
     * @return 返回 Token 对象
     */
    public static Oauth2AccessToken readAccessToken(Context context) {
        if (null == context) {
            return null;
        }
        
        Oauth2AccessToken token = new Oauth2AccessToken();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        token.setUid(pref.getString(KEY_UID, ""));
        token.setToken(pref.getString(KEY_ACCESS_TOKEN, ""));
        token.setRefreshToken(pref.getString(KEY_REFRESH_TOKEN, ""));
        token.setExpiresTime(pref.getLong(KEY_EXPIRES_IN, 0));

        if ("".equals(pref.getString(KEY_UID,""))){
            return null;
        }
        
        return token;
    }
    /**
     *
     *@param context
     *
     * @return userToken exist? or out of expiresTime?
     **/
    public static boolean isAccessTokenExist(Context context){
        Oauth2AccessToken token = readAccessToken(context);
        if ("".equals(token.getUid())){
            return false;
        }
        if (token.getExpiresTime()>= System.currentTimeMillis()){
            return true;
        }
        return false;
    }

    /**
     * 清空 SharedPreferences 中 Token信息。
     * 
     * @param context 应用程序上下文环境
     */
    public static void clear(Context context) {
        if (null == context) {
            return;
        }
        
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public static StatusesAPI getStatuesAPI() {

        return sStatusesAPI;
    }
}
