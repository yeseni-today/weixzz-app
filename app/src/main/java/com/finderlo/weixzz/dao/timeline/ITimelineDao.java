package com.finderlo.weixzz.dao.timeline;

import android.database.Cursor;

import com.finderlo.weixzz.model.model.BaseListModel;

/**
 * Created by Finderlo on 2016/8/20.
 */

public interface ITimelineDao {

    public void loadFromCache();
    public void cache();
    public void load(boolean isRefresh);
    public Cursor query();
    public BaseListModel getList();
}
