package com.finderlo.weixzz.Widgt;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Finderlo on 2016/8/10.
 */
public class BaseImageView extends ImageView {
    private String mPic_Url;

    public BaseImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public BaseImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseImageView(Context context) {
        super(context);

    }

    public BaseImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPic_Url(String url){
        mPic_Url = url;
    }

    public String getPic_Url() {
        return mPic_Url;
    }
}
