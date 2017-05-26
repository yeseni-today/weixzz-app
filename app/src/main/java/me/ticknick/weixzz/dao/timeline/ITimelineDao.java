package me.ticknick.weixzz.dao.timeline;


import android.database.Cursor;

import me.ticknick.weixzz.model.BaseListModel;


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
