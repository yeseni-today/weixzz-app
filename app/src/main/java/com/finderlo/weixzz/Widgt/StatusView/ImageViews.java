package com.finderlo.weixzz.Widgt.StatusView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.Util.CallbackListener.HttpLoadPicCallbackListener;
import com.finderlo.weixzz.Util.HttpUtil;
import com.finderlo.weixzz.Util.Util;
import com.finderlo.weixzz.Util.WeiXzzApplication;

import java.util.ArrayList;

/**
 * Created by Finderlo on 2016/8/8.
 */
public class ImageViews extends GridLayout {
    private int mImageCount;
    Context mContext;

    public ImageViews(Context context) {
        super(context);
        mContext = context;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        setColumnCount(3);


    }


    public void setImageSrc(ArrayList<String> list) {
        if (null == list || list.size() == 0) return;

        mImageCount = list.size();
        CardImage[] images = new CardImage[mImageCount];
        for (int i = 0; i < mImageCount; i++) {
            images[i] = new CardImage(mContext);
            images[i].setBitmapUrl(mContext, list.get(i));
            addView(images[i]);

        }
        invalidate();
        setVisibility(VISIBLE);
    }

    public class CardImage extends FrameLayout {

        private static final String TAG = "CardImage";
        ImageView mImageView;
        Context mContext;

        public CardImage(Context context) {
            super(context);
            mContext = context;
            LayoutParams layoutParams = new LayoutParams(Util.dip2px(mContext, 100),
                    Util.dip2px(mContext, 100));
            layoutParams.setMargins(Util.dip2px(mContext, 10), Util.dip2px(mContext, 10), 0, 0);
            setLayoutParams(layoutParams);

            mImageView = new ImageView(context);
            mImageView.setLayoutParams(layoutParams);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageView.setImageResource(R.drawable.user_default_pic);
            addView(mImageView);
        }

        private void setBitmap(Bitmap bitmap) {
            mImageView.setImageBitmap(bitmap);
            invalidate();
        }

        public void setBitmapUrl(Context context, String bitmapUrl) {

            HttpUtil.loadPicFromUrl(bitmapUrl, new HttpLoadPicCallbackListener() {
                @Override
                public void onComplete(Bitmap bitmap) {
                    Message message = Message.obtain();
                    message.obj = bitmap;
                    message.what = 1;
                    mHandler.sendMessage(message);
                }

                @Override
                public void onError(Exception exception) {
                    Log.d(TAG, "onError: 图片加载错误");
                }
            });

        }

        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        Bitmap bitmap = (Bitmap) msg.obj;
                        setBitmap(bitmap);
                }
            }
        };
    }
}
