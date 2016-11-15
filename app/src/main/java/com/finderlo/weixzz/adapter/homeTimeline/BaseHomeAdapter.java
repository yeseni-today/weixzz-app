package com.finderlo.weixzz.adapter.homeTimeline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.finderlo.weixzz.model.model.BaseListModel;

/**
 * Created by Finderlo on 2016/8/20.
 */

public abstract class BaseHomeAdapter< T extends BaseListModel > extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    protected T mListModel;


    public BaseHomeAdapter(Context context, T t) {
        mContext = context;
        mListModel = t;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public abstract int getItemCount(
    	);

    public T getListModel(){
        return mListModel;
    }
}
