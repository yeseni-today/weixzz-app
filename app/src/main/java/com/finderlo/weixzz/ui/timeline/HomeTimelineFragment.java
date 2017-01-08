package com.finderlo.weixzz.ui.timeline;

import android.os.Bundle;
import android.view.View;

import com.finderlo.weixzz.adapter.timeline.HomeStatusAdapter;
import com.finderlo.weixzz.dao.timeline.BaseTimelineDao;
import com.finderlo.weixzz.dao.timeline.StatusDao;
import com.finderlo.weixzz.model.model.StatusListModel;
import com.finderlo.weixzz.model.model.StatusModel;
import com.finderlo.weixzz.ui.statusDetail.StatusDetailActivity;

/**
 * Created by Finderlo on 2016/8/7.
 */
public class HomeTimelineFragment extends BaseHomeTimelineFragment {
    //the fragment initialization parameters
    private static final String ARG_GROUP_Id = "arg_group_id";

    String mGroupId = "";

    public HomeTimelineFragment(){
        // Required empty public constructor
    }


    public static HomeTimelineFragment newInstance(String groupId) {
        HomeTimelineFragment fragment = new HomeTimelineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GROUP_Id, groupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGroupId = getArguments().getString(ARG_GROUP_Id);
        }
    }



    @Override
    protected HomeStatusAdapter bindAdapter() {
        HomeStatusAdapter adapter =  new HomeStatusAdapter(getActivity(), (StatusListModel) mDao.getList());
        adapter.setOnItemClickListener(new HomeStatusAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int positon) {
                StatusDetailActivity.start(getActivity(), (StatusModel) mDao.getList().get(positon));
            }
        });
        return adapter;
    }

    @Override
    protected BaseTimelineDao bindDao() {
        return new StatusDao(getActivity(), mGroupId);
    }




}
