package com.finderlo.weixzz.Dao;

import android.database.sqlite.SQLiteDatabase;

import com.finderlo.weixzz.Database.DatabaseHelper;
import com.finderlo.weixzz.base.WeiException;
import com.finderlo.weixzz.model.bean.AbsBean;

import java.util.List;


/**
 * Created by Finderlo on 2016/8/19.
 */

public abstract class AbsDao {

    final String TAG = this.getClass().getSimpleName();

    private String CURRENT_TABLE_NAME = this.getTABLE_NAME();

    public static final String TYPE_ID = " id ";
    public static final String TYPE_IDSTR = " idstr ";

    static final String JSON = " json ";

    static DatabaseHelper sDatabaseHelper;
    static SQLiteDatabase sDatabase;

    AbsDao() {
        sDatabaseHelper = DatabaseHelper.getInstance();
        sDatabase = sDatabaseHelper.getWritableDatabase();
    }


    boolean isDataAlreadyExist(String type, String id) {
        if (!(TYPE_ID.equals(type) || TYPE_IDSTR.equals(type) )) {
            return false;
        }
        if (sDatabase.rawQuery
                ("select * from " + CURRENT_TABLE_NAME + " where " + type + " = " + id + " ", null)
                .moveToFirst()) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public abstract List<? extends AbsBean> query();

    @SuppressWarnings("unchecked")
    public abstract List<? extends AbsBean> query(int count);

    public abstract void insert(AbsBean bean) throws WeiException;

    public abstract void insert(List<? extends AbsBean> list) throws WeiException;

    public abstract void clear();

    public abstract String getTABLE_NAME();

    abstract void isCurrentBean(AbsBean bean) throws WeiException;


}
