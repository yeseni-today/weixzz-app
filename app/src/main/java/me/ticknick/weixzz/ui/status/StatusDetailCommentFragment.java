package me.ticknick.weixzz.ui.status;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import me.ticknick.weixzz.adapter.status.CommentSdAdapter;
import me.ticknick.weixzz.dao.statusdetail.BaseStatusDetailDao;
import me.ticknick.weixzz.dao.statusdetail.CommentSdDao;
import me.ticknick.weixzz.model.CommentListModel;

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
