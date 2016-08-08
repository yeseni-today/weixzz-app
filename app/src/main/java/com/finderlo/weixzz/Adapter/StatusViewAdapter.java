package com.finderlo.weixzz.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.finderlo.weixzz.SinaAPI.openapi.models.Status;
import com.finderlo.weixzz.Widgt.StatusView.StatusViewitem;

import java.util.ArrayList;

/**
 * Created by Finderlo on 2016/8/8.
 */
public class StatusViewAdapter extends BaseAdapter {
    private  ArrayList<Status> mDataList;
    private Context mContext;

    public StatusViewAdapter(Context context,ArrayList<Status> list){
        mContext = context;
        mDataList = list;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Status getItem(int i) {
        return mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        StatusViewitem statusView = new StatusViewitem(mContext);
        Status status  = getItem(i);
        statusView.setStatus(mContext,status);
        return statusView;
    }
}
