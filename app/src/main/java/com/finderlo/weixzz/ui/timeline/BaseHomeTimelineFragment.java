package com.finderlo.weixzz.ui.timeline;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;

import com.finderlo.weixzz.dao.timeline.BaseTimelineDao;
import com.finderlo.weixzz.widgt.RecyclerViewDivider;
import com.finderlo.weixzz.adapter.timeline.BaseHomeAdapter;
import com.finderlo.weixzz.R;
import com.finderlo.weixzz.base.BaseFragment;

/**
 * Created by Finderlo on 2016/8/19.
 * 这是一个抽象类，这个fragment用于展示一个recyclerView
 * 支持上拉刷新和下拉加载更多
 */

public abstract class BaseHomeTimelineFragment extends BaseFragment {


    protected BaseHomeAdapter mAdapter;
    protected RecyclerAdapterWithHF mAdapterWithHf;
    protected com.finderlo.weixzz.dao.timeline.BaseTimelineDao mDao;


    protected RecyclerView mRecyclerView;
    //这是支持上拉刷新和下拉加载更多的库，中对应的frame
    protected PtrClassicFrameLayout mPtrClassicFrameLayout;

    private boolean mRefreshing = false;
    Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.abs_timeline_status_recycler, container, false);
        //获取recyclerView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.abs_timeline_fragment_content_recyclerView);
        //
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.abs_timeline_fragment_content_ptrFrame);

        mDao = bindDao();
        //cache读取数据
        mDao.loadFromCache();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));

        mAdapter= bindAdapter();
        mAdapterWithHf = new RecyclerAdapterWithHF(mAdapter);
        mRecyclerView.setAdapter(mAdapterWithHf);
        //第一次执行刷新
        mPtrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrClassicFrameLayout.autoRefresh(true);
            }
        }, 150);

        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new Refresher().execute(true);
                        mAdapterWithHf.notifyDataSetChanged();
                        mPtrClassicFrameLayout.refreshComplete();
                        mPtrClassicFrameLayout.setLoadMoreEnable(true);
                    }
                }, 1500);
            }
        });

        mPtrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new Refresher().execute(false);
                        mAdapterWithHf.notifyDataSetChanged();
                        mPtrClassicFrameLayout.loadMoreComplete(true);
//                        Toast.makeText(RecyclerViewActivity.this, "load more complete", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });

        return view;
    }



//    protected void onLoadMore(int currentPage) {
//            new Refresher().execute(false);
//        }
//    }

    protected abstract BaseHomeAdapter bindAdapter();

    protected abstract BaseTimelineDao bindDao();


    public void onRefresh() {
        if (!mRefreshing) {
//            if (mSwipeRefresh != null) {
//                mSwipeRefresh.setRefreshing(true);
//            }
            new Refresher().execute(true);
        }
    }


    //执行刷新 异步线程
    //new Refresher().execute(true);
    //true为刷新，false为加载更多，通过dao层来实现加载更多
    private class Refresher extends AsyncTask<Boolean, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRefreshing = true;
        }

        @Override
        protected Boolean doInBackground(Boolean... booleen) {
            if (booleen[0]) {
                load(booleen[0]);
            } else {
                mDao.loadMore();
            }
            return booleen[0];
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mAdapter.notifyDataSetChanged();
            mRefreshing = false;
//            if (mSwipeRefresh != null) {
//                mSwipeRefresh.setRefreshing(false);
//            }
        }
    }

    private void load(Boolean param) {
        Log.d(TAG, "load: ");
        mDao.load(param);
        mDao.cache();
    }

}
