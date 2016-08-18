package com.finderlo.weixzz.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Finderlo on 2016/8/10.
 */
public class BaseTextView extends TextView {
    public BaseTextView(Context context) {
        super(context);
    }

    public BaseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
