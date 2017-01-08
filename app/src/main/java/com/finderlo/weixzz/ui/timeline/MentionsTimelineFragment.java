package com.finderlo.weixzz.ui.timeline;

import com.finderlo.weixzz.adapter.timeline.BaseHomeAdapter;
import com.finderlo.weixzz.adapter.timeline.HomeStatusAdapter;
import com.finderlo.weixzz.dao.timeline.BaseTimelineDao;
import com.finderlo.weixzz.dao.timeline.MentionsDao;
import com.finderlo.weixzz.model.model.StatusListModel;

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
    protected BaseHomeAdapter bindAdapter() {
        return new HomeStatusAdapter(getActivity(), (StatusListModel) mDao.getList());
    }

    @Override
    protected BaseTimelineDao bindDao() {
        return new MentionsDao(getActivity());
    }
}
