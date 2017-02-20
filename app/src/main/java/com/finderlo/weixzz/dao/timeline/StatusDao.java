package com.finderlo.weixzz.dao.timeline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.Log;

import com.finderlo.weixzz.Constants;
import com.finderlo.weixzz.dao.HttpClientUtils;
import com.finderlo.weixzz.dao.UrlConstants;
import com.finderlo.weixzz.dao.WeiboParameters;
import com.finderlo.weixzz.database.Table.StatusTable;
import com.finderlo.weixzz.model.model.StatusListModel;
import com.finderlo.weixzz.model.model.StatusModel;
import com.google.gson.Gson;

import static com.finderlo.weixzz.BuildConfig.DEBUG;

/**
 * Created by Finderlo on 2016/8/16.
 * Dao Data Access Object
 * 时间线微博dao层
 */
public class StatusDao extends BaseTimelineDao<StatusListModel> {


    public static final String GROUP_BILATERAL = "groups_bilateral";
    public static final String GROUP_ALL = "groups_all";
    private static String TAG = "StatusDao";

    public String mGroupId = GROUP_ALL;

    public StatusDao(Context context, String groupId) {
        super(context);
        if (mGroupId == null || "".equals(groupId)) {

        } else {
            mGroupId = groupId;
        }

    }


    @Override
    public void cache() {
        mDatabase.beginTransaction();
        mDatabase.delete(StatusTable.NAME, StatusTable.GROUP_ID + " =? ", new String[]{mGroupId});
        ContentValues values = new ContentValues();
        values.put(StatusTable.GROUP_ID, mGroupId);
        values.put(StatusTable.JSON, new Gson().toJson(mListModel));
        mDatabase.insert(StatusTable.NAME, null, values);
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    @Override
    public StatusListModel load() {
        StatusListModel model;
        if (mGroupId.equals(GROUP_ALL)) {
            model = getHomeTimeLine(Constants.HOME_TIMELINE_PAGE_SIZE, ++mCurrentPage);
        } else if (mGroupId.equals(GROUP_BILATERAL)) {
            model = getBilateralTimeLine(Constants.HOME_TIMELINE_PAGE_SIZE, ++mCurrentPage);
        } else {
            model = getGroupTimelines(mGroupId, Constants.HOME_TIMELINE_PAGE_SIZE, ++mCurrentPage);
        }

        return model;
    }

    @Override
    public void loadMore() {
        int size = mListModel.getSize();
        StatusListModel modelList;
        if (size == 0){
            modelList= getHomeTimeLine(0,Constants.HOME_TIMELINE_PAGE_SIZE,1);
        }else {
            StatusModel statusModel = mListModel.get(size - 1);
            modelList = getHomeTimeLine(statusModel.id,Constants.HOME_TIMELINE_PAGE_SIZE,1);
        }
        mListModel.addAll(false,modelList);
    }

    @Override
    public Cursor query() {
        return mDatabase.rawQuery("select * from " + StatusTable.NAME
                + " where "
                + StatusTable.GROUP_ID
                + " = '" + mGroupId + "' ", null);
    }


    protected Class<? extends StatusListModel> getListClass() {
        return StatusListModel.class;
    }


    public static StatusListModel getHomeTimeLine(int count, int page) {
        return getHomeTimeLine(0, count, page);
    }

    @Nullable
    public static StatusListModel getHomeTimeLine(long maxId, int count, int page) {
        WeiboParameters params = new WeiboParameters();
        params.put("count", count);
        params.put("page", page);
        params.put("max_id", maxId);

        try {
            String json = HttpClientUtils.doGetRequstWithAceesToken(UrlConstants.HOME_TIMELINE, params);
            return new Gson().fromJson(json, StatusListModel.class);
        } catch (Exception e) {
            if (DEBUG) {
                Log.d(TAG, "Can't get  home timeline, " + e.getClass().getSimpleName());
                Log.d(TAG, Log.getStackTraceString(e));
            }
            return null;
        }
    }

    @Nullable
    public static StatusListModel getGroupTimelines(String groupId, int count, int page) {
        WeiboParameters params = new WeiboParameters();
        params.put("list_id", groupId);
        params.put("count", count);
        params.put("page", page);

        try {
            String json = HttpClientUtils.doGetRequstWithAceesToken(UrlConstants.FRIENDSHIPS_GROUPS_TIMELINE, params);
            return new Gson().fromJson(json, StatusListModel.class);
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(TAG, "Cannot get group timeline");
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }

        return null;
    }

    @Nullable
    public static StatusListModel getBilateralTimeLine(int count, int page) {
        WeiboParameters params = new WeiboParameters();
        params.put("count", count);
        params.put("page", page);

        try {
            String json = HttpClientUtils.doGetRequstWithAceesToken(UrlConstants.BILATERAL_TIMELINE, params);
            return new Gson().fromJson(json, StatusListModel.class);
        } catch (Exception e) {
            if (DEBUG) {
                Log.d(TAG, "Cannot get bilateral timeline, " + e.getClass().getSimpleName());
                Log.d(TAG, Log.getStackTraceString(e));
            }
            return null;
        }
    }
}
