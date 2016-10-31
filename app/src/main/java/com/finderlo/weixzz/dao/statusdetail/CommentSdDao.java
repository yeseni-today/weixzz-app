package com.finderlo.weixzz.dao.statusdetail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.finderlo.weixzz.Constants;
import com.finderlo.weixzz.dao.HttpClientUtils;
import com.finderlo.weixzz.dao.UrlConstants;
import com.finderlo.weixzz.dao.WeiboParameters;
import com.finderlo.weixzz.database.Table.CommentStatusDetailTable;
import com.finderlo.weixzz.model.model.CommentListModel;
import com.google.gson.Gson;

import java.io.IOException;

/**
 * Created by Finderlo on 2016/8/24.
 */

public class CommentSdDao extends BaseStatusDetailDao<CommentListModel> {

    public long mStatusId;

    public CommentSdDao(Context context, long statusId) {
        super(context);
        mStatusId = statusId;
    }

    @Override
    public CommentListModel load() {
        return getCommentByStatusId(mStatusId, Constants.HOME_TIMELINE_PAGE_SIZE, ++mCurrentPage);
    }

    @Override
    public void cache() {
        mDatabase.beginTransaction();
        mDatabase.delete(CommentStatusDetailTable.NAME,
                CommentStatusDetailTable.STATUS_ID + " = ?",
                new String[]{String.valueOf(mStatusId)});
        ContentValues values = new ContentValues();
        values.put(CommentStatusDetailTable.STATUS_ID, mStatusId);
        values.put(CommentStatusDetailTable.JSON, new Gson().toJson(mModelList));
        mDatabase.insert(CommentStatusDetailTable.NAME, null, values);
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }


    @Override
    public Cursor query() {
        return mDatabase.rawQuery("select * from "
                + CommentStatusDetailTable.NAME + " where " + CommentStatusDetailTable.STATUS_ID + "= '" + mStatusId + "'", null);
    }

    @Override
    protected Class<? extends CommentListModel> getListClass() {
        return CommentListModel.class;
    }

    public CommentListModel getCommentByStatusId(long statusId, int count, int page) {
        WeiboParameters param = new WeiboParameters();
        param.put("count", count);
        param.put("page", page);
        param.put("id", statusId);

        try {
            String json = HttpClientUtils.doGetRequstWithAceesToken(UrlConstants.COMMENTS_SHOW, param);
            return new Gson().fromJson(json, CommentListModel.class);
        } catch (Exception e) {
            Log.d(TAG, "getCommentByStatusId: get comment show fail" + e.getClass().getSimpleName());
            e.printStackTrace();
            return null;
        }
    }
}
