package com.finderlo.weixzz.dao.timeline;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.finderlo.weixzz.Constants;
import com.finderlo.weixzz.database.DatabaseHelper;
import com.finderlo.weixzz.database.Table.StatusTable;
import com.finderlo.weixzz.model.model.BaseListModel;
import com.google.gson.Gson;


/**
 * Created by Finderlo on 2016/8/19.
 */

public abstract class BaseTimelineDao<BeanList extends BaseListModel> implements ITimelineDao {

    protected  final String TAG = this.getClass().getSimpleName();

    protected int mCurrentPage = 0;


    protected DatabaseHelper mDatabaseHelper;
    protected SQLiteDatabase mDatabase;
    protected BeanList mListModel;

    private Context mContext;


    BaseTimelineDao(Context context) {
        mDatabaseHelper = DatabaseHelper.getInstance(context);
        mDatabase = mDatabaseHelper.getWritableDatabase();
        mContext = context;
    }

    @Override
    public void loadFromCache() {
        Cursor cursor = query();

        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            mListModel = (BeanList) new Gson().fromJson(cursor.getString(cursor.getColumnIndex(StatusTable.JSON)), getListClass());
            mCurrentPage = mListModel.getSize() / Constants.HOME_TIMELINE_PAGE_SIZE;
//            spanAll(mListModel);
        } else {
            try {
                mListModel = (BeanList) getListClass().newInstance();
            } catch (Exception e) {

            }
        }
    }

    @Override
    public abstract void cache();

    @Override
    public void load(boolean isRefresh) {
        if (isRefresh) {
            mCurrentPage = 0;
        }
        BeanList list = load();
//        dealStatus(list);
        if (list == null) {
            return;
        }
        if (isRefresh) {
            mListModel.getList().clear();
        }
        mListModel.addAll(false, list);
//        spanAll(mListModel);
    }

    public abstract BeanList load();


    @Override
    public abstract Cursor query();

    @Override
    public BaseListModel getList() {
        return mListModel;
    }

    protected abstract Class<? extends BeanList> getListClass();
}
