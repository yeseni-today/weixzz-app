package com.finderlo.weixzz.adapter.timeline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.utility.ImageLoader;
import com.finderlo.weixzz.utility.TimeLineUtil;
import com.finderlo.weixzz.widgt.RoundImageView;
import com.finderlo.weixzz.model.model.CommentListModel;
import com.finderlo.weixzz.model.model.CommentModel;

/**
 * Created by Finderlo on 2016/8/19.
 */

public class HomeCommentAdapter extends BaseHomeAdapter<CommentListModel> {


    public HomeCommentAdapter(Context context, CommentListModel comments) {
        super(context,comments);
        mListModel = comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mLayoutInflater = LayoutInflater.from(parent.getContext());
        View view = mLayoutInflater.inflate(R.layout.comment_recycler_item,null);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;

        CommentModel commentModel = mListModel.get(position);

        holder.mUsername.setText(commentModel.user.screen_name);
        holder.mLeftText.setText(TimeLineUtil.getInstance().parse4Timeline(commentModel.created_at));
        holder.mRightText.setText(commentModel.source);
        ImageLoader.load(mContext, commentModel.user.profile_image_url,holder.mUserPic);

        holder.mCommentContent.setText(commentModel.text);

        if (commentModel.reply_comment !=null){
            ImageLoader.load(mContext, commentModel.reply_comment.user.profile_image_url,holder.mImageView);
            holder.mStatusUserName.setText("回复 "+ commentModel.reply_comment.user.screen_name);
            holder.mStatusContent.setText(commentModel.reply_comment.text);
        }else {

            if ("".equals(commentModel.status.original_pic)){
                ImageLoader.load(mContext, commentModel.status.user.profile_image_url,holder.mImageView);
            }else {
                ImageLoader.load(mContext, commentModel.status.original_pic,holder.mImageView);
            }

            holder.mStatusUserName.setText("评论 "+ commentModel.status.user.screen_name);
            holder.mStatusContent.setText(commentModel.status.text);

        }

    }

    @Override
    public int getItemCount() {
        return mListModel.getSize();
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
