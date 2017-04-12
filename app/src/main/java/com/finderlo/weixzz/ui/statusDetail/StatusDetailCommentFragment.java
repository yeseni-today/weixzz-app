package com.finderlo.weixzz.ui.statusDetail;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.finderlo.weixzz.adapter.status.CommentSdAdapter;
import com.finderlo.weixzz.dao.statusdetail.BaseStatusDetailDao;
import com.finderlo.weixzz.dao.statusdetail.CommentSdDao;
import com.finderlo.weixzz.model.model.CommentListModel;

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
