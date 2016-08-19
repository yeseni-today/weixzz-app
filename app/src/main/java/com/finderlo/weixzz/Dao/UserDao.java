package com.finderlo.weixzz.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.finderlo.weixzz.Constants;
import com.finderlo.weixzz.base.WeiException;
import com.finderlo.weixzz.model.bean.AbsBean;
import com.finderlo.weixzz.model.bean.Status;
import com.finderlo.weixzz.model.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finderlo on 2016/8/16.
 * Dao Data Access Object
 */
public class UserDao extends AbsDao {
    private static final String TAG = "UserDao";
    private static final String TABLE_NAME = " User ";

    private static UserDao sUserDao;

    private UserDao(){}

    @Override
    public List<? extends AbsBean> query() {
        return query(0);
    }

    @Override
    public List<? extends AbsBean> query(int count) {
        return null;
    }

    @Override
    public void insert(AbsBean bean) throws WeiException {
        isCurrentBean(bean);
        User user = (User) bean;
        if (isDataAlreadyExist(TYPE_IDSTR, user.idstr)) {
            Log.i(TAG, "insertUser: 用户数据已经存在,没有插入");
            return;
        }

        ContentValues values = new ContentValues();
        values.put(Constants.USER_ID, user.id);
        values.put(Constants.USER_IDSTR, user.idstr);
        values.put(JSON, user.getJsonString());

        sDatabase.insert(TABLE_NAME, null, values);
        Log.i(TAG, "insertUser: 用户数据已经存在");
    }

    @Override
    public void insert(List<? extends AbsBean> list) throws WeiException {
        ArrayList<User> users = (ArrayList<User>) list;
        for (User user:users){
            insert(user);
        }
    }

    @Override
    public void clear() {

    }


    @Override
    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    @Override
    void isCurrentBean(AbsBean bean) throws WeiException {
        if (! (bean instanceof User))
            throw new WeiException("bean is not cast");
    }

    public static UserDao getInstance() {
        if (sUserDao == null) {
            sUserDao = new UserDao();
        }
        return sUserDao;
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




}
