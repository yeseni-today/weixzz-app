package com.finderlo.weixzz.widgt.StatusView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.model.model.StatusModel;
import com.finderlo.weixzz.util.ImageLoader;
import com.finderlo.weixzz.widgt.RoundImageView;

/**
 * Created by Finderlo on 2016/8/8.
 */
public class StatusHeadView extends LinearLayout {

    private StatusModel mStatusModel;
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

    public void setStatusModel(StatusModel statusModel) {
        mStatusModel = statusModel;
        initView();
    }

    private void initView() {
        TextView username = (TextView) findViewById(R.id.statusview_header_username);
        username.setText(mStatusModel.user.name);

        TextView isverifed = (TextView) findViewById(R.id.statusview_header_blow_left_textview);
        isverifed.setText(mStatusModel.user.verified ? "已认证" : "未认证");

        TextView verfied_reason = (TextView) findViewById(R.id.statusview_header_blow_right_textview);
        verfied_reason.setText(mStatusModel.user.verified_reason);

        RoundImageView user_pic = (RoundImageView) findViewById(R.id.statusview_header_image_user_pic);
        ImageLoader.load(mContext, mStatusModel.user.profile_image_url,user_pic);

    }




}
