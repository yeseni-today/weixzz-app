package com.finderlo.weixzz.base;

import android.app.Fragment;

/**
 * Created by Finderlo on 2016/8/19.
 */

public class BaseFragment extends Fragment {


    protected BaseActivity getBaseActivity(){
        BaseActivity baseActivity = null;
        if (getActivity() instanceof BaseActivity){
            baseActivity = (BaseActivity) getActivity();
        }
        return baseActivity;
    }
}
