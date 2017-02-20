package com.finderlo.weixzz.widgt.StatusView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.model.model.StatusModel;
import com.finderlo.weixzz.ui.ImageDetailActivity;
import com.finderlo.weixzz.ui.login.LoadDataActivity;
import com.finderlo.weixzz.util.ImageLoader;
import com.finderlo.weixzz.util.Util;
import com.finderlo.weixzz.widgt.BaseImageView;

import java.util.ArrayList;

/**
 * Created by Finderlo on 2016/8/8.
 */
public class ImageViews extends GridLayout {


    private int mImageCount = 0;
    Context mContext;
    private StatusModel mStatusModel;

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

    public void setImageSrc(StatusModel statusModel) {
        ArrayList<StatusModel.PictureUrl> list = statusModel.pic_urls;
        mStatusModel = statusModel;
        if (null == list || list.size() == 0) return;

        mImageCount = list.size();
        images = new CardImage[mImageCount];
        for (int i = 0; i < mImageCount; i++) {
            images[i] = new CardImage(mContext,i);
            images[i].setBitmapUrl(mContext, list.get(i).getMedium());
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

        private int mIndex;

        public CardImage(Context context,int index) {
            super(context);
            mContext = context;
            mIndex = index;


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
                ImageDetailActivity.start(mContext,mStatusModel,mIndex);
            }
        };

        @Override
        public void setOnClickListener(OnClickListener l) {
            super.setOnClickListener(l);
        }
    }
}
