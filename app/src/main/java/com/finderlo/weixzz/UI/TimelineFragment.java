package com.finderlo.weixzz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.finderlo.weixzz.adapter.StatusViewRecyclerAdapter;
import com.finderlo.weixzz.Constants;
import com.finderlo.weixzz.R;
import com.finderlo.weixzz.ui.Mainview.MainViewFragment;
import com.finderlo.weixzz.ui.StatusDetail.StatusDetailActivity;
import com.finderlo.weixzz.Utility.Util;
import com.finderlo.weixzz.base.BaseFragment;
import com.finderlo.weixzz.model.bean.Status;

import java.util.ArrayList;

/**
 * Created by Finderlo on 2016/8/19.
 * 这是一个抽象类，这个fragment用于展示一个recyclerView，item是一条完整的微博信息
 */

public abstract class TimelineFragment extends BaseFragment {



    ArrayList<Status> mDataList;

    RecyclerView mRecyclerView;
    StatusViewRecyclerAdapter mAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.abs_timeline_status_recycler, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.listView_Statuses);

        mRecyclerView.setAdapter(mAdapter);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        return view;
    }


    public void setDataList(ArrayList dataList) {
        mDataList = dataList;
    }
}
