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

import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.finderlo.weixzz.dao.timeline.BaseTimelineDao;
import com.finderlo.weixzz.widgt.EndlessOnScrollListener;
import com.finderlo.weixzz.widgt.RecyclerViewDivider;
import com.finderlo.weixzz.adapter.homeTimeline.BaseHomeAdapter;
import com.finderlo.weixzz.R;
import com.finderlo.weixzz.base.BaseFragment;

/**
 * Created by Finderlo on 2016/8/19.
 * 这是一个抽象类，这个fragment用于展示一个recyclerView
 */

public abstract class BaseHomeTimelineFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener {


    protected BaseHomeAdapter mAdapter;
    protected com.finderlo.weixzz.dao.timeline.BaseTimelineDao mDao;


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

        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(),LinearLayoutManager.HORIZONTAL));

        mAdapter = bindAdapter();

        HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        headerAndFooterRecyclerViewAdapter.addFooterView(inflater.inflate(R.layout.foot_layout,null));
        mRecyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);

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

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            onLoadMore(0);
        }
    };

    protected  void onLoadMore(int currentPage){
        if (!mRefreshing){
            if (mSwipeRefresh!=null){
                mSwipeRefresh.setRefreshing(true);
            }
            new Refresher().execute(false);
        }
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
            if (booleen[0]){
                load(booleen[0]);
            }else {
                mDao.loadMore();
            }
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
