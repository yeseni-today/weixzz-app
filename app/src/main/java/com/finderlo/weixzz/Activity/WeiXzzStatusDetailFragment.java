package com.finderlo.weixzz.Activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.Util.StatusDatabaseTool;
import com.finderlo.weixzz.Util.Util;
import com.finderlo.weixzz.XzzConstants;
import com.sina.weibo.sdk.openapi.models.Status;

/**
 * Created by Finderlo on 2016/8/4.
 */
public class WeiXzzStatusDetailFragment extends Fragment {

    private static final String TAG = "StatusDetailFragment";
    private Status mStatus;
    public WeiXzzStatusDetailFragment() {
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
        View view = inflater.inflate(R.layout.fragment_statusdetail,container,false);
        TextView textView = (TextView) view.findViewById(R.id.textview_statusdetail_content);
        textView.setText(mStatus.text);

        return view;
    }

    public static WeiXzzStatusDetailFragment newInstance(String status_idstr){
        WeiXzzStatusDetailFragment weiXzzStatusDetailFragment = new WeiXzzStatusDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(XzzConstants.STATUS_IDSTR,status_idstr);
        weiXzzStatusDetailFragment.setArguments(bundle);
        return weiXzzStatusDetailFragment;
    }
}
