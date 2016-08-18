package com.finderlo.weixzz.UI.Mainview;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finderlo.weixzz.Adapter.StatusViewRecyclerAdapter;
import com.finderlo.weixzz.Constants;
import com.finderlo.weixzz.Dao.StatusDao;
import com.finderlo.weixzz.R;
import com.finderlo.weixzz.model.StatusesAPI;
import com.finderlo.weixzz.model.bean.Status;
import com.finderlo.weixzz.UI.Login.LoginActivity;
import com.finderlo.weixzz.UI.StatusDetail.StatusDetailActivity;
import com.finderlo.weixzz.model.APIManger;
import com.finderlo.weixzz.Utility.Util;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.util.ArrayList;

/**
 * Created by Finderlo on 2016/8/7.
 */
public class MainViewFragment extends Fragment {

    private String TAG = "MainViewFragment";

    ArrayList<Status> mDataList;

    RecyclerView mRecyclerView;
    StatusViewRecyclerAdapter mAdapter;

    View.OnClickListener lister_fresh = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            queryLastStatus();
        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mainview_nav_mainview, container, false);

        FloatingActionButton fabButton = (FloatingActionButton) view.findViewById(R.id.fab_refresh);

        fabButton.setOnClickListener(lister_fresh);


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


    /**
     * 从服务器获取最新的50条微博消息
     **/
    private void queryLastStatus() {
        StatusesAPI mStatusesAPI = APIManger.getStatusesAPI();
        showProgressDialog();
        if (null == mStatusesAPI) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
            return;
        }
        mStatusesAPI.friendsTimeline(0, 0, 50, 1, false, 0, false, new RequestListener() {
            @Override
            public void onComplete(String s) {
                Util.handleJSONStringData(getActivity(), s);

                Log.d(TAG, "onComplete: mDataList.size" + mDataList.size());
                refreshDatalist();
                Log.d(TAG, "onComplete: 99999999+9");
                closeProgressDialog();
            }

            @Override
            public void onWeiboException(WeiboException e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * 数据库中微博刷新时,刷新适配器
     **/
    private void refreshDatalist() {
        ArrayList<Status> mStatuses = StatusDao.getInstance().queryStatuses();
        mDataList.clear();
        for (Status s : mStatuses) {
            mDataList.add(s);
        }
        Util.sort(mDataList);
        mAdapter.notifyDataSetChanged();
    }


    ProgressDialog mProgressDialog;

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("正在加载...");
            mProgressDialog.setCanceledOnTouchOutside(false);

        }
        mProgressDialog.show();
    }

    private void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(message);
            mProgressDialog.setCanceledOnTouchOutside(false);

        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    private void closeProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

}
