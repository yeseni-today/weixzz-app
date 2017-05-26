package me.ticknick.weixzz.ui.timeline;

import android.support.v7.widget.RecyclerView;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;

import me.ticknick.weixzz.adapter.timeline.BaseHomeAdapter;
import me.ticknick.weixzz.adapter.timeline.HomeStatusAdapter;
import me.ticknick.weixzz.dao.timeline.BaseTimelineDao;
import me.ticknick.weixzz.dao.timeline.MentionsDao;
import me.ticknick.weixzz.model.StatusListModel;
import me.ticknick.weixzz.model.StatusModel;
import me.ticknick.weixzz.ui.status.StatusDetailActivity;

/**
 * Created by Finderlo on 2016/8/23.
 */

public class MentionsTimelineFragment extends BaseHomeTimelineFragment {

    public MentionsTimelineFragment(){
        //required empty construct
    }

    public static  MentionsTimelineFragment newInstance(){
        MentionsTimelineFragment fragment = new MentionsTimelineFragment();
        return fragment;
    }

    @Override
    protected void setListener() {
        mAdapterWithHf.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                StatusDetailActivity.start(getActivity(), ((StatusModel) mDao.getList().get(position)).retweeted_status);
            }
        });
    }


    @Override
    protected BaseHomeAdapter bindAdapter() {
        return new HomeStatusAdapter(getActivity(), (StatusListModel) mDao.getList());
    }

    @Override
    protected BaseTimelineDao bindDao() {
        return new MentionsDao(getActivity());
    }
}
