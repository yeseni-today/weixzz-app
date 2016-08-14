//package com.finderlo.weixzz.Adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.finderlo.weixzz.R;
//import com.finderlo.weixzz.SinaAPI.openapi.models.Status;
//import com.finderlo.weixzz.Util.ImageLoader;
//import com.finderlo.weixzz.Widgt.AutoLinkTextView;
//import com.finderlo.weixzz.Widgt.RoundImageView;
//import com.finderlo.weixzz.Widgt.StatusView.ImageViews;
//import com.finderlo.weixzz.Widgt.StatusView.StatusViewitem;
//
//import java.util.ArrayList;
//
///**
// * Created by Finderlo on 2016/8/8.
// */
//public class StatusViewAdapter extends BaseAdapter {
//    private ArrayList<Status> mDataList;
//    private Context mContext;
//
//    public StatusViewAdapter(Context context, ArrayList<Status> list) {
//        mContext = context;
//        mDataList = list;
//    }
//
//    @Override
//    public int getCount() {
//        return mDataList.size();
//    }
//
//    @Override
//    public Status getItem(int i) {
//        return mDataList.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int i, View convertView, ViewGroup viewGroup) {
//        Status status = getItem(i);
//        ViewHolder holder = null;
//        if (convertView == null) {
//            holder = new ViewHolder();
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.statusview, null);
//
//            holder.username = (TextView) convertView.findViewById(R.id.statusview_header_username);
//            holder.user_pic = (RoundImageView) convertView.findViewById(R.id.statusview_header_image_user_pic);
//            holder.isverifed = (TextView) convertView.findViewById(R.id.statusview_header_flag_Verified);
//            holder.verfied_reason = (TextView) convertView.findViewById(R.id.statusview_header_Verified_reason);
//
//            holder.statusContent = (AutoLinkTextView) convertView.findViewById(R.id.statusview_status_content);
//            holder.pics = (ImageViews) convertView.findViewById(R.id.statusview_imageviews_gridlayout);
//
//            holder.retweetedStatusContent = (AutoLinkTextView) convertView.findViewById(
//                    R.id.statusview_retweeted_status_content);
//            holder.retweetedStatusPics = (ImageViews) convertView.findViewById(
//                    R.id.statusview_retweeted_imageviews_gridlayout);
//
//            convertView.setTag(holder);
//        }else {
//            holder = (ViewHolder) convertView.getTag();
//            holder.initViewHolder();
//        }
//
//
//        initStatusHead(holder, status);
//        initStatus(holder, status);
//        initRetweetedStaus(holder, status);
//
//
//        return convertView;
//    }
//
//    private void initRetweetedStaus(ViewHolder holder, Status status) {
//        if (status.retweeted_status == null) return;
//        holder.retweetedStatusContent.setText(status.retweeted_status.text);
//        holder.retweetedStatusContent.setVisibility(View.VISIBLE);
//
//        if (status.retweeted_status.pic_urls == null) return;
//        holder.retweetedStatusPics.setImageSrc(status.retweeted_status);
//        holder.retweetedStatusPics.setVisibility(View.VISIBLE);
//    }
//
//    private void initStatus(ViewHolder holder, Status mStatus) {
//        holder.statusContent.setText(mStatus.text);
//        holder.pics.setImageSrc(mStatus);
//    }
//
//    private void initStatusHead(ViewHolder holder, Status mStatus) {
//        holder.username.setText(mStatus.user.name);
//        holder.isverifed.setText(mStatus.user.verified ? "已认证" : "未认证");
//        holder.verfied_reason.setText(mStatus.user.verified_reason);
//
//        ImageLoader.load(mContext, mStatus.user.profile_image_url, holder.user_pic);
//    }
//
////    class ViewHolder extends RecyclerView.ViewHolder {
////        public TextView username;
////        public TextView isverifed;
////        public TextView verfied_reason;
////        public RoundImageView user_pic;
////
////        public AutoLinkTextView statusContent;
////        public ImageViews pics;
////
////        public AutoLinkTextView retweetedStatusContent;
////        public ImageViews retweetedStatusPics;
////
////
////
////        public void initViewHolder() {
////            pics.setVisibility(View.GONE);
////            retweetedStatusContent.setVisibility(View.GONE);
////            retweetedStatusPics.setVisibility(View.GONE);
////        }
////    }
//}
