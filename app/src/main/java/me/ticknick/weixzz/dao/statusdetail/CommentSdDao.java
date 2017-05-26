package me.ticknick.weixzz.dao.statusdetail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import me.ticknick.weixzz.Constants;
import me.ticknick.weixzz.dao.HttpClientUtils;
import me.ticknick.weixzz.dao.UrlConstants;
import me.ticknick.weixzz.dao.WeiboParameters;
import me.ticknick.weixzz.database.table.CommentStatusDetailTable;
import me.ticknick.weixzz.model.CommentListModel;

/**
 * Created by Finderlo on 2016/8/24.
 * 评论微博的dao层
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
