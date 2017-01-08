package com.finderlo.weixzz.dao.statusdetail;

import android.database.Cursor;

import com.finderlo.weixzz.model.model.BaseListModel;

/**
 * Created by Finderlo on 2016/8/24.
 */

public interface IStatusDetailDao {
    public void loadFromCache();
    public void cache();
    public void load(boolean isRefresh);
    public Cursor query();
    public BaseListModel getList();
}
