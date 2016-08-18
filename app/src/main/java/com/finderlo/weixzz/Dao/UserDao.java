package com.finderlo.weixzz.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.finderlo.weixzz.Constants;
import com.finderlo.weixzz.Database.DatabaseHelper;
import com.finderlo.weixzz.model.bean.User;

/**
 * Created by Finderlo on 2016/8/16.
 * Dao Data Access Object
 */
public class UserDao {
    private static final String TAG = "UserDao";
    private static final String TABLE_NAME_USER = " User ";
    public static final String TYPE_ID = " id ";
    public static final String TYPE_IDSTR = " idstr ";

    private static DatabaseHelper sDatabaseHelper;
    private static UserDao sUserDao;
    private static SQLiteDatabase sDatabase;


    private UserDao(){
        sDatabaseHelper = DatabaseHelper.getInstance();
        sDatabase = sDatabaseHelper.getWritableDatabase();
    }

    public static UserDao getInstance() {
        if (sUserDao == null) {
            sUserDao = new UserDao();
        }
        return sUserDao;
    }
    /**
     * 插入数据库中一个用户信息
     *
     * @param user
     * @param userjson 这是数据代表的json数据
     **/
    public void insertUser(User user, String userjson) {
        if (isDataAlreadyExist(TABLE_NAME_USER, TYPE_IDSTR, user.idstr)) {
            Log.i(TAG, "insertUser: 用户数据已经存在,没有插入");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(Constants.USER_ID, user.id);
        values.put(Constants.USER_IDSTR, user.idstr);
        values.put("json", userjson);

        sDatabase.insert(TABLE_NAME_USER, null, values);
        Log.i(TAG, "insertUser: 用户数据已经存在");
    }

    /**
     * 通过给定的idstr来查询用户
     **/
    public User queryUser(String user_id_type, String user_id) {
        User user = null;
        if (TYPE_IDSTR.equals(user_id_type) && !user_id.equals("")) {
            Cursor cursor = sDatabase.rawQuery("select * from User where idstr =  " + user_id, null);
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

    /**
     * @param tablename the data belong to table
     * @param type      the type for id or idstr
     * @param id        the id
     * @return isDataAlreadyExist
     **/
    private boolean isDataAlreadyExist(String tablename, String type, String id) {
        if (!(TYPE_ID.equals(type) || TYPE_IDSTR.equals(type))) {
            return false;
        }

        if (sDatabase.rawQuery
                ("select * from " + tablename + " where " + type + " = " + id + " ", null)
                .moveToFirst()) {
            return true;
        }
        return false;
    }


}
