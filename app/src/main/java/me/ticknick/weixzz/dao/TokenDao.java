package me.ticknick.weixzz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.ticknick.weixzz.database.DatabaseHelper;
import me.ticknick.weixzz.database.table.TokenTable;
import me.ticknick.weixzz.model.TokenModel;
import me.ticknick.weixzz.model.UserModel;

import static me.ticknick.weixzz.database.table.TokenTable.KEY_ACCESS_TOKEN;
import static me.ticknick.weixzz.database.table.TokenTable.KEY_EXPIRE_DATE;
import static me.ticknick.weixzz.database.table.TokenTable.KEY_ISCURRENT;
import static me.ticknick.weixzz.database.table.TokenTable.KEY_UID;
import static me.ticknick.weixzz.database.table.TokenTable.KEY_USER_NAME;
import static me.ticknick.weixzz.database.table.TokenTable.TABLE_NAME_TOKEN;


/**
 * Created by Finderlo on 2016/11/13.
 * TokenDao 管理Dao,通过数据库来存储所有已经登录的信息
 */

public class TokenDao {

    public static final String TAG = TokenDao.class.getSimpleName();

    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    private TokenModel mTokenModel;

    private static TokenDao mInstance;

    private TokenDao(Context context) {
        mDatabaseHelper = DatabaseHelper.getInstance(context);
        mDatabase = mDatabaseHelper.getWritableDatabase();
        mTokenModel = getCurrentTokenModel();
        if (mTokenModel != null) {
            HttpClientUtils.setAccessToken(mTokenModel.getToken());
        }
    }

    public static TokenDao getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TokenDao(context);
        }
        return mInstance;
    }


    public TokenModel getCurrentTokenModel() {
        Cursor cursor = mDatabase.query(TABLE_NAME_TOKEN, null,
                KEY_ISCURRENT + "=?", new String[]{"true"}, null, null, null);
        List list = parseCursorToTokenModel(cursor);
        if (list.size() == 0)
            return null;
        else {
            return (TokenModel) list.get(0);
        }
    }

    public boolean isTokenAlreadyExistByName(String name) {
        if (name == null) {
            return false;
        }
        Cursor cursor = mDatabase.query(TABLE_NAME_TOKEN, null,
                KEY_USER_NAME + "=?", new String[]{name}, null, null, null);
        int count = cursor.getCount();
        if (cursor != null) {
            cursor.close();
        }
        return count == 0 ? false : true;
    }

    public void save(TokenModel tokenModel) {
        mDatabase.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ACCESS_TOKEN, tokenModel.getToken());
        contentValues.put(KEY_EXPIRE_DATE, tokenModel.getExpireDate());
        contentValues.put(KEY_ISCURRENT, String.valueOf(tokenModel.isCurrent()));
        contentValues.put(KEY_UID, tokenModel.getUid());
        contentValues.put(KEY_USER_NAME, tokenModel.getUserName());
        mDatabase.insert(TABLE_NAME_TOKEN, null, contentValues);
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();

        Log.d(TAG, "save: ");
        Log.e(TAG, "save: getall"+getAll().size());
    }

    public List<TokenModel> getAll() {
        Cursor cursor = mDatabase.query(TABLE_NAME_TOKEN, null, null, null, null, null, null);
        Log.d(TAG, "getAll: cursor size "+cursor.getCount());
        return parseCursorToTokenModel(cursor);
    }

    public TokenModel parseTokenModelBytokenAndExpire(String token,String expire){
        String uid = getUidByToken(token);
        String name = getNameByToken(token, uid);
        long expireDate = System.currentTimeMillis() + Long.valueOf(expire) * 1000;

        TokenModel tokenModel = new TokenModel();
        tokenModel.setToken(token);
        tokenModel.setUserName(name);
        tokenModel.setExpireDate(expireDate);
        tokenModel.setUid(uid);
        return tokenModel;
    }

    public void login(String token, String expire) {

        mTokenModel = parseTokenModelBytokenAndExpire(token, expire);
        mTokenModel.setCurrent(true);

        //登陆新的用户后，需要将之前的登陆用户的信息设为false，并更新数据库
        //在存入新的用户之前，获取之前登陆用户
        TokenModel preLoginTokenModel = getCurrentTokenModel();
        if (preLoginTokenModel != null) {
            updateTokenModelCurrentInfo(preLoginTokenModel, false);
        }

        if (isTokenAlreadyExistByName(mTokenModel.getUserName())) {
            //如果存在，则更新token和过期时间
            Log.d(TAG, "login: 已经存在");
            updateTokenModelExpire(mTokenModel, mTokenModel.getExpireDate());
        } else {
            Log.d(TAG, "login: 不存在，开始保存");
            //如果不存在，直接保存
            save(mTokenModel);
        }

        //存入数据库后，设置应用中使用的token
        HttpClientUtils.setAccessToken(token);

    }

    public void changeCurrentTokenModel(TokenModel tokenModel){
        //登陆新的用户后，需要将之前的登陆用户的信息设为false，并更新数据库
        //在存入新的用户之前，获取之前登陆用户
        TokenModel preLoginTokenModel = getCurrentTokenModel();
        Log.d(TAG, "changeCurrentTokenModel: preLoginTokenModel name:"+preLoginTokenModel.getUserName());
        if (preLoginTokenModel != null) {
            updateTokenModelCurrentInfo(preLoginTokenModel, false);
        }

        mTokenModel = tokenModel;

        if (isTokenAlreadyExistByName(tokenModel.getUserName())) {
            //如果存在，则更新token和过期时间
            Log.d(TAG, "login: 已经存在");
            mTokenModel.setCurrent(true);
            updateTokenModelExpire(mTokenModel, mTokenModel.getExpireDate());
        } else {
            Log.d(TAG, "login: 不存在，开始保存");
            //如果不存在，直接保存
            save(mTokenModel);
        }

        //存入数据库后，设置应用中使用的token
        HttpClientUtils.setAccessToken(tokenModel.getToken());
    }

    private void updateTokenModelExpire(TokenModel tokenModel, long expire) {
        ContentValues values = new ContentValues();
        values.put(KEY_EXPIRE_DATE, expire);
        values.put(KEY_ISCURRENT,"true");
        mDatabase.update(TABLE_NAME_TOKEN, values,
                KEY_UID + "=? and " + KEY_USER_NAME + "=?",
                new String[]{tokenModel.getUid(), tokenModel.getUserName()});
    }

    private void updateTokenModelCurrentInfo(TokenModel tokenModel, boolean isCurrent) {
        ContentValues values = new ContentValues();
        values.put(KEY_ISCURRENT, String.valueOf(isCurrent));
        mDatabase.beginTransaction();
        mDatabase.update(TABLE_NAME_TOKEN, values,
                KEY_UID + "=? and " + KEY_USER_NAME + "=?",
                new String[]{tokenModel.getUid(), tokenModel.getUserName()});
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
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
    public static String getNameByToken(String access_token, String uid) {
        WeiboParameters parameters = new WeiboParameters();
        parameters.put("access_token", access_token);
        parameters.put("uid", uid);
        try {
            String json = HttpClientUtils.doGetRequst(UrlConstants.USER_SHOW, parameters);
            return new Gson().fromJson(json, UserModel.class).name;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<TokenModel> parseCursorToTokenModel(Cursor cursor) {
        List<TokenModel> result = new ArrayList<>();
        Log.d(TAG, "parseCursorToTokenModel: cursor count:"+cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                TokenModel tokenModel = new TokenModel();
                tokenModel.setUid(cursor.getString(cursor.getColumnIndex(KEY_UID)));
                tokenModel.setCurrent(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(KEY_ISCURRENT))));
                tokenModel.setExpireDate(cursor.getLong(cursor.getColumnIndex(KEY_EXPIRE_DATE)));
                tokenModel.setUserName(cursor.getString(cursor.getColumnIndex(KEY_USER_NAME)));
                tokenModel.setToken(cursor.getString(cursor.getColumnIndex(KEY_ACCESS_TOKEN)));
                result.add(tokenModel);
                Log.d(TAG, "parseCursorToTokenModel: add one");
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        Log.d(TAG, "parseCursorToTokenModel: "+result.size());

        return result;
    }

    public TokenModel getTokenModel() {
        return mTokenModel;
    }

    public String getAccessToken() {
        return mTokenModel.getToken();
    }

    public long getExpireDate() {
        return mTokenModel.getExpireDate();
    }

    public void cache() {
        save(mTokenModel);
    }

    public static boolean isAccessTokenExist(Context context) {
        if (getInstance(context).getTokenModel() != null){
            Log.d(TAG, "isAccessTokenExist: "+true);
            return true;
        }
        Log.d(TAG, "isAccessTokenExist: "+false);
        return false;
    }
}
