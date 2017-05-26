package me.ticknick.weixzz.adapter.status;

import android.content.Context;

import me.ticknick.weixzz.model.RepostListModel;
import me.ticknick.weixzz.model.StatusModel;
import me.ticknick.weixzz.util.ImageLoader;

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
