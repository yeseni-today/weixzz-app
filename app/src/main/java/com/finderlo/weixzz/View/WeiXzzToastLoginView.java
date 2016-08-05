package com.finderlo.weixzz.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.finderlo.weixzz.R;

/**
 * Created by Finderlo on 2016/8/3.
 */
public class WeiXzzToastLoginView extends LinearLayout {
    public WeiXzzToastLoginView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.loginview_aty,this);
    }

    public WeiXzzToastLoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.loginview_aty,this);
    }
}
