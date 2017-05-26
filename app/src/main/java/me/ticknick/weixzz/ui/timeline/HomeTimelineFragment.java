package me.ticknick.weixzz.ui.timeline;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;

import me.ticknick.weixzz.adapter.timeline.HomeStatusAdapter;
import me.ticknick.weixzz.dao.timeline.BaseTimelineDao;
import me.ticknick.weixzz.dao.timeline.StatusDao;
import me.ticknick.weixzz.model.StatusListModel;
import me.ticknick.weixzz.model.StatusModel;
import me.ticknick.weixzz.ui.status.StatusDetailActivity;

/**
 * Created by Finderlo on 2016/8/7.
 */
public class HomeTimelineFragment extends BaseHomeTimelineFragment {
    //the fragment initialization parameters
    private static final String ARG_GROUP_Id = "arg_group_id";

    String mGroupId = "";

    public HomeTimelineFragment() {
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
        HomeStatusAdapter adapter = new HomeStatusAdapter(getActivity(), (StatusListModel) mDao.getList());
        adapter.setOnItemClickListener(new HomeStatusAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int positon) {
                StatusDetailActivity.start(getActivity(), (StatusModel) mDao.getList().get(positon));
            }
        });

        return adapter;
    }

    @Override
    protected void setListener() {
        mAdapterWithHf.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                StatusDetailActivity.start(getActivity(), (StatusModel) mDao.getList().get(position));
            }
        });
    }

    @Override
    protected BaseTimelineDao bindDao() {
        return new StatusDao(getActivity(), mGroupId);
    }


}
