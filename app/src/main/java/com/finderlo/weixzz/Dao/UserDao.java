//package com.finderlo.weixzz.dao;
//
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.util.Log;
//
//import com.finderlo.weixzz.Constants;
//import com.finderlo.weixzz.dao.timeline.BaseTimelineDao;
//import com.finderlo.weixzz.model.bean.StatusListModel;
//import com.finderlo.weixzz.model.bean.UserModel;
//
//import static com.finderlo.weixzz.database.Table.StatusTable.JSON;
//
///**
// * Created by Finderlo on 2016/8/16.
// * Dao Data Access Object
// */
//public class UserDao  {
//    private static final String TAG = "UserDao";
//    private static final String TABLE_NAME = " UserModel ";
//
//    private static UserDao sUserDao;
//
//    private UserDao(){}
//
//
//    public Cursor query() {
//        return query(0);
//    }
//
//
//    public StatusListModel query(int count) {
//        return null;
//    }
//
//
//    public void insert(UserModel bean){
//        UserModel mUserModel = (UserModel) bean;
//        if (isDataAlreadyExist(TYPE_IDSTR, mUserModel.idstr)) {
//            Log.i(TAG, "insertUser: 用户数据已经存在,没有插入");
//            return;
//        }
//
//        ContentValues values = new ContentValues();
//        values.put(Constants.USER_ID, mUserModel.id);
//        values.put(Constants.USER_IDSTR, mUserModel.idstr);
//        values.put(JSON, mUserModel.getJsonString());
//
//        sDatabase.insert(TABLE_NAME, null, values);
//        Log.i(TAG, "insertUser: 用户数据已经存在");
//    }
//
//
//    public void insert(StatusListModel list) {
//
//    }
//
//
//
//    public void clear() {
//
//    }
//
//
//    public String getTABLE_NAME() {
//        return TABLE_NAME;
//    }
//
//    public static UserDao getInstance() {
//        if (sUserDao == null) {
//            sUserDao = new UserDao();
//        }
//        return sUserDao;
//    }
//
//
//    /**
//     * 通过给定的idstr来查询用户
//     **/
//    public UserModel queryUser(String user_id_type, String user_id) {
//        UserModel mUserModel = null;
//        if (TYPE_IDSTR.equals(user_id_type) && !user_id.equals("")) {
//            Cursor cursor = sDatabase.rawQuery("select * from UserModel where idstr =  " + user_id, null);
//            if (cursor.getCount() > 0) {
//                mUserModel = queryUser(cursor);
//            }
//
//            if (cursor != null) cursor.close();
//        }
//        return mUserModel;
//    }
//
//    /**
//     * 通过cursor来返回一个user对象
//     * 注意:status字段未添加
//     **/
//    public UserModel queryUser(Cursor cursor) {
//        UserModel mUserModel = null;
//        if (cursor.moveToFirst()) {
//            mUserModel = queryUser(cursor, cursor.getPosition());
//        }
//        return mUserModel;
//    }
//
//    public UserModel queryUser(Cursor cursor, int position) {
//        UserModel mUserModel = null;
//        if (cursor.moveToPosition(position)) {
//            String userjson = cursor.getString(cursor.getColumnIndex("json"));
//            mUserModel = UserModel.parse(userjson);
//        }
//        return mUserModel;
//    }
//
//
//
//
//}
