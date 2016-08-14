package com.finderlo.weixzz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.SinaAPI.openapi.models.Status;
import com.finderlo.weixzz.Util.ImageLoader;
import com.finderlo.weixzz.Widgt.AutoLinkTextView;
import com.finderlo.weixzz.Widgt.RoundImageView;
import com.finderlo.weixzz.Widgt.StatusView.ImageViews;

import java.util.List;

/**
 * Created by Finderlo on 2016/8/14.
 */
public class StatusViewRecyclerAdapter extends RecyclerView.Adapter<StatusViewRecyclerAdapter.ViewHolder> {

    private List<Status> mStatusList;

    private Context mContext;

    public StatusViewRecyclerAdapter(Context context, List<Status> list) {
        mStatusList = list;
        mContext = context;
    }

    public StatusViewRecyclerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public StatusViewRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statusview, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatusViewRecyclerAdapter.ViewHolder holder, int position) {

        holder.initViewHolder();
        Status mStatus = mStatusList.get(position);

        holder.username.setText(mStatus.user.name);
        holder.isverifed.setText(mStatus.user.verified ? "已认证" : "未认证");
        holder.verfied_reason.setText(mStatus.user.verified_reason);

        ImageLoader.load(mContext, mStatus.user.profile_image_url, holder.user_pic);


        holder.statusContent.setText(mStatus.text);
        holder.pics.setImageSrc(mStatus);


        if (mStatus.retweeted_status == null) return;
        holder.retweetedStatusContent.setText(mStatus.retweeted_status.text);
        holder.retweetedStatusContent.setVisibility(View.VISIBLE);

        if (mStatus.retweeted_status.pic_urls == null) return;
        holder.retweetedStatusPics.setImageSrc(mStatus.retweeted_status);
        holder.retweetedStatusPics.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mStatusList.size();
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int positon);
    }

    public View getView4Status(Context context, Status mStatus) {

        View view = LayoutInflater.from(context).inflate(R.layout.statusview, null);

         TextView username;
         TextView isverifed;
         TextView verfied_reason;
         RoundImageView user_pic;

         AutoLinkTextView statusContent;
         ImageViews pics;

         AutoLinkTextView retweetedStatusContent;
         ImageViews retweetedStatusPics;

        username = (TextView) view.findViewById(R.id.statusview_header_username);
        user_pic = (RoundImageView) view.findViewById(R.id.statusview_header_image_user_pic);
        isverifed = (TextView) view.findViewById(R.id.statusview_header_flag_Verified);
        verfied_reason = (TextView) view.findViewById(R.id.statusview_header_Verified_reason);

        statusContent = (AutoLinkTextView) view.findViewById(R.id.statusview_status_content);
        pics = (ImageViews) view.findViewById(R.id.statusview_imageviews_gridlayout);

        retweetedStatusContent = (AutoLinkTextView) view.findViewById(
                R.id.statusview_retweeted_status_content);
        retweetedStatusPics = (ImageViews) view.findViewById(
                R.id.statusview_retweeted_imageviews_gridlayout);

        username.setText(mStatus.user.name);
        isverifed.setText(mStatus.user.verified ? "已认证" : "未认证");
        verfied_reason.setText(mStatus.user.verified_reason);

        ImageLoader.load(mContext, mStatus.user.profile_image_url, user_pic);


        statusContent.setText(mStatus.text);
        pics.setImageSrc(mStatus);


        if (mStatus.retweeted_status != null) {
            retweetedStatusContent.setText(mStatus.retweeted_status.text);
            retweetedStatusContent.setVisibility(View.VISIBLE);
        }

        if (mStatus.retweeted_status.pic_urls != null) {
            retweetedStatusPics.setImageSrc(mStatus.retweeted_status);
            retweetedStatusPics.setVisibility(View.VISIBLE);
        }

        return view;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView isverifed;
        public TextView verfied_reason;
        public RoundImageView user_pic;

        public AutoLinkTextView statusContent;
        public ImageViews pics;

        public AutoLinkTextView retweetedStatusContent;
        public ImageViews retweetedStatusPics;


        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, getPosition());
                    }
                }
            });

            username = (TextView) itemView.findViewById(R.id.statusview_header_username);
            user_pic = (RoundImageView) itemView.findViewById(R.id.statusview_header_image_user_pic);
            isverifed = (TextView) itemView.findViewById(R.id.statusview_header_flag_Verified);
            verfied_reason = (TextView) itemView.findViewById(R.id.statusview_header_Verified_reason);

            statusContent = (AutoLinkTextView) itemView.findViewById(R.id.statusview_status_content);
            pics = (ImageViews) itemView.findViewById(R.id.statusview_imageviews_gridlayout);

            retweetedStatusContent = (AutoLinkTextView) itemView.findViewById(
                    R.id.statusview_retweeted_status_content);
            retweetedStatusPics = (ImageViews) itemView.findViewById(
                    R.id.statusview_retweeted_imageviews_gridlayout);
        }

        public void initViewHolder() {
            pics.init();
            retweetedStatusPics.init();
            pics.setVisibility(View.GONE);
            retweetedStatusContent.setVisibility(View.GONE);
            retweetedStatusPics.setVisibility(View.GONE);
        }
    }
}
