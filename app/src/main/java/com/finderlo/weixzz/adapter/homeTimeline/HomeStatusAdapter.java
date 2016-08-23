package com.finderlo.weixzz.adapter.homeTimeline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.model.model.StatusListModel;
import com.finderlo.weixzz.model.model.StatusModel;
import com.finderlo.weixzz.Utility.ImageLoader;
import com.finderlo.weixzz.Utility.TimeLineUtil;
import com.finderlo.weixzz.Widgt.AutoLinkTextView;
import com.finderlo.weixzz.Widgt.RoundImageView;
import com.finderlo.weixzz.Widgt.StatusView.ImageViews;

import java.util.List;

/**
 * Created by Finderlo on 2016/8/14.
 */
public class HomeStatusAdapter extends BaseHomeAdapter<StatusListModel> {

    private List<StatusModel> mStatusModelList;

    private StatusListModel mListModel;

    private String TAG = HomeStatusAdapter.class.getSimpleName();

    public HomeStatusAdapter(Context context, StatusListModel list) {
        super(context,list);
        mListModel = list;
        if (list!=null){
            mStatusModelList =  list.getList();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statusview, null);
        return new StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StatusViewHolder viewHolder = (StatusViewHolder)holder;

        viewHolder.initViewHolder();
        StatusModel mStatusModel = mStatusModelList.get(position);

        viewHolder.username.setText(mStatusModel.user.name);
        viewHolder.isverifed.setText(mStatusModel.user.verified ? "已认证" : "未认证");
//        viewHolder.verfied_reason.setText(mStatusModel.mUserModel.verified_reason);
        viewHolder.verfied_reason.setText(TimeLineUtil.getInstance().parse4Timeline(mStatusModel.created_at));

        ImageLoader.load(mContext, mStatusModel.user.profile_image_url, viewHolder.user_pic);


        viewHolder.statusContent.setText(mStatusModel.text);
        viewHolder.pics.setImageSrc(mStatusModel);

        if (mStatusModel.retweeted_status != null) {
            viewHolder.retweetedStatusContent.setText(mStatusModel.retweeted_status.text);
            viewHolder.retweetedStatusContent.setVisibility(View.VISIBLE);

            if (mStatusModel.retweeted_status.pic_urls != null) {
                viewHolder.retweetedStatusPics.setImageSrc(mStatusModel.retweeted_status);
                viewHolder.retweetedStatusPics.setVisibility(View.VISIBLE);
            }
        }


    }

    @Override
    public int getItemCount() {
        return mStatusModelList.size();
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int positon);
    }

    public View getView4Status(Context context, StatusModel mStatusModel) {

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
        isverifed = (TextView) view.findViewById(R.id.statusview_header_blow_left_textview);
        verfied_reason = (TextView) view.findViewById(R.id.statusview_header_blow_right_textview);

        statusContent = (AutoLinkTextView) view.findViewById(R.id.statusview_status_content);
        pics = (ImageViews) view.findViewById(R.id.statusview_imageviews_gridlayout);

        retweetedStatusContent = (AutoLinkTextView) view.findViewById(
                R.id.statusview_retweeted_status_content);
        retweetedStatusPics = (ImageViews) view.findViewById(
                R.id.statusview_retweeted_imageviews_gridlayout);

        username.setText(mStatusModel.user.name);
        isverifed.setText(mStatusModel.user.verified ? "已认证" : "未认证");
        verfied_reason.setText(mStatusModel.user.verified_reason);

        ImageLoader.load(mContext, mStatusModel.user.profile_image_url, user_pic);


        statusContent.setText(mStatusModel.text);
        pics.setImageSrc(mStatusModel);


        if (mStatusModel.retweeted_status != null) {
            retweetedStatusContent.setText(mStatusModel.retweeted_status.text);
            retweetedStatusContent.setVisibility(View.VISIBLE);

            if (mStatusModel.retweeted_status.pic_urls != null) {
                retweetedStatusPics.setImageSrc(mStatusModel.retweeted_status);
                retweetedStatusPics.setVisibility(View.VISIBLE);
            }

        }

        return view;

    }


    public class StatusViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView isverifed;
        public TextView verfied_reason;
        public RoundImageView user_pic;

        public AutoLinkTextView statusContent;
        public ImageViews pics;

        public AutoLinkTextView retweetedStatusContent;
        public ImageViews retweetedStatusPics;


        public StatusViewHolder(View itemView) {
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
            isverifed = (TextView) itemView.findViewById(R.id.statusview_header_blow_left_textview);
            verfied_reason = (TextView) itemView.findViewById(R.id.statusview_header_blow_right_textview);

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
