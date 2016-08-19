package com.finderlo.weixzz.adapter.comment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.Utility.ImageLoader;
import com.finderlo.weixzz.Utility.TimeLineUtil;
import com.finderlo.weixzz.Widgt.AutoLinkTextView;
import com.finderlo.weixzz.Widgt.RoundImageView;
import com.finderlo.weixzz.Widgt.StatusView.ImageViews;
import com.finderlo.weixzz.model.bean.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finderlo on 2016/8/19.
 */

public class CommentViewAdapter extends RecyclerView.Adapter<CommentViewAdapter.ViewHolder> {

    private ArrayList<Comment> mComments ;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public CommentViewAdapter(Context context,ArrayList<Comment> comments) {
        mContext = context;
        mComments = comments;
    }

    @Override
    public CommentViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mLayoutInflater = LayoutInflater.from(parent.getContext());
        View view = mLayoutInflater.inflate(R.layout.comment_recycler_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewAdapter.ViewHolder holder, int position) {

        Comment comment = mComments.get(position);

        holder.mUsername.setText(comment.user.screen_name);
        holder.mLeftText.setText(TimeLineUtil.getInstance().parse4Timeline(comment.created_at));
        holder.mRightText.setText(comment.source);
        ImageLoader.load(mContext,comment.user.profile_image_url,holder.mUserPic);

        holder.mCommentContent.setText(comment.text);

        if (comment.reply_comment!=null){
            ImageLoader.load(mContext,comment.reply_comment.user.profile_image_url,holder.mImageView);
            holder.mStatusUserName.setText("回复 "+comment.reply_comment.user.screen_name);
            holder.mStatusContent.setText(comment.reply_comment.text);
        }else {

            if ("".equals(comment.status.original_pic)){
                ImageLoader.load(mContext,comment.status.user.profile_image_url,holder.mImageView);
            }else {
                ImageLoader.load(mContext,comment.status.original_pic,holder.mImageView);
            }

            holder.mStatusUserName.setText("评论 "+comment.status.user.screen_name);
            holder.mStatusContent.setText(comment.status.text);

        }

    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mUsername;
        public TextView mLeftText;
        public TextView mRightText;
        public RoundImageView mUserPic;

        public TextView mCommentContent;
        public ImageView mImageView;

        public TextView mStatusContent;
        public TextView mStatusUserName;

        public ViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {

            mUsername = (TextView) view.findViewById(R.id.statusview_header_username);
            mUserPic = (RoundImageView) view.findViewById(R.id.statusview_header_image_user_pic);
            mLeftText = (TextView) view.findViewById(R.id.statusview_header_blow_left_textview);
            mRightText = (TextView) view.findViewById(R.id.statusview_header_blow_right_textview);

            mImageView = (ImageView) view.findViewById(R.id.comment_item_image);

            mCommentContent = (TextView) view.findViewById(R.id.comment_item_comment_content);

            mStatusContent = (TextView) view.findViewById(R.id.comment_item_comment_status_content);
            mStatusUserName = (TextView) view.findViewById(R.id.comment_item_comment_status_username);

        }

    }
}
