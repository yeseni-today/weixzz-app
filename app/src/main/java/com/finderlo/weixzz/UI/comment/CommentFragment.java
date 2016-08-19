package com.finderlo.weixzz.ui.comment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.adapter.StatusViewRecyclerAdapter;
import com.finderlo.weixzz.adapter.comment.CommentViewAdapter;
import com.finderlo.weixzz.base.BaseFragment;
import com.finderlo.weixzz.base.WeiException;
import com.finderlo.weixzz.dao.CommentDao;
import com.finderlo.weixzz.model.CommentWrapAPI;
import com.finderlo.weixzz.model.bean.Comment;
import com.finderlo.weixzz.sinaApi.openapi.models.CommentList;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.util.ArrayList;

/**
 * Created by Finderlo on 2016/8/19.
 */

public class CommentFragment extends BaseFragment {

    RecyclerView mRecyclerView;

    CommentViewAdapter mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mComments == null||mComments.size()==0) {
            refreshData();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.comment_mainview_fragment, container, false);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.comment_fragment_content_recyclerView);
        mAdapter = new CommentViewAdapter(getActivity(), mComments);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipeRefreshLayout);

        return mView;
    }

    public static CommentFragment newInstance(ArrayList<Comment> comments) {
        CommentFragment fragment = new CommentFragment();
        fragment.setComments(comments);
        return fragment;
    }

    private ArrayList<Comment> mComments = new ArrayList<Comment>();

    public void setComments(ArrayList<Comment> comments) {
        if (mComments == null) {
            mComments = comments;
        } else {
            mComments.clear();
            for (Comment comment :
                    comments) {
             mComments.add(comment);
            }
        }
        mAdapter.notifyDataSetChanged();

    }

    public static CommentFragment newInstance() {
        CommentFragment fragment = new CommentFragment();
        return fragment;
    }

    private void refreshData() {

        getBaseActivity().showProgressDialog();

        CommentWrapAPI.getInstance().timeline(0, 0, 50, 1, false, new RequestListener() {
            @Override
            public void onComplete(String s) {
                final CommentList commentList = CommentList.parse(s);

                Message message = Message.obtain();
                message.what = 1;
                message.obj = commentList.commentList;
                mHandler.sendMessage(message);
                getBaseActivity().closeProgressDialog();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            CommentDao.getInstance().insert(commentList.commentList);
                        } catch (WeiException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onWeiboException(WeiboException e) {

            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    setComments((ArrayList<Comment>) msg.obj);
                    break;
            }
        }
    };

}
