package com.finderlo.weixzz.UI.StatusDetail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.Database.StatusDatabaseTool;
import com.finderlo.weixzz.SinaAPI.openapi.models.Status;
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
        View view = inflater.inflate(R.layout.statusdetail_fragment,container,false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        AutoLinkTextView status_text = (AutoLinkTextView) view.findViewById(R.id.textview_statusdetail_content);
        status_text.setText(mStatus.text);
        if (mStatus.retweeted_status!=null){
            AutoLinkTextView retweeted_text = (AutoLinkTextView) view.findViewById(R.id.textview_statusdetail_retweeted_status_content);
            retweeted_text.setText(mStatus.retweeted_status.text);
            retweeted_text.setVisibility(View.VISIBLE);
        }

    }

    public static StatusDetailFragment newInstance(String status_idstr){
        StatusDetailFragment statusDetailFragment = new StatusDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(XzzConstants.STATUS_IDSTR,status_idstr);
        statusDetailFragment.setArguments(bundle);
        return statusDetailFragment;
    }
}
