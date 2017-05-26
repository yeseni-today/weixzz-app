package me.ticknick.weixzz.dao.statusdetail;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;

import me.ticknick.weixzz.Constants;
import me.ticknick.weixzz.database.DatabaseHelper;
import me.ticknick.weixzz.database.table.StatusTable;
import me.ticknick.weixzz.model.BaseListModel;

/**
 * Created by Finderlo on 2016/8/24.
 */

public abstract class BaseStatusDetailDao<ModelList extends BaseListModel> implements IStatusDetailDao {

    protected  final String TAG = this.getClass().getSimpleName();

    protected Context mContext;
    protected ModelList mModelList;
    protected int mCurrentPage = 0;


    protected DatabaseHelper mDatabaseHelper;
    protected SQLiteDatabase mDatabase;

    BaseStatusDetailDao(Context context){
        mContext = context;
        mDatabaseHelper = DatabaseHelper.getInstance(context);
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    @Override
    public void loadFromCache() {
        Cursor cursor = query();

        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            mModelList = (ModelList) new Gson().fromJson(cursor.getString(cursor.getColumnIndex(StatusTable.JSON)), getListClass());
            mCurrentPage = mModelList.getSize() / Constants.HOME_TIMELINE_PAGE_SIZE;
//            spanAll(mListModel);
        } else {
            try {
                mModelList = (ModelList) getListClass().newInstance();
            } catch (Exception e) {

            }
        }
    }

    @Override
    public abstract void cache() ;

    @Override
    public void load(boolean isRefresh) {
        if (isRefresh) {
            mCurrentPage = 0;
        }
        ModelList list = load();
//        dealStatus(list);
        if (list == null) {
            return;
        }
        if (isRefresh) {
            mModelList.getList().clear();
        }
        mModelList.addAll(false, list);
        Log.d(TAG, "load: mModelList.size "+mModelList.getSize());
//        spanAll(mListModel);
    }

    public abstract ModelList load();

    @Override
    public abstract Cursor query();

    @Override
    public BaseListModel getList() {
        return mModelList;
    }

    protected abstract Class<? extends ModelList> getListClass();

}
