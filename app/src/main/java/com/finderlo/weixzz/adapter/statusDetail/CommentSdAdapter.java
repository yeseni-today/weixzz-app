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
import com.finderlo.weixzz.model.model.CommentListModel;
import com.finderlo.weixzz.model.model.CommentModel;
import com.finderlo.weixzz.model.model.UserModel;

/**
 * Created by Finderlo on 2016/8/24.
 */

public class CommentSdAdapter extends BaseSdRecyclerAdapter<CommentListModel> {



    public CommentSdAdapter(Context context,CommentListModel listModel){
        super(context,listModel);

    }


    @Override
    protected void onBindContentViewHolder(BaseSdRecyclerViewHolder holder, int position) {
        CommentModel comment = mListModel.get(position);
        holder.username .setText(comment.user.screen_name);
        holder.left_textview.setText(comment.created_at);
        ImageLoader.load(mContext,comment.user.profile_image_url,holder.user_pic);

        holder.content.setText(comment.text);
    }
}
