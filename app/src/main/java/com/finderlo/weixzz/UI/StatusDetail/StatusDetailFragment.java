package com.finderlo.weixzz.UI.StatusDetail;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.Database.StatusDatabaseTool;
import com.finderlo.weixzz.SinaAPI.openapi.models.Status;
import com.finderlo.weixzz.Util.CallbackListener.HttpLoadPicCallbackListener;
import com.finderlo.weixzz.Util.HttpUtil;
import com.finderlo.weixzz.Util.Util;
import com.finderlo.weixzz.View.AutoLinkTextView;
import com.finderlo.weixzz.XzzConstants;

/**
 * Created by Finderlo on 2016/8/4.
 */
public class StatusDetailFragment extends Fragment {

    private static final String TAG = "StatusDetailFragment";
    private Status mStatus;
    public StatusDetailFragment() {
        super();
    }
    View view;
    int[] imageView_grid  = new int[]{
    R.id.statusdetail_pic_count_1_gridview_imageview, R.id.statusdetail_pic_count_2_gridview_imageview,
    R.id.statusdetail_pic_count_3_gridview_imageview, R.id.statusdetail_pic_count_4_gridview_imageview,
    R.id.statusdetail_pic_count_5_gridview_imageview, R.id.statusdetail_pic_count_6_gridview_imageview,
    R.id.statusdetail_pic_count_7_gridview_imageview, R.id.statusdetail_pic_count_8_gridview_imageview,
    R.id.statusdetail_pic_count_9_gridview_imageview
    };
    ImageView[] imageViews_count_2to9;
    ImageView mImageView_count_1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String status_idstr = getArguments().getString(XzzConstants.STATUS_IDSTR);
        mStatus = StatusDatabaseTool.getInstance(
                getActivity()).queryStatus(StatusDatabaseTool.TYPE_IDSTR,status_idstr);
        Log.d(TAG, "onCreate: status is null ?"+ Util.booleanToString(mStatus==null));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.statusdetail_fragment,container,false);
        imageViews_count_2to9 = new ImageView[9];
        for (int i = 0; i < 9; i++) {
            imageViews_count_2to9[i] = (ImageView) view.findViewById(imageView_grid[i]);
        }
        mImageView_count_1 = (ImageView) view.findViewById(R.id.statusdetail_pic_count_one_imageview);

        initView(view);
        return view;
    }

    private void initView(final View view) {
        AutoLinkTextView status_text = (AutoLinkTextView) view.findViewById(R.id.textview_statusdetail_content);
        status_text.setText(mStatus.text);

        if (mStatus.retweeted_status!=null){
            AutoLinkTextView retweeted_text = (AutoLinkTextView) view.findViewById(R.id.textview_statusdetail_retweeted_status_content);
            retweeted_text.setText(mStatus.retweeted_status.text);
            retweeted_text.setVisibility(View.VISIBLE);
        }

        if (mStatus.pic_urls!=null&&mStatus.pic_urls.size()>0){
            if (mStatus.pic_urls.size()==1){
                loadPicFromUrl(mImageView_count_1,mStatus.pic_urls.get(0));
            }else {
                int count = mStatus.pic_urls.size();
                for (int i = 0; i < count; i++) {
                    loadPicFromUrl(imageViews_count_2to9[i],mStatus.pic_urls.get(i));
                }
            }
        }

    }

    private void loadPicFromUrl(final ImageView imageView, String url) {
        HttpUtil.loadPicFromUrl(url, new HttpLoadPicCallbackListener() {
            @Override
            public void onComplete(final Bitmap bitmap) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }

            @Override
            public void onError(Exception exception) {

            }
        });
    }





    public static StatusDetailFragment newInstance(String status_idstr){
        StatusDetailFragment statusDetailFragment = new StatusDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(XzzConstants.STATUS_IDSTR,status_idstr);
        statusDetailFragment.setArguments(bundle);
        return statusDetailFragment;
    }
}
