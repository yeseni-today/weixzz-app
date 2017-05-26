package me.ticknick.weixzz.adapter.status;

import android.content.Context;

import me.ticknick.weixzz.model.CommentListModel;
import me.ticknick.weixzz.model.CommentModel;
import me.ticknick.weixzz.util.ImageLoader;

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
