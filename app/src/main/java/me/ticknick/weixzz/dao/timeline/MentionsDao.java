package me.ticknick.weixzz.dao.timeline;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;

import me.ticknick.weixzz.Constants;
import me.ticknick.weixzz.dao.HttpClientUtils;
import me.ticknick.weixzz.dao.UrlConstants;
import me.ticknick.weixzz.dao.WeiboParameters;
import me.ticknick.weixzz.database.table.MentionsTable;
import me.ticknick.weixzz.model.StatusListModel;

import static me.ticknick.weixzz.BuildConfig.DEBUG;


/**
 * Created by Finderlo on 2016/8/16.
 * Dao Data Access Object
 * 提到我的dao层
 */
public class MentionsDao extends BaseTimelineDao<StatusListModel> {


    public MentionsDao(Context context) {
        super(context);
    }


    @Override
    public void cache() {
        mDatabase.beginTransaction();
        mDatabase.delete(MentionsTable.NAME," 1 = 1 ",null);
        ContentValues values = new ContentValues();
        values.put(MentionsTable.JSON,new Gson().toJson(mListModel));
        mDatabase.insert(MentionsTable.NAME,null,values);
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    @Override
    public StatusListModel load() {
        StatusListModel listModel ;
        listModel = getMentionsTimeline(Constants.HOME_TIMELINE_PAGE_SIZE,++mCurrentPage);
        return listModel;
    }

    @Override
    public void loadMore() {

    }

    private StatusListModel getMentionsTimeline(int count, int page) {
        WeiboParameters param = new WeiboParameters();
        param.put("count",count);
        param.put("page",page);

        try {
            String json = HttpClientUtils.doGetRequstWithAceesToken(UrlConstants.MENTIONS,param);
            return new Gson().fromJson(json,StatusListModel.class);
        } catch (Exception e) {
            if (DEBUG) {
                Log.d(TAG, "Can't get comment home timeline, " + e.getClass().getSimpleName());
                Log.d(TAG, Log.getStackTraceString(e));
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Cursor query() {
        return mDatabase.rawQuery("select * from "+MentionsTable.NAME,null);
    }

    @Override
    protected Class<? extends StatusListModel> getListClass() {
        return StatusListModel.class;
    }
}
