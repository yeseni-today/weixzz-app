package com.finderlo.weixzz.Widgt.StatusView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.SinaAPI.openapi.models.Status;
import com.finderlo.weixzz.UI.Login.LoadDataActivity;
import com.finderlo.weixzz.Util.CallbackListener.HttpLoadPicCallbackListener;
import com.finderlo.weixzz.Util.HttpUtil;
import com.finderlo.weixzz.Util.ImageLoader;
import com.finderlo.weixzz.Util.Util;
import com.finderlo.weixzz.Util.WeiXzzApplication;
import com.finderlo.weixzz.Widgt.BaseImageView;

import java.util.ArrayList;

/**
 * Created by Finderlo on 2016/8/8.
 */
public class ImageViews extends GridLayout {


    private int mImageCount = 0;
    Context mContext;
    private Status mStatus;

    public ImageViews(Context context) {
        super(context);
        mContext = context;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        setColumnCount(3);
        setPadding(Util.dip2px(context, 10), Util.dip2px(context, 10), Util.dip2px(context, 10), Util.dip2px(context, 10));
    }

    public ImageViews(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setColumnCount(3);
        setPadding(Util.dip2px(context, 10), Util.dip2px(context, 10), Util.dip2px(context, 10), Util.dip2px(context, 10));
    }


    CardImage[] images;

    public void setImageSrc(Status status) {
        ArrayList<String> list = status.pic_urls;
        if (null == list || list.size() == 0) return;

        mImageCount = list.size();
        images = new CardImage[mImageCount];
        for (int i = 0; i < mImageCount; i++) {
            images[i] = new CardImage(mContext);
            images[i].setBitmapUrl(mContext, list.get(i));
            addView(images[i]);
        }
        invalidate();
        setVisibility(VISIBLE);
    }

    public void init() {
        if (images == null) return;
        if (mImageCount == 0) return;
        removeAllViews();
    }

    public class CardImage extends FrameLayout {

        private static final String TAG = "CardImage";

        BaseImageView mImageView;
        Context mContext;

        public CardImage(Context context) {
            super(context);
            mContext = context;

//            LayoutParams layoutParams = new LayoutParams(
//                    Util.dip2px(mContext, LoadDataActivity.PIC_WIDTH+10),
//                    Util.dip2px(mContext,  LoadDataActivity.PIC_WIDTH+10));
//            setLayoutParams(layoutParams);

            LayoutParams imagelayoutParams = new LayoutParams(
                    Util.dip2px(mContext, LoadDataActivity.PIC_WIDTH),
                    Util.dip2px(mContext, LoadDataActivity.PIC_WIDTH));

            imagelayoutParams.setMargins(Util.dip2px(mContext, 5), Util.dip2px(mContext, 5),
                    Util.dip2px(mContext, 5), Util.dip2px(mContext, 5));
            mImageView = new BaseImageView(context);
            mImageView.setLayoutParams(imagelayoutParams);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageView.setImageResource(R.drawable.user_default_pic);
            mImageView.setOnClickListener(mOnClickListener);
            addView(mImageView);
        }


        public void setBitmapUrl(Context context, String bitmapUrl) {
            ImageLoader.load(context, bitmapUrl, mImageView);
            invalidate();
        }

        OnClickListener mOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("image_detail");
                intent.putExtra("image_url", mImageView.getPic_Url());
                mContext.startActivity(intent);
            }
        };

        @Override
        public void setOnClickListener(OnClickListener l) {
            super.setOnClickListener(l);
        }
    }
}
