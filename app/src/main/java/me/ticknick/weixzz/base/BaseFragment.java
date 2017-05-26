package me.ticknick.weixzz.base;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Finderlo on 2016/8/19.
 */

public class BaseFragment extends Fragment {

    protected String TAG = getClass().getSimpleName();

    protected boolean isFirstCreate=true;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isFirstCreate=false;
    }

    protected BaseActivity getBaseActivity(){
        BaseActivity baseActivity = null;
        if (getActivity() instanceof BaseActivity){
            baseActivity = (BaseActivity) getActivity();
        }
        return baseActivity;
    }
}
