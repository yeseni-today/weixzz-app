package com.finderlo.weixzz.ui.StatusDetail;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.finderlo.weixzz.adapter.statusDetail.RepostSdAdapter;
import com.finderlo.weixzz.dao.statusdetail.BaseStatusDetailDao;
import com.finderlo.weixzz.dao.statusdetail.RepostStatusSdDAO;
import com.finderlo.weixzz.model.model.RepostListModel;

/**
 * Created by Finderlo on 2016/8/24.
 */

public class StatusDetailRepostFragment extends BaseSdReclclerFragment {
    @Override
    protected RecyclerView.Adapter bindAdapter() {
        RepostSdAdapter adapter = new RepostSdAdapter(getActivity(), (RepostListModel) mDao.getList());
        return adapter;
    }

    @Override
    protected BaseStatusDetailDao bindDAO() {
        return new RepostStatusSdDAO(getActivity(),mStatusId);
    }


    public static StatusDetailRepostFragment newInstance(long statusId) {
        StatusDetailRepostFragment fragment = new StatusDetailRepostFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_STATUS_ID, statusId);
        fragment.setArguments(args);
        return fragment;
    }

}
