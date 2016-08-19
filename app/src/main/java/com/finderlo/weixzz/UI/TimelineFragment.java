package com.finderlo.weixzz.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.finderlo.weixzz.Adapter.StatusViewRecyclerAdapter;
import com.finderlo.weixzz.Constants;
import com.finderlo.weixzz.R;
import com.finderlo.weixzz.UI.Mainview.MainViewFragment;
import com.finderlo.weixzz.UI.StatusDetail.StatusDetailActivity;
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.abs_timeline_status_recycler, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.listView_Statuses);
        Util.sort(mDataList);

        mAdapter = new StatusViewRecyclerAdapter(getActivity(),mDataList);
        mAdapter.setOnItemClickListener(new StatusViewRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int positon) {
                Status status = mDataList.get(positon);
                Intent intent = new Intent(getActivity(), StatusDetailActivity.class);
                intent.putExtra(Constants.STATUS_IDSTR, status.idstr);
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(),"Refreshing...",Toast.LENGTH_SHORT).show();
                if ((Math.random())>0.5){
                    Toast.makeText(getActivity(),"success",Toast.LENGTH_SHORT).show();
                }else Toast.makeText(getActivity(),"fail",Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }

        });


        return view;
    }
    public static MainViewFragment newInstance(ArrayList<Status> list) {
        MainViewFragment mainViewFragment = new MainViewFragment();
        mainViewFragment.setDataList(list);
        return mainViewFragment;
    }




    public void setDataList(ArrayList dataList) {
        mDataList = dataList;
    }
}
