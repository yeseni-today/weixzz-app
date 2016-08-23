package com.finderlo.weixzz.ui.timeline;

import android.os.Bundle;

import com.finderlo.weixzz.adapter.homeTimeline.BaseHomeAdapter;
import com.finderlo.weixzz.adapter.homeTimeline.HomeCommentAdapter;
import com.finderlo.weixzz.dao.timeline.BaseTimelineDao;
import com.finderlo.weixzz.dao.timeline.CommentDao;
import com.finderlo.weixzz.model.model.CommentListModel;


public class CommentTimelineFragment extends BaseHomeTimelineFragment {

    //initialization parameter
    private static final String ARG_COMMENT_TYPE  = " arg_comment_type";

    String mType = "";

    public CommentTimelineFragment(){

    }

    public static CommentTimelineFragment newInstance(String commentType){
        CommentTimelineFragment commentTimelineFragment = new CommentTimelineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COMMENT_TYPE,commentType);
        commentTimelineFragment.setArguments(args);
        return commentTimelineFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            mType = getArguments().getString(ARG_COMMENT_TYPE);
        }
    }

    @Override
    protected BaseHomeAdapter bindAdapter() {
        HomeCommentAdapter adapter = new HomeCommentAdapter(getActivity(), (CommentListModel) mDao.getList());
        return adapter;
    }

    @Override
    protected BaseTimelineDao bindDao() {
        return new CommentDao(getActivity(),mType);
    }
}

