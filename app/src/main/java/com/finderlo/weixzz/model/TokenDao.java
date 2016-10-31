package com.finderlo.weixzz.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.finderlo.weixzz.dao.HttpClientUtils;
import com.finderlo.weixzz.dao.UrlConstants;
import com.finderlo.weixzz.dao.WeiboParameters;
import com.finderlo.weixzz.model.model.UserModel;
import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

/**
 * Created by Finderlo on 2016/10/30.
 */

public class TokenDao {

    private static final String TAG = "TokenDao";

    private static final String PREFERENCES_NAME = "access_tokens";

    /**
     * 授权用户的UID，本字段只是为了方便开发者，
     * 减少一次user/show接口调用而返回的，
     * 第三方应用不能用此字段作为用户登录状态的识别，只有access_token才是用户授权的唯一票据。
     */
    private static final String KEY_UID = "uid";
    /**
     * 用户授权的唯一票据，用于调用微博的开放接口，同时也是第三方应用验证微博用户登录的唯一票据，
     * 第三方应用应该用该票据和自己应用内的用户建立唯一影射关系，来识别登录状态，不能使用本返回值里的UID字段来做登录识别。
     */
    private static final String KEY_ACCESS_TOKEN = "access_token";
//    /**
//     * access_token的生命周期，单位是秒数。
//     */
//    private static final String KEY_EXPIRES_IN = "expires_in";
    /**
     * access_token的过期时间。为获得accesstoken的时间加上当前系统时间，单位为秒数
     */
    private static final String KEY_EXPIRE_DATE = "expire_date";
    /**
     * 文件中存储的token数量
     */
    private static final String KEY_TOKEN_COUNT = "token_count";
    /**
     * 程序默认的token数量，即当前用户的index下标
     */
    private static final String KEY_DEFAULT_TOKEN_INDEX = "token_default_index";

    private static final String KEY_USER_NAME = "username";

    private long mExpireDate;
    private String mAccessToken;
    private String mUid;
    private String mName;

    private int mCurrentTokenIndex;
    private int mTokenCount;

    private static Context sContext;
    private static TokenDao singleton;

    SharedPreferences mPreferences = sContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

    public static TokenDao getInstance(Context context) {
        if (singleton == null) {
            sContext = context;
            singleton = new TokenDao(context);
        }
        return singleton;
    }

    private TokenDao(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        mCurrentTokenIndex = preferences.getInt(KEY_DEFAULT_TOKEN_INDEX, 0);
        mTokenCount = preferences.getInt(KEY_TOKEN_COUNT, 0);

        mExpireDate = preferences.getLong(KEY_EXPIRE_DATE + mCurrentTokenIndex, 0);
        mAccessToken = preferences.getString(KEY_ACCESS_TOKEN + mCurrentTokenIndex, "");
        mUid = preferences.getString(KEY_UID + mCurrentTokenIndex, "");
        mName = preferences.getString(KEY_USER_NAME + mCurrentTokenIndex, "");
    }

    /**
     * 通过微博登录，登录后设置当前用户为输入参数
     *
     * @param token  token
     * @param expire 过期时间
     */
    public void login(String token, String expire) {

        mUid = getUidByToken(token);
        mName = getNameByToken(token);
        mAccessToken = token;
        mExpireDate = System.currentTimeMillis() + Long.valueOf(expire) * 1000;

        if (isAccessTokenExist(sContext, mAccessToken)) {
            updateExpireDate();
        } else {
            mTokenCount++;
            mCurrentTokenIndex = mTokenCount;
            cache();
        }


    }

    private void updateExpireDate() {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putLong(KEY_EXPIRE_DATE + mCurrentTokenIndex, mExpireDate);
    }

    public void loginTest(String accessToken, String expire, String uid, String name) {
        mUid = uid;
        mName = name;
        mAccessToken = accessToken;
        mExpireDate = System.currentTimeMillis() + Long.valueOf(expire) * 1000;

        if (isAccessTokenExist(sContext, mAccessToken)) {
            updateExpireDate();
        } else {
            mTokenCount++;
            mCurrentTokenIndex = mTokenCount;
            cache();
        }
    }

    private void cache() {
        SharedPreferences.Editor editor = mPreferences.edit();


        editor.putInt(KEY_TOKEN_COUNT, mTokenCount);
        editor.putInt(KEY_DEFAULT_TOKEN_INDEX, mCurrentTokenIndex);

        editor.putLong(KEY_EXPIRE_DATE + mCurrentTokenIndex, mExpireDate);

        editor.putString(KEY_UID + mCurrentTokenIndex, mUid);
        editor.putString(KEY_USER_NAME + mCurrentTokenIndex, mName);
        editor.putString(KEY_ACCESS_TOKEN + mCurrentTokenIndex, mAccessToken);

        editor.commit();

    }

    public Map<String, Integer> getTokens() {
        Map<String, Integer> result = new HashMap<>();

        SharedPreferences preferences = sContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        mTokenCount = preferences.getInt(KEY_TOKEN_COUNT, 1);

        for (int i = 0; i < mTokenCount; i++) {
            result.put(preferences.getString(KEY_USER_NAME + i, ""), i);
        }
        return result;
    }

    /**
     * @param context
     * @return userToken exist? or out of expiresTime?
     **/
    public boolean isAccessTokenExist(Context context, String token) {
        for (int i = 0; i < mTokenCount; i++) {
            if (mPreferences.getString(KEY_ACCESS_TOKEN + i, "").equals(token)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通过用户的名字设置当前用户信息
     */
    public boolean setCurrentUser(String name) {
        Map<String, Integer> users = getTokens();
        for (String s : users.keySet()) {
            if (s.equals(name)) {
                return setCurrentUser(users.get(name));
            }
            return false;
        }
        return false;
    }

    /**
     * 通过当前用户在pref文件中的下标来设置当前用户信息
     */
    public boolean setCurrentUser(int userIndex) {
        if (userIndex >= mTokenCount) {
            return false;
        }

        SharedPreferences preferences = sContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        mExpireDate = preferences.getLong(KEY_EXPIRE_DATE + userIndex, 0);
        mAccessToken = preferences.getString(KEY_ACCESS_TOKEN + userIndex, "");
        mUid = preferences.getString(KEY_UID + userIndex, "");
        mName = preferences.getString(KEY_USER_NAME + userIndex, "");

        HttpClientUtils.setAccessToken(mAccessToken);
        return true;
    }


    /**
     * 用于通过token来获取uid
     */
    public static String getUidByToken(String access_token) {
        WeiboParameters parameters = new WeiboParameters();
        parameters.put("access_token", access_token);
        try {

            String json = HttpClientUtils.doGetRequst(UrlConstants.GET_UID, parameters);
            return new JSONObject(json).optString("uid");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 用于通过token来获得名称
     */
    public static String getNameByToken(String access_token) {
        WeiboParameters parameters = new WeiboParameters();
        parameters.put("access_token", access_token);
        try {
            String json = HttpClientUtils.doGetRequst(UrlConstants.USER_SHOW, parameters);
            return new Gson().fromJson(json, UserModel.class).name;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getExpireDate() {
        return mExpireDate;
    }

    public void setExpireDate(long expireDate) {
        mExpireDate = expireDate;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getCurrentTokenIndex() {
        return mCurrentTokenIndex;
    }

    public void setCurrentTokenIndex(int currentTokenIndex) {
        mCurrentTokenIndex = currentTokenIndex;
    }

    public int getTokenCount() {
        return mTokenCount;
    }

    public void setTokenCount(int tokenCount) {
        mTokenCount = tokenCount;
    }

    public String getAccessToken() {
        return mAccessToken;
    }
}
