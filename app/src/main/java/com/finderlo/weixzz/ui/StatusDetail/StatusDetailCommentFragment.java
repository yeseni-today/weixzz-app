package com.finderlo.weixzz.ui.StatusDetail;

import android.app.Fragment;
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
import android.widget.FrameLayout;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.adapter.statusDetail.CommentSdAdapter;
import com.finderlo.weixzz.base.BaseFragment;
import com.finderlo.weixzz.dao.statusdetail.BaseStatusDetailDao;
import com.finderlo.weixzz.dao.statusdetail.CommentSdDao;
import com.finderlo.weixzz.model.model.CommentListModel;
import com.finderlo.weixzz.ui.timeline.BaseHomeTimelineFragment;

/**
 * Created by Finderlo on 2016/8/24.
 */

public class StatusDetailCommentFragment extends BaseSdReclclerFragment {



    public StatusDetailCommentFragment() {
        super();
    }


    @Override
    protected RecyclerView.Adapter bindAdapter() {
        return new CommentSdAdapter(getActivity(), (CommentListModel) mDao.getList());
    }

    @Override
    protected BaseStatusDetailDao bindDAO() {
        return new CommentSdDao(getActivity(), mStatusId);
    }

    public static StatusDetailCommentFragment newInstance(long statusId) {
        StatusDetailCommentFragment fragment = new StatusDetailCommentFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_STATUS_ID, statusId);
        fragment.setArguments(args);
        return fragment;
    }



}
