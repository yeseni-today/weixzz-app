package com.finderlo.weixzz.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.finderlo.weixzz.Constants;
import com.finderlo.weixzz.Database.DatabaseHelper;
import com.finderlo.weixzz.Model.Status;

import java.util.ArrayList;

/**
 * Created by Finderlo on 2016/8/16.
 * Dao Data Access Object
 */
public class StatusDao {
    private static final String TAG = "StautsDao";
    private static final String TABLE_NAME_STATUS = " Status ";
    public static final String TYPE_ID = " id ";
    public static final String TYPE_IDSTR = " idstr ";
    public static final String TYPE_MID = " mid ";

    private static DatabaseHelper sDatabaseHelper;
    private static StatusDao sStatusDao;
    private static SQLiteDatabase sDatabase;


    private StatusDao() {
        sDatabaseHelper = DatabaseHelper.getInstance();
        sDatabase = sDatabaseHelper.getWritableDatabase();
    }

    public static StatusDao getInstance() {
        if (sStatusDao == null) {
            sStatusDao = new StatusDao();
        }
        return sStatusDao;
    }

    /**
     * insert a status object to database table.table name is "Status"
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

        sDatabase.insert(TABLE_NAME_STATUS, null, values);
        Log.i(TAG, "insertStatus: 微博数据插入成功");

    }

    /**
     * 从数据库中查询所有的微博微博
     **/
    public ArrayList<Status> queryStatuses() {

        Cursor cursor = sDatabase.query(TABLE_NAME_STATUS, null, null, null, null, null, null, null);
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
     * 从数据库中查询所有的微博微博
     **/
    public ArrayList<Status> queryLastStatuses(int count) {

        Cursor cursor = sDatabase.query(TABLE_NAME_STATUS, null, null, null, null, null, null, null);
        ArrayList<Status> list = new ArrayList<Status>();
        if (cursor.moveToLast()) {
            do {
                Status status = queryStatus(cursor, cursor.getPosition());
                list.add(status);
                if (list.size()==count) break;

            } while (cursor.moveToPrevious());
        }
        if (cursor != null) cursor.close();
        return list;
    }


    /**
     * 通过给定的idstr来查询微博
     **/
    public Status queryStatus(String status_id_type, String status_id) {
        Status status = null;
        if ("idstr".equals(status_id_type) && !"".equals(status_id) && null != status_id) {
            Cursor cursor = sDatabase.rawQuery("select * from Status where idstr = ? ", new String[]{status_id});
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
    private Status queryStatus(Cursor cursor) {
        Status status = null;
        if (cursor.moveToFirst()) {
            status = queryStatus(cursor, cursor.getPosition());
        }
        return status;
    }

    /**
     * @param tablename the data belong to table
     * @param type      the type for id or idstr
     * @param id        the id
     * @return isDataAlreadyExist
     **/
    private boolean isDataAlreadyExist(String tablename, String type, String id) {
        if (!(TYPE_ID.equals(type) || TYPE_IDSTR.equals(type) || TYPE_MID.equals(type))) {
            return false;
        }
        if (sDatabase.rawQuery
                ("select * from " + TABLE_NAME_STATUS + " where " + type + " = " + id + " ", null)
                .moveToFirst()) {
            return true;
        }
        return false;
    }


}
