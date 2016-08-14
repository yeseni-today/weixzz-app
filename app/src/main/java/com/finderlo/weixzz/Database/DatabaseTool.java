package com.finderlo.weixzz.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.finderlo.weixzz.SinaAPI.openapi.models.Status;
import com.finderlo.weixzz.SinaAPI.openapi.models.User;
import com.finderlo.weixzz.Constants;

import java.util.ArrayList;

/**
 * Created by Finderlo on 2016/8/1 0001.
 * <p/>
 * <p/>
 */

public class DatabaseTool {

    public static final String TYPE_IDSTR = " idstr ";
    public static final String TYPE_ID = " id ";


    private static final String TAG = "DatabaseTool";

    private static SQLiteDatabase mDatabase;
    private static Context sContext;

    private static final String TABLE_NAME_STATUS = " Status ";
    private static final String TABLE_NAME_USER = " User ";

    /**
     * 这是数据库版本信息
     */
    private static final int STATUS_TABLE_VERSION_1 = 1;


    private DatabaseTool(Context context) {
        sContext = context;
        WeiXzzDatabaseHelper weiXzzDatabaseHelper =
                new WeiXzzDatabaseHelper(context, TABLE_NAME_STATUS, null, STATUS_TABLE_VERSION_1);
        mDatabase = weiXzzDatabaseHelper.getWritableDatabase();
    }

    /**
     * 插入数据库中一个用户信息
     * @param user
     * @param userjson 这是数据代表的json数据
     **/
    public void insertUser(User user,String userjson) {
        if (isDataAlreadyExist(TABLE_NAME_USER, TYPE_IDSTR, user.idstr)) {
            Log.i(TAG, "insertUser: 用户数据已经存在,没有插入");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(Constants.USER_ID, user.id);
        values.put(Constants.USER_IDSTR, user.idstr);
        values.put("json",userjson);

        mDatabase.insert(TABLE_NAME_USER, null, values);
        Log.i(TAG, "insertUser: 用户数据已经存在");
    }

    /**
     * 插入数据库中一个微博信息
     *
     * @param status
     * @param jsonString 这是数据代表的json数据
     **/
    public void insertStatus(Status status, String jsonString) {
        if (isDataAlreadyExist(TABLE_NAME_STATUS, TYPE_IDSTR, status.idstr)) {
            Log.i(TAG, "insertStatus: 微博数据已经存在,没有插入");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(Constants.STATUS_ID, status.id);
        values.put(Constants.STATUS_MID, status.mid);
        values.put(Constants.STATUS_IDSTR, status.idstr);
        values.put("json", jsonString);

        mDatabase.insert(TABLE_NAME_STATUS, null, values);
        Log.i(TAG, "insertStatus: 微博数据插入成功");

    }

    /**
     *
     *@param tablename the table name for query
     *@param type the type for id or idstr
     *@param id the id
     * @return isDataAlreadyExist
     **/
    public boolean isDataAlreadyExist(String tablename, String type, String id) {
        if (!(TABLE_NAME_STATUS.equals(tablename) || TABLE_NAME_USER.equals(tablename))) {
            return false;
        }
        if (!(TYPE_ID.equals(type) || TYPE_IDSTR.equals(type))) {
            return false;
        }
        if (mDatabase.rawQuery("select * from " + tablename + " where " + type + " = ? ", new String[]{id}).moveToFirst()) {
            return true;
        }
        return false;
    }



    /**
     * 从数据库中查询所有的微博微博
     **/
    public ArrayList<Status> queryStatuses() {

        Cursor cursor = mDatabase.query(TABLE_NAME_STATUS, null, null, null, null, null, null, null);
        ArrayList<Status> list = new ArrayList<Status>();
        if (cursor.moveToFirst()) {
            do {
                Status status = queryStatus(cursor, cursor.getPosition());
                list.add(status);
            } while (cursor.moveToNext());
            Log.d(TAG, "queryStatuses success  list size" + list.size());
        }
        if (cursor != null) cursor.close();
        return list;
    }


    /**
     * 通过给定的idstr来查询微博
     **/
    public Status queryStatus(String status_id_type, String status_id) {
        Status status = null;
        if (TYPE_IDSTR.equals(status_id_type) && !"".equals(status_id) && null != status_id) {
            Cursor cursor = mDatabase.rawQuery("select * from Status where idstr = ? ", new String[]{status_id});
            status = queryStatus(cursor);
            if (cursor != null) cursor.close();
        }
        return status;
    }

    /**
     * 查询游标所指定的位置来返回一个status对象
     **/
    public Status queryStatus(Cursor cursor, int position) {
        Status status = null;
        if (cursor.moveToPosition(position)) {
            String jsonstring = cursor.getString(cursor.getColumnIndex("json"));
            status = Status.parse(jsonstring);
        }
        return status;
    }

    /**
     * 通过cursor来返回一个status对象
     **/
    public Status queryStatus(Cursor cursor) {
        Status status = null;
        if (cursor.moveToFirst()) {
            status = queryStatus(cursor, cursor.getPosition());
        }
        return status;
    }


    /**
     * 通过给定的idstr来查询用户
     **/
    public User queryUser(String user_id_type, String user_id) {
        User user = null;
        if (TYPE_IDSTR.equals(user_id_type) && !user_id.equals("")) {
            Cursor cursor = mDatabase.rawQuery("select * from User where idstr =  " + user_id, null);
            if (cursor.getCount() > 0) {
                user = queryUser(cursor);
            }

            if (cursor != null) cursor.close();
        }
        return user;
    }

    /**
     * 通过cursor来返回一个user对象
     * 注意:status字段未添加
     **/
    public User queryUser(Cursor cursor) {
        User user = null;
        if (cursor.moveToFirst()) {
            user = queryUser(cursor, cursor.getPosition());
        }
        return user;
    }

    public User queryUser(Cursor cursor, int position) {
        User user = null;
        if (cursor.moveToPosition(position)) {
            String userjson = cursor.getString(cursor.getColumnIndex("json"));
            user = User.parse(userjson);
        }
        return user;
    }

    public  String queryStatusJsonString(Status status){
        String json;
        Cursor cursor = mDatabase.rawQuery("select * from Status where idstr = ? ", new String[]{status.idstr});
        json = queryStatusJson(cursor);
        return json;
    }

    private String queryStatusJson(Cursor cursor) {
        String json = "";
        if (cursor.moveToFirst()) {
            json = cursor.getString(cursor.getColumnIndex("json"));
        }

    return json;
    }


    /**
     * 使用了单例模式
     **/
    public static DatabaseTool getInstance(Context context) {
        if (sDatabaseTool == null) {
            sContext = context.getApplicationContext();
            sDatabaseTool = new DatabaseTool(sContext);

        }
        return sDatabaseTool;
    }

    private static DatabaseTool sDatabaseTool;




}

/**
 * * 这是status表中的数据类型
 * 返回值字段	                    字段类型	    字段说明
 * created_at	                    string	    微博创建时间
 * id	                            int64	    微博ID
 * mid	                            int64	    微博MID
 * idstr	                        string	    字符串型的微博ID
 * text	                            string	    微博信息内容
 * source	                        string	    微博来源
 * favorited	                    boolean	    是否已收藏，true：是，false：否
 * truncated	                    boolean	    是否被截断，true：是，false：否
 * in_reply_to_status_id	        string	    （暂未支持）回复ID
 * in_reply_to_user_id	            string	    （暂未支持）回复人UID
 * in_reply_to_screen_name	        string	    （暂未支持）回复人昵称
 * thumbnail_pic	                string	    缩略图片地址，没有时不返回此字段
 * bmiddle_pic	                    string	    中等尺寸图片地址，没有时不返回此字段
 * original_pic	                    string	    原始图片地址，没有时不返回此字段
 * geo	                            object	    地理信息字段 详细
 * user	                            object	    微博作者的用户信息字段 详细
 * retweeted_status	                object	    被转发的原微博信息字段，当该微博为转发微博时返回 详细
 * reposts_count	                int	        转发数
 * comments_count	                int	        评论数
 * attitudes_count             	    int	        表态数
 * mlevel	                        int	        暂未支持
 * visible	                        object	    微博的可见性及指定可见分组信息。
 * 该object中type取值，
 * 0：普通微博，
 * 1：私密微博，
 * 3：指定分组微博，
 * 4：密友微博；list_id为分组的组号
 * pic_ids	                        object	     微博配图ID。
 * 多图时返回多图ID，用来拼接图片url。
 * 用返回字段thumbnail_pic的地址配上该返回字段的图片ID，即可得到多个图片url。
 * ad	                            object array	微博流内的推广微博ID
 **/

//            status = new Status();
////
////            /**这些是string和int数据*/
////            status.created_at = cursor.getString(cursor.getColumnIndex(Constants.STATUS_CREATED_AT));
////            status.id = cursor.getString(cursor.getColumnIndex(Constants.STATUS_ID));
////            status.mid = cursor.getString(cursor.getColumnIndex(Constants.STATUS_MID));
////            status.idstr = cursor.getString(cursor.getColumnIndex(Constants.STATUS_IDSTR));
////            status.text = cursor.getString(cursor.getColumnIndex(Constants.STATUS_TEXT));
////            status.source = cursor.getString(cursor.getColumnIndex(Constants.STATUS_SOURCE));
////            status.in_reply_to_status_id = cursor.getString(cursor.getColumnIndex(Constants.STATUS_IN_REPLY_TO_STATUS_ID));
////            status.in_reply_to_user_id = cursor.getString(cursor.getColumnIndex(Constants.STATUS_IN_REPLY_TO_USER_ID));
////            status.in_reply_to_screen_name = cursor.getString(cursor.getColumnIndex(Constants.STATUS_IN_REPLY_TO_SCAEEN_NAME));
////            status.thumbnail_pic = cursor.getString(cursor.getColumnIndex(Constants.STATUS_THUMNAIL_PIC));
////            status.bmiddle_pic = cursor.getString(cursor.getColumnIndex(Constants.STATUS_BMIDDLE_PIC));
////            status.original_pic = cursor.getString(cursor.getColumnIndex(Constants.STATUS_ORIGINAL_PIC));
////            status.reposts_count = cursor.getInt(cursor.getColumnIndex(Constants.STATUS_REPOSTS_COUNT));
////            status.comments_count = cursor.getInt(cursor.getColumnIndex(Constants.STATUS_COMMENTS_COUNT));
////            status.attitudes_count = cursor.getInt(cursor.getColumnIndex(Constants.STATUS_ATTITUDES_COUNT));
////            status.mlevel = cursor.getInt(cursor.getColumnIndex(Constants.STATUS_MLEVEL));
////
////            status.user = queryUser(TYPE_IDSTR, cursor.getString(cursor.getColumnIndex(Constants.STATUS_USER_IDSTR)));
////            status.retweeted_status = queryStatus(TYPE_IDSTR, cursor.getString(cursor.getColumnIndex(Constants.STATUS_RETWEETED_STATUS_IDSTR)));
////
////            /**这些是Boolean数据*/
////            status.favorited = Util.stringToBoolean(cursor.getString(cursor.getColumnIndex(Constants.STATUS_FAVORITED)));
////            status.truncated = Util.stringToBoolean(cursor.getString(cursor.getColumnIndex(Constants.STATUS_TRUNCATED)));
//
//            /** *
//             * 这些数据暂时没有添加
//             * geo	                object	   地理信息字段 详细
//             * retweeted_status	   object	    被转发的原微博信息字段，当该微博为转发微博时返回 详细 已经添加
//             * visible	                        object	    微博的可见性及指定可见分组信息。
//             * 该object中type取值，
//             * 0：普通微博，
//             * 1：私密微博，
//             * 3：指定分组微博，
//             * 4：密友微博；list_id为分组的组号
//             * pic_ids	                        object	     微博配图ID。
//             * 多图时返回多图ID，用来拼接图片url。
//             * 用返回字段thumbnail_pic的地址配上该返回字段的图片ID，即可得到多个图片url。
//             * ad	                            object array	微博流内的推广微博ID
//             * */

//values.put(Constants.STATUS_CREATED_AT, status.created_at);
//        values.put(Constants.STATUS_ID, status.id);
//        values.put(Constants.STATUS_MID, status.mid);
//        values.put(Constants.STATUS_IDSTR, status.idstr);
//        values.put(Constants.STATUS_TEXT, status.text);
//        values.put(Constants.STATUS_SOURCE, status.source);
//        values.put(Constants.STATUS_FAVORITED, Util.booleanToString(status.favorited));
//        values.put(Constants.STATUS_TRUNCATED, Util.booleanToString(status.truncated));
//
//        values.put(Constants.STATUS_IN_REPLY_TO_STATUS_ID, status.in_reply_to_status_id);
//        values.put(Constants.STATUS_IN_REPLY_TO_USER_ID, status.in_reply_to_user_id);
//        values.put(Constants.STATUS_IN_REPLY_TO_SCAEEN_NAME, status.in_reply_to_screen_name);
//        values.put(Constants.STATUS_THUMNAIL_PIC, status.thumbnail_pic);
//        values.put(Constants.STATUS_BMIDDLE_PIC, status.bmiddle_pic);
//        values.put(Constants.STATUS_ORIGINAL_PIC, status.original_pic);
//        values.put(Constants.STATUS_REPOSTS_COUNT, status.reposts_count);
//        values.put(Constants.STATUS_COMMENTS_COUNT, status.comments_count);
//        values.put(Constants.STATUS_ATTITUDES_COUNT, status.attitudes_count);
//        values.put(Constants.STATUS_MLEVEL, status.mlevel);
//
//        values.put(Constants.STATUS_USER_IDSTR, status.user_idstr);
//        values.put(Constants.STATUS_RETWEETED_STATUS_IDSTR, status.retweeted_status_idstr);

//
//user.id = cursor.getString(cursor.getColumnIndex(Constants.USER_ID));
//        user.idstr = cursor.getString(cursor.getColumnIndex(Constants.USER_IDSTR));
//        user.name = cursor.getString(cursor.getColumnIndex(Constants.USER_NAME));
//        user.screen_name = cursor.getString(cursor.getColumnIndex(Constants.USER_SCREEN_NAME));
//
//        user.province = cursor.getInt(cursor.getColumnIndex(Constants.USER_PROVINCE));
//        user.city = cursor.getInt(cursor.getColumnIndex(Constants.USER_CITY));
//
//        user.location = cursor.getString(cursor.getColumnIndex(Constants.USER_LOCATION));
//        user.description = cursor.getString(cursor.getColumnIndex(Constants.USER_DESCRIPTION));
//        user.url = cursor.getString(cursor.getColumnIndex(Constants.USER_URL));
//        user.profile_image_url = cursor.getString(cursor.getColumnIndex(Constants.USER_PROFILE_IMAGE_URL));
//        user.profile_url = cursor.getString(cursor.getColumnIndex(Constants.USER_PROFILE_URL));
//        user.domain = cursor.getString(cursor.getColumnIndex(Constants.USER_DOMAIN));
//        user.weihao = cursor.getString(cursor.getColumnIndex(Constants.USER_WEIHAO));
//        user.gender = cursor.getString(cursor.getColumnIndex(Constants.USER_GENDER));
//
//        user.followers_count = cursor.getInt(cursor.getColumnIndex(Constants.USER_FOLLOWERS_COUNT));
//        user.friends_count = cursor.getInt(cursor.getColumnIndex(Constants.USER_FRIENDS_COUNT));
//        user.statuses_count = cursor.getInt(cursor.getColumnIndex(Constants.USER_STATUSES_COUNT));
//        user.favourites_count = cursor.getInt(cursor.getColumnIndex(Constants.USER_FAVORITES_COUNT));
//
//        user.created_at = cursor.getString(cursor.getColumnIndex(Constants.USER_CREATED_AT));
//
//        user.remark = cursor.getString(cursor.getColumnIndex(Constants.USER_REMARK));
//        /**user.status = ?*/
//        user.avatar_large = cursor.getString(cursor.getColumnIndex(Constants.USER_AVATAR_LARGE));
//        user.avatar_hd = cursor.getString(cursor.getColumnIndex(Constants.USER_AVATAR_HD));
//        user.verified_reason = cursor.getString(cursor.getColumnIndex(Constants.USER_VERIFIED_REASON));
//
//        user.online_status = cursor.getInt(cursor.getColumnIndex(Constants.USER_ONLINE_STATUS));
//        user.bi_followers_count = cursor.getInt(cursor.getColumnIndex(Constants.USER_BI_FOLLOWERS_COUNT));
//
//        user.lang = cursor.getString(cursor.getColumnIndex(Constants.USER_LANG));
//
//        /**这些字段是Boolean类型的数据,在database中存为text类型,需要转换为Boolean类型*/
//        user.follow_me = Util.stringToBoolean
//        (cursor.getString(cursor.getColumnIndex(Constants.USER_FOLLOW_ME)));
//        user.allow_all_comment = Util.stringToBoolean
//        (cursor.getString(cursor.getColumnIndex(Constants.USER_ALLOW_ALL_COMMENT)));
//        user.geo_enabled = Util.stringToBoolean(
//        cursor.getString(cursor.getColumnIndex(Constants.USER_GEO_ENABLED)));
//        user.allow_all_act_msg = Util.stringToBoolean
//        (cursor.getString(cursor.getColumnIndex(Constants.USER_ALL_ACT_MSG)));
//        user.verified = Util.stringToBoolean
//        (cursor.getString(cursor.getColumnIndex(Constants.USER_VERIFIED)));
//        user.following = Util.stringToBoolean
//        (cursor.getString(cursor.getColumnIndex(Constants.USER_FOLLOWING)));


//        values.put(Constants.USER_SCREEN_NAME, user.screen_name);
//        values.put(Constants.USER_NAME, user.name);
//        values.put(Constants.USER_PROVINCE, user.province);
//        values.put(Constants.USER_CITY, user.city);
//        values.put(Constants.USER_LOCATION, user.location);
//        values.put(Constants.USER_DESCRIPTION, user.description);
//        values.put(Constants.USER_URL, user.url);
//        values.put(Constants.USER_PROFILE_IMAGE_URL, user.profile_image_url);
//        values.put(Constants.USER_PROFILE_URL, user.profile_url);
//        values.put(Constants.USER_DOMAIN, user.domain);
//        values.put(Constants.USER_WEIHAO, user.weihao);
//        values.put(Constants.USER_GENDER, user.gender);
//        values.put(Constants.USER_FOLLOWERS_COUNT, user.followers_count);
//        values.put(Constants.USER_FRIENDS_COUNT, user.friends_count);
//        values.put(Constants.USER_STATUSES_COUNT, user.statuses_count);
//        values.put(Constants.USER_FAVORITES_COUNT, user.favourites_count);
//        values.put(Constants.USER_CREATED_AT, user.created_at);
//        values.put(Constants.USER_FOLLOWING, user.following);
//        values.put(Constants.USER_ALLOW_ALL_COMMENT, Util.booleanToString(user.allow_all_act_msg));
//        values.put(Constants.USER_GEO_ENABLED, Util.booleanToString(user.geo_enabled));
//        values.put(Constants.USER_VERIFIED, Util.booleanToString(user.verified));
//        values.put(Constants.USER_VERIFIED_TYPE, user.verified_type);
////        values.put(Constants.USER_SCREEN_NAME, user.status);
//        values.put(Constants.USER_ALLOW_ALL_COMMENT, Util.booleanToString(user.allow_all_comment));
//        values.put(Constants.USER_AVATAR_LARGE, user.avatar_large);
//        values.put(Constants.USER_AVATAR_HD, user.avatar_hd);
//        values.put(Constants.USER_VERIFIED_REASON, user.verified_reason);
//        values.put(Constants.USER_FOLLOWING, Util.booleanToString(user.follow_me));
//        values.put(Constants.USER_ONLINE_STATUS, user.online_status);
//        values.put(Constants.USER_BI_FOLLOWERS_COUNT, user.bi_followers_count);
//        values.put(Constants.USER_LANG, user.lang);


