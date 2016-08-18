package com.finderlo.weixzz.Widgt.StatusView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.model.bean.Status;
import com.finderlo.weixzz.Utility.ImageLoader;
import com.finderlo.weixzz.Widgt.RoundImageView;

/**
 * Created by Finderlo on 2016/8/8.
 */
public class StatusHeadView extends LinearLayout {

    private Status mStatus;
    private Context mContext;

    public StatusHeadView(Context context) {
        super(context);
        mContext = context;
//        LayoutInflater.from(context).inflate(R.layout.statusview_head, this);
        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }

    public StatusHeadView(Context context, AttributeSet attrs ) {
        super(context, attrs);
        mContext = context;
        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }

    public void setStatus(Status status) {
        mStatus = status;
        initView();
    }

    private void initView() {
        TextView username = (TextView) findViewById(R.id.statusview_header_username);
        username.setText(mStatus.user.name);

        TextView isverifed = (TextView) findViewById(R.id.statusview_header_flag_Verified);
        isverifed.setText(mStatus.user.verified ? "已认证" : "未认证");

        TextView verfied_reason = (TextView) findViewById(R.id.statusview_header_Verified_reason);
        verfied_reason.setText(mStatus.user.verified_reason);

        RoundImageView user_pic = (RoundImageView) findViewById(R.id.statusview_header_image_user_pic);
        ImageLoader.load(mContext,mStatus.user.profile_image_url,user_pic);

    }




}
