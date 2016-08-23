package com.finderlo.weixzz.ui.timeline;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finderlo.weixzz.adapter.homeTimeline.BaseHomeAdapter;
import com.finderlo.weixzz.R;
import com.finderlo.weixzz.base.BaseFragment;
import com.finderlo.weixzz.dao.timeline.BaseTimelineDao;

/**
 * Created by Finderlo on 2016/8/19.
 * 这是一个抽象类，这个fragment用于展示一个recyclerView
 */

public abstract class BaseHomeTimelineFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener {


    protected BaseHomeAdapter mAdapter;
    protected BaseTimelineDao mDao;


    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mSwipeRefresh;

    private boolean mRefreshing = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.abs_timeline_status_recycler, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.abs_timeline_fragment_content_recyclerView);

        mDao = bindDao();
        mDao.loadFromCache();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = bindAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefresh.setOnRefreshListener(this);

        if (isFirstCreate){
//            onRefresh();
            new Handler().postAtTime(new Runnable() {
                @Override
                public void run() {
                    onRefresh();
                }
            },500);
        }

        return view;
    }

    protected abstract BaseHomeAdapter bindAdapter();

    protected abstract BaseTimelineDao bindDao();


    public  void onRefresh(){
        if (!mRefreshing){
            if (mSwipeRefresh!=null){
                mSwipeRefresh.setRefreshing(true);
            }
            new Refresher().execute(true);
        }
    }


    private class Refresher extends AsyncTask<Boolean,Void ,Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRefreshing = true;
        }

        @Override
        protected Boolean doInBackground(Boolean... booleen) {
            load(booleen[0]);
            return booleen[0];
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mAdapter.notifyDataSetChanged();
            mRefreshing = false;
            if (mSwipeRefresh!=null){
                mSwipeRefresh.setRefreshing(false);
            }
        }
    }

    private void load(Boolean param) {
        Log.d(TAG, "load: ");
        mDao.load(param);
        mDao.cache();
    }

}
