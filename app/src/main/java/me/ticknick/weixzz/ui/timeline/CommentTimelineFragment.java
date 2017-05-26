package me.ticknick.weixzz.ui.timeline;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;

import me.ticknick.weixzz.adapter.timeline.BaseHomeAdapter;
import me.ticknick.weixzz.adapter.timeline.HomeCommentAdapter;
import me.ticknick.weixzz.dao.timeline.BaseTimelineDao;
import me.ticknick.weixzz.dao.timeline.CommentTlDao;
import me.ticknick.weixzz.model.CommentListModel;
import me.ticknick.weixzz.model.CommentModel;
import me.ticknick.weixzz.ui.status.StatusDetailActivity;


public class CommentTimelineFragment extends BaseHomeTimelineFragment {

    //initialization parameter
    private static final String ARG_COMMENT_TYPE = " arg_comment_type";

    String mType = "";

    public CommentTimelineFragment() {

    }

    public static CommentTimelineFragment newInstance(String commentType) {
        CommentTimelineFragment commentTimelineFragment = new CommentTimelineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COMMENT_TYPE, commentType);
        commentTimelineFragment.setArguments(args);
        return commentTimelineFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
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
        return new CommentTlDao(getActivity(), mType);
    }

    @Override
    protected void setListener() {
        mAdapterWithHf.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                StatusDetailActivity.start(getActivity(), ((CommentModel) mDao.getList().get(position)).status);
            }
        });
    }

}

