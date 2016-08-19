package com.finderlo.weixzz.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.finderlo.weixzz.Constants;
import com.finderlo.weixzz.base.WeiException;
import com.finderlo.weixzz.model.bean.AbsBean;
import com.finderlo.weixzz.model.bean.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finderlo on 2016/8/16.
 * Dao Data Access Object
 */
public class StatusDao extends AbsDao{

    private static final String TABLE_NAME = " Status ";
    static final String TYPE_MID = " mid ";

    private static StatusDao sStatusDao;


    private StatusDao() {}

    @Override
    public List<? extends AbsBean> query() {
        return query(0);
    }

    @Override
    public List<? extends AbsBean> query(int count) {
        Cursor cursor = sDatabase.query(TABLE_NAME, null, null, null, null, null, null, null);
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

    @Override
    public void insert(AbsBean bean) throws WeiException {
        isCurrentBean(bean);

        Status status = (Status) bean;
        if (isDataAlreadyExist(TYPE_IDSTR, status.idstr)) {
            Log.i(TAG, "insertStatus: 微博数据已经存在,没有插入");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(Constants.STATUS_ID, status.id);
        values.put(Constants.STATUS_MID, status.mid);
        values.put(Constants.STATUS_IDSTR, status.idstr);
        values.put(JSON, status.getJsonString());

        sDatabase.insert(TABLE_NAME, null, values);
        Log.i(TAG, "insertStatus: 微博数据插入成功");
    }

    @Override
    public void insert(List<? extends AbsBean> list) throws WeiException {
        ArrayList<Status> statuses = (ArrayList<Status>) list;
        for (Status status:statuses){
            insert(status);
        }
    }

    @Override
    public void clear() {

    }

    public static StatusDao getInstance() {
        if (sStatusDao == null) {
            sStatusDao = new StatusDao();
        }
        return sStatusDao;
    }







    /**
     * 通过给定的idstr来查询微博
     **/
    public Status queryStatus(String status_id_type, String status_id) {
        Status status = null;
        if (TYPE_IDSTR.equals(status_id_type) && !"".equals(status_id)) {
            Cursor cursor = sDatabase.rawQuery("select * from "+TABLE_NAME+" where "+status_id_type+" = "+status_id+" ",
                    null);
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



    @Override
    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    @Override
    void isCurrentBean(AbsBean bean) throws WeiException {
        if (! (bean instanceof Status))
            throw new WeiException("bean is not cast");
    }
}
