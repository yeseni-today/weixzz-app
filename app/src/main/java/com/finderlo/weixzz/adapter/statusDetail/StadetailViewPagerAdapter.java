package com.finderlo.weixzz.adapter.statusDetail;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.finderlo.weixzz.model.model.StatusModel;
import com.finderlo.weixzz.ui.statusDetail.StatusDetailCommentFragment;
import com.finderlo.weixzz.ui.statusDetail.StatusDetailRepostFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finderlo on 2016/8/24.
 */

public class StadetailViewPagerAdapter extends FragmentPagerAdapter {

    public long mStatusId;

    ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    private List<String> list_Title;

    public StadetailViewPagerAdapter(FragmentManager fm, long statusId,List<String> list_Title) {
        super(fm);
        mStatusId = statusId;
        this.list_Title = list_Title;
        mFragments.add(StatusDetailCommentFragment.newInstance(statusId));
        mFragments.add(StatusDetailRepostFragment.newInstance(statusId));
    }

    public StadetailViewPagerAdapter(FragmentManager fm, StatusModel statusModel) {
        super(fm);
        mStatusId = statusModel.id;
        mFragments.add(StatusDetailCommentFragment.newInstance(statusModel.id));
        if (statusModel.retweeted_status != null) {
            mFragments.add(StatusDetailRepostFragment.newInstance(statusModel.retweeted_status.id));
        } else mFragments.add(StatusDetailRepostFragment.newInstance(statusModel.id));

    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return list_Title.get(position % list_Title.size());
    }
}
