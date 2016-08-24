package com.finderlo.weixzz.adapter.statusDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.Utility.ImageLoader;
import com.finderlo.weixzz.Widgt.AutoLinkTextView;
import com.finderlo.weixzz.Widgt.RoundImageView;
import com.finderlo.weixzz.model.model.BaseListModel;
import com.finderlo.weixzz.model.model.CommentListModel;
import com.finderlo.weixzz.model.model.CommentModel;

/**
 * Created by Finderlo on 2016/8/24.
 */

public abstract class BaseSdRecyclerAdapter<ListModel extends BaseListModel>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    protected ListModel mListModel;
    protected Context mContext;

    public BaseSdRecyclerAdapter(Context context,ListModel listModel){
        mContext = context;
        mListModel = listModel;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseSdRecyclerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.statusdetail_recycler_item,null));
    }

    @Override
    public  void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        BaseSdRecyclerViewHolder viewHolder = (BaseSdRecyclerViewHolder) holder;
        onBindContentViewHolder(viewHolder,position);
    }

    protected abstract void onBindContentViewHolder(BaseSdRecyclerViewHolder holder, int position);


    @Override
    public int getItemCount() {
        return mListModel.getSize();
    }

    class BaseSdRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView left_textview;
        public TextView right_textview;
        public RoundImageView user_pic;

        public AutoLinkTextView content;

        public BaseSdRecyclerViewHolder(View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.statusview_header_username);
            user_pic = (RoundImageView) itemView.findViewById(R.id.statusview_header_image_user_pic);
            left_textview = (TextView) itemView.findViewById(R.id.statusview_header_blow_left_textview);
            right_textview = (TextView) itemView.findViewById(R.id.statusview_header_blow_right_textview);

            content = (AutoLinkTextView) itemView.findViewById(R.id.content);

        }
    }
}
