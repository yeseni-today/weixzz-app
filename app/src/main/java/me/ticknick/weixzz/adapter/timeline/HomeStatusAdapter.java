package me.ticknick.weixzz.adapter.timeline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.ticknick.weixzz.R;
import java.util.List;

import me.ticknick.weixzz.adapter.timeline.BaseHomeAdapter;
import me.ticknick.weixzz.model.StatusListModel;
import me.ticknick.weixzz.model.StatusModel;
import me.ticknick.weixzz.util.ImageLoader;
import me.ticknick.weixzz.util.TimeLineUtil;
import me.ticknick.weixzz.widgt.AutoLinkTextView;
import me.ticknick.weixzz.widgt.RoundImageView;
import me.ticknick.weixzz.widgt.status.ImageViews;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statusview,null,true);
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
        viewHolder.pics.setImageSrcAndShow(mStatusModel);

        if (mStatusModel.retweeted_status != null) {
            viewHolder.retweetedStatusContent.setText(mStatusModel.retweeted_status.text);
            viewHolder.retweetedStatusContent.setVisibility(View.VISIBLE);

            if (mStatusModel.retweeted_status.pic_urls != null) {
                viewHolder.retweetedStatusPics.setImageSrcAndShow(mStatusModel.retweeted_status);
                viewHolder.retweetedStatusPics.setVisibility(View.VISIBLE);
            }
        }


    }

    @Override
    public int getItemCount() {
        return mStatusModelList.size();
    }

    private OnItemClickListener mOnItemClickListener;

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
        pics.setImageSrcAndShow(mStatusModel);


        if (mStatusModel.retweeted_status != null) {
            retweetedStatusContent.setText(mStatusModel.retweeted_status.text);
            retweetedStatusContent.setVisibility(View.VISIBLE);

            if (mStatusModel.retweeted_status.pic_urls != null) {
                retweetedStatusPics.setImageSrcAndShow(mStatusModel.retweeted_status);
                retweetedStatusPics.setVisibility(View.VISIBLE);
            }

        }

        return view;

    }


    public class StatusViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        private TextView isverifed;
        private TextView verfied_reason;
        private RoundImageView user_pic;

        private AutoLinkTextView statusContent;
        private ImageViews pics;

        private AutoLinkTextView retweetedStatusContent;
        private ImageViews retweetedStatusPics;


        StatusViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: ONE");
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });

            username = (TextView) itemView.findViewById(R.id.statusview_header_username);
            user_pic = (RoundImageView) itemView.findViewById(R.id.statusview_header_image_user_pic);
            isverifed = (TextView) itemView.findViewById(R.id.statusview_header_blow_left_textview);
            verfied_reason = (TextView) itemView.findViewById(R.id.statusview_header_blow_right_textview);

            statusContent = (AutoLinkTextView) itemView.findViewById(R.id.statusview_status_content);

            statusContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: TWO");
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });

            pics = (ImageViews) itemView.findViewById(R.id.statusview_imageviews_gridlayout);

            retweetedStatusContent = (AutoLinkTextView) itemView.findViewById(
                    R.id.statusview_retweeted_status_content);

            retweetedStatusContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: THREE");
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });
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
