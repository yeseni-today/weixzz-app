package com.finderlo.weixzz.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Finderlo on 2016/8/1 0001.
 */
public class WeiXzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME_STATUS = "Status";
    private static final String TABLE_NAME_USER     = "User";

    /**
     *status表中使用了user的ID和idstr来存储user数据
     **/
    private static final String CREATETABLE_STATUS = "create table Status ( " +
            "tableId                    integer primary key autoincrement ," +
            "created_at                 text ,"     +
            "id                         text ,"     +
            "mid                        text ,"     +
            "idstr                      text ,"     +
            "text                       text ,"     +
            "source                     text ,"     +
            "favorited                  text ,"     +
            "truncated                  text ,"     +
            "in_reply_to_status_id      text ,"     +
            "in_reply_to_user_id        text ,"     +
            "in_reply_to_screen_name    text ,"     +
            "thumbnail_pic              text ,"     +
            "bmiddle_pic                text ,"     +
            "original_pic               text ,"     +
            "reposts_count              integer ,"  +
            "comments_count             integer ,"  +
            "attitudes_count            integer ,"  +
            "mlevel                     integer ,"  +
            "user_idstr                 text ,"     +
            "user_id                    text"       +
            ")";


    /**
     *这个建表语句没有添加源类型非int string boolean的数据
     * 如 user.status
     * boolean以text方式存储
     **/
    private static final String CREATETABLE_USER = "create table User ( " +
            "tableId                    integer primary key autoincrement ," +
            "id                         text ,"     +
            "idstr                      text ,"     +
            "screen_name                text ,"     +
            "name                       text ,"     +
            "province                   integer ,"  +
            "city                       integer ,"  +
            "location                   text ,"     +
            "description                text ,"     +
            "url                        text ,"     +
            "profile_image_url          text ,"     +
            "domain                     text ,"     +
            "profile_url                text ,"     +
            "weihao                     text ,"     +
            "gender                     text ,"     +
            "followers_count            integer ,"  +
            "friends_count              integer ,"  +
            "statuses_count             integer ,"  +
            "favourites_count           integer ,"  +
            "created_at                 text ,"     +
            "following                  text ,"     +
            "allow_all_act_msg          text ,"     +
            "geo_enabled                text ,"     +
            "verified                   text ,"     +
            "verified_type              integer ,"  +
            "remark                     text ,"     +
            "allow_all_comment          text ,"     +
            "avatar_large               text ,"     +
            "avatar_hd                  text ,"     +
            "verified_reason            text ,"     +
            "follow_me                  text ,"     +
            "online_status              integer ,"  +
            "bi_followers_count         integer ,"  +
            "lang                       text "      +
            ")";

    private Context mContext;
    private static final String TAG = "WeiXzzDatabaseHelper";

    public WeiXzzDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATETABLE_STATUS);
        sqLiteDatabase.execSQL(CREATETABLE_USER);
        Log.d(TAG, "onCreate: 表创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
