package com.finderlo.weixzz.UI.Mainview;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.finderlo.weixzz.Adapter.StatusAdapter;
import com.finderlo.weixzz.Database.DatabaseTool;
import com.finderlo.weixzz.R;
import com.finderlo.weixzz.SinaAPI.openapi.StatusesAPI;
import com.finderlo.weixzz.SinaAPI.openapi.models.Status;
import com.finderlo.weixzz.UI.Login.LoginActivity;
import com.finderlo.weixzz.UI.StatusDetail.StatusDetailActivity;
import com.finderlo.weixzz.Util.ClientApiManger;
import com.finderlo.weixzz.Util.Util;
import com.finderlo.weixzz.View.NestedListView;
import com.finderlo.weixzz.XzzConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Finderlo on 2016/8/7.
 */
public class MainViewFragment extends Fragment {

    Toolbar mToolbar;
    NestedListView mListViewStatuses;

    ArrayList<Status> mDataList;

    View.OnClickListener lister_fresh = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            queryLastStatus();
        }
    };
    private String TAG = "MainviewFragment";

    public void setListViewStatuses(NestedListView listViewStatuses) {
        mListViewStatuses = listViewStatuses;
    }

    public void setToolbar(Toolbar toolbar) {
        mToolbar = toolbar;
    }

    StatusAdapter mAdapter;

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

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar.setTitle("Weixzz");

        mListViewStatuses = (NestedListView) view.findViewById(R.id.listView_Statuses);
        mAdapter = new StatusAdapter(getActivity(),
                R.layout.mainview_listitem,
                mDataList);
        mListViewStatuses.setAdapter(mAdapter);

        mListViewStatuses.setOnItemClickListener(itemListClickListener);


        return view;
    }


    public static MainViewFragment newInstance(ArrayList<Status> list) {
        MainViewFragment mainViewFragment = new MainViewFragment();
        mainViewFragment.setDataList(list);
        return mainViewFragment;
    }

    private AdapterView.OnItemClickListener itemListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Status status = mDataList.get(i);
            Intent intent = new Intent(getActivity(), StatusDetailActivity.class);
            intent.putExtra(XzzConstants.STATUS_IDSTR, status.idstr);
            startActivity(intent);
        }
    };

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public StatusAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(StatusAdapter adapter) {
        mAdapter = adapter;
    }

    public NestedListView getListViewStatuses() {
        return mListViewStatuses;
    }

    public ArrayList getDataList() {
        return mDataList;
    }

    public void setDataList(ArrayList dataList) {
        mDataList = dataList;
    }


    /**
     * 从服务器获取最新的50条微博消息
     **/
    private void queryLastStatus() {
        StatusesAPI mStatusesAPI = ClientApiManger.getClientApiManger(getActivity()).getStatusesAPI();
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
     *数据库中微博刷新时,刷新适配器
     **/
    private void refreshDatalist() {
        ArrayList<Status> mStatuses = DatabaseTool.getInstance(getActivity()).queryStatuses();
        mDataList.clear();
        for (Status s:mStatuses){
            mDataList.add(s);
        }
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
