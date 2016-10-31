package com.finderlo.weixzz.ui.statusDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.finderlo.weixzz.adapter.homeTimeline.HomeStatusAdapter;
import com.finderlo.weixzz.R;
import com.finderlo.weixzz.base.BaseFragment;
import com.finderlo.weixzz.model.model.StatusModel;

/**
 * Created by Finderlo on 2016/8/4.
 */
public class StatusDetailFragment extends BaseFragment {


    private static final String ARG_STATUS_MODEL = "arg_status_model";

    private StatusModel mStatusModel;

    View view;

    public StatusDetailFragment() {
        super();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            mStatusModel = getArguments().getParcelable(ARG_STATUS_MODEL);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.statusdetail_fragment2,container,false);


        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.statusdetail_statuscoent_framlayout);

        HomeStatusAdapter adapter = new HomeStatusAdapter(getActivity(),null);
        frameLayout.addView(adapter.getView4Status(getActivity(), mStatusModel));

        return view;
    }





    public static StatusDetailFragment newInstance(StatusModel statusModel){
        StatusDetailFragment statusDetailFragment = new StatusDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_STATUS_MODEL,statusModel);
        statusDetailFragment.setArguments(bundle);
        return statusDetailFragment;
    }

}
