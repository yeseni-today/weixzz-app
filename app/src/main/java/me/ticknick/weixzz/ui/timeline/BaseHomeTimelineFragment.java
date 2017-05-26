package me.ticknick.weixzz.ui.timeline;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

import me.ticknick.weixzz.R;
import me.ticknick.weixzz.adapter.timeline.BaseHomeAdapter;
import me.ticknick.weixzz.base.BaseFragment;
import me.ticknick.weixzz.dao.timeline.BaseTimelineDao;
import me.ticknick.weixzz.widgt.RecyclerViewDivider;

/**
 * Created by Finderlo on 2016/8/19.
 * 这是一个抽象类，这个fragment用于展示一个recyclerView
 * 支持上拉刷新和下拉加载更多
 */

public abstract class BaseHomeTimelineFragment extends BaseFragment {


    protected BaseHomeAdapter mAdapter;
    protected RecyclerAdapterWithHF mAdapterWithHf;
    protected BaseTimelineDao mDao;


    protected RecyclerView mRecyclerView;
    //这是支持上拉刷新和下拉加载更多的库，中对应的frame
    protected PtrClassicFrameLayout mPtrClassicFrameLayout;

    private boolean mRefreshing = false;
    Handler handler = new Handler();

    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.abs_timeline_status_recycler, container, false);
        mView = view;
        //获取recyclerView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.abs_timeline_fragment_content_recyclerView);
        //
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.abs_timeline_fragment_content_ptrFrame);
        mPtrClassicFrameLayout.setClickable(true);
        mDao = bindDao();
        //cache读取数据
        mDao.loadFromCache();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));

        mAdapter = bindAdapter();
        mAdapterWithHf = new RecyclerAdapterWithHF(mAdapter);
        mRecyclerView.setAdapter(mAdapterWithHf);
        setListener();
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
                        new Refresher(mView).execute(true);
                        mPtrClassicFrameLayout.setLoadMoreEnable(true);
                        mAdapterWithHf.notifyDataSetChanged();
                        mPtrClassicFrameLayout.refreshComplete();
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
                        new Refresher(mView).execute(false);
                        mPtrClassicFrameLayout.loadMoreComplete(true);
                        mAdapterWithHf.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });

        return view;
    }

    protected void setListener() {

    }


    protected abstract BaseHomeAdapter bindAdapter();

    protected abstract BaseTimelineDao bindDao();




    //执行刷新 异步线程
    //new Refresher().execute(true);
    //true为刷新，false为加载更多，通过dao层来实现加载更多
    private class Refresher extends AsyncTask<Boolean, Void, Boolean> {

        View view;
        private Refresher(View view){
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRefreshing = true;
            getBaseActivity().showSnackbar(view,"正在刷新");
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
            getBaseActivity().getSnackbar(view).setText("刷新完成");
            getBaseActivity().dismissSnackbar();
//            Snackbar.make(view,"刷新完成",Snackbar.LENGTH_SHORT).show();
        }
    }

    private void load(Boolean param) {
        Log.d(TAG, "load: ");
        mDao.load(param);
        mDao.cache();
    }

}
