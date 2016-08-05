package com.finderlo.weixzz.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.Util.CallbackListener.HttpLoadPicCallbackListener;
import com.finderlo.weixzz.Util.HttpUtil;
import com.sina.weibo.sdk.openapi.models.Status;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Finderlo on 2016/8/4.
 */
public class StatusAdapter extends ArrayAdapter<Status> {
    private int mResource;

    private Status mStatus;
    private View mView;
    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvIsVerified;
    private TextView mTvVerifiedReason;
    private ImageView mImagePic;

    ViewHolder viewHolder;


    public StatusAdapter(Context context, int resource, List<Status> objects) {
        super(context, resource, objects);
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(mResource, null);
            viewHolder.mTvTitle = (TextView) convertView.findViewById(R.id.list_item_username);
            viewHolder.mTvContent = (TextView) convertView.findViewById(R.id.list_item_statusContent);
            viewHolder.mTvIsVerified = (TextView) convertView.findViewById(R.id.textview_flag_Verified);
            viewHolder.mTvVerifiedReason = (TextView) convertView.findViewById(R.id.list_item_Verified_reason);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        mStatus = getItem(position);
        mImagePic = (ImageView) convertView.findViewById(R.id.image_user_pic);

        HttpUtil.loadPicFromUrl(mStatus.user.profile_image_url, mListener);
        viewHolder.mTvIsVerified.setText(mStatus.user.verified ?
                getContext().getString(R.string.isVerified) : getContext().getString(R.string.isUnVerified));
        viewHolder.mTvVerifiedReason.setText(mStatus.user.verified_reason);
        viewHolder.mTvTitle.setText(mStatus.user.name);
        viewHolder.mTvContent.setText(mStatus.text);
        return convertView;
    }

    HttpLoadPicCallbackListener mListener = new HttpLoadPicCallbackListener() {
        @Override
        public void onComplete(final Bitmap bitmap) {
            if (getContext() instanceof MainViewAcivity) {
                MainViewAcivity mainViewAcivity = (MainViewAcivity) getContext();
                mainViewAcivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImagePic.setImageBitmap(bitmap);
                    }
                });
            }
        }

        @Override
        public void onError(Exception exception) {
            exception.printStackTrace();
        }
    };

    public final class ViewHolder {
        public Status mStatus;
        public View mView;
        public TextView mTvTitle;
        public TextView mTvContent;
        public TextView mTvIsVerified;
        public TextView mTvVerifiedReason;
        public ImageView mImagePic;
    }
}
