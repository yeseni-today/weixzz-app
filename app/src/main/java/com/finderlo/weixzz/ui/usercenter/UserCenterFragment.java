package com.finderlo.weixzz.ui.usercenter;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.base.BaseFragment;
import com.finderlo.weixzz.model.model.UserModel;
import com.finderlo.weixzz.ui.statusDetail.StatusDetailFragment;
import com.finderlo.weixzz.utility.ImageLoader;
import com.finderlo.weixzz.widgt.RoundImageView;

/**
 * Created by Finderlo on 2016/10/30.
 */

public class UserCenterFragment extends BaseFragment {

    private static final String  ARG_User_Model = "arg_user_model";

    UserModel mUserModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            mUserModel = getArguments().getParcelable(ARG_User_Model);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.usercenter_fragment,container,false);

        RoundImageView userPic = (RoundImageView) view.findViewById(R.id.usercenter_user_pic);
        ImageLoader.load(getActivity(),mUserModel.profile_image_url,userPic);

        TextView username = (TextView) view.findViewById(R.id.usercenter_name);
        username.setText(mUserModel.name);

        TextView userGeo = (TextView) view.findViewById(R.id.usercenter_geo_descript);
        userGeo.setText(mUserModel.location);

        TextView statusCount = (TextView) view.findViewById(R.id.usercenter_statuscount);
        TextView favCount = (TextView) view.findViewById(R.id.usercenter_facoritecount);
        TextView followCount = (TextView) view.findViewById(R.id.usercenter_followscount);
        statusCount.setText(mUserModel.statuses_count+"");
        favCount.setText(mUserModel.favourites_count+"");
        followCount.setText(mUserModel.followers_count+"");

        TextView descript = (TextView) view.findViewById(R.id.usercenter_descript);
        descript.setText(mUserModel.description);

        return view;
    }

    public static UserCenterFragment newInstance(UserModel userModel) {
        UserCenterFragment userCenterFragment = new UserCenterFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_User_Model,userModel);
        userCenterFragment.setArguments(bundle);
        return userCenterFragment;
    }
}
