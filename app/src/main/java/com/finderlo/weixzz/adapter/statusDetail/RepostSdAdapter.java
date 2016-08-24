package com.finderlo.weixzz.adapter.statusDetail;

import android.content.Context;

import com.finderlo.weixzz.Utility.ImageLoader;
import com.finderlo.weixzz.model.model.CommentModel;
import com.finderlo.weixzz.model.model.RepostListModel;
import com.finderlo.weixzz.model.model.StatusModel;

/**
 * Created by Finderlo on 2016/8/24.
 */

public class RepostSdAdapter extends BaseSdRecyclerAdapter<RepostListModel> {
    public RepostSdAdapter(Context context, RepostListModel listModel) {
        super(context, listModel);
    }

    @Override
    protected void onBindContentViewHolder(BaseSdRecyclerViewHolder holder, int position) {
        StatusModel statusModel = mListModel.get(position);
        holder.username .setText(statusModel.user.screen_name);
        holder.left_textview.setText(statusModel.created_at);
        ImageLoader.load(mContext,statusModel.user.profile_image_url,holder.user_pic);

        holder.content.setText(statusModel.text);
    }
}
