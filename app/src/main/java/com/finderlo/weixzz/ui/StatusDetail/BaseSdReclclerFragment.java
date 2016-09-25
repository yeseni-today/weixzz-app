package com.finderlo.weixzz.ui.statusDetail;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.base.BaseFragment;
import com.finderlo.weixzz.dao.statusdetail.BaseStatusDetailDao;

/**
 * Created by Finderlo on 2016/8/24.
 */

public abstract class BaseSdReclclerFragment extends BaseFragment{

    protected static final String ARG_STATUS_ID = "arg_status_id";
    long mStatusId;

    RecyclerView.Adapter mAdapter;

    BaseStatusDetailDao mDao;
    private boolean mRefreshing = false;


    public BaseSdReclclerFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            mStatusId = getArguments().getLong(ARG_STATUS_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.testlayout,container,false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDao = bindDAO();
        mDao.loadFromCache();
        mAdapter = bindAdapter();

        recyclerView.setAdapter(mAdapter);

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

    protected abstract RecyclerView.Adapter bindAdapter() ;

    protected abstract BaseStatusDetailDao bindDAO();


    public  void onRefresh(){
        if (!mRefreshing){
//            if (mSwipeRefresh!=null){
//                mSwipeRefresh.setRefreshing(true);
//            }
            new Refresher().execute(true);
        }
    }

    private class Refresher extends AsyncTask<Boolean,Void ,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRefreshing = true;
        }

        @Override
        protected Boolean doInBackground(Boolean... booleen) {
            Log.d(TAG, "doInBackground: 正在刷新");
            load(booleen[0]);
            return booleen[0];
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Log.d(TAG, "onPostExecute: 刷新成功");
            super.onPostExecute(aBoolean);
            Log.d(TAG, "onPostExecute: "+mDao.getList().getSize());
            mAdapter.notifyDataSetChanged();
            mRefreshing = false;
//            if (mSwipeRefresh!=null){
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
