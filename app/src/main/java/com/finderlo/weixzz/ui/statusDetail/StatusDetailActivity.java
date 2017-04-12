package com.finderlo.weixzz.ui.statusDetail;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.adapter.status.StadetailViewPagerAdapter;
import com.finderlo.weixzz.base.BaseActivity;
import com.finderlo.weixzz.model.model.StatusModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个activity是用来承载微博的详细信息
 * */
public class StatusDetailActivity extends BaseActivity {


    private static final String ARG_STATUS_MODEL = "arg_status_model";
    private static final String ARG_BUNDLE = "arg_bundle";

    Toolbar toolbar;
    StatusModel mStatusModel;
    //评论或者转发的tablelayout和viewpager
    TabLayout mTabLayout;
    ViewPager mViewPager;

    private List<String> list_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statusdetail_activity2);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        if (getIntent()!=null){
            mStatusModel = getIntent().getBundleExtra(ARG_BUNDLE).getParcelable(ARG_STATUS_MODEL);
        }

//        Log.d(TAG, "The status detail: "+new Gson().toJson(mStatusModel));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.id_ContentFragment);

        if (fragment == null) {
            fragment = StatusDetailFragment.newInstance(mStatusModel);
            manager.beginTransaction().add(R.id.id_ContentFragment, fragment).commit();
        }


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.argb(00,49,37,37));
        collapsingToolbarLayout.setTitle("微博详情");

        list_title = new ArrayList<>();
        list_title.add("评论");
        list_title.add("转发");

        mTabLayout.addTab(mTabLayout.newTab().setText("评论"));
        mTabLayout.addTab(mTabLayout.newTab().setText("转发"));

        mViewPager.setAdapter(new StadetailViewPagerAdapter(getFragmentManager(),mStatusModel.id,list_title));

        mTabLayout.setupWithViewPager(mViewPager);
    }



    public static void start(Context context,StatusModel status){
        Intent intent = new Intent(context,StatusDetailActivity.class);
        Log.d("StatusDetailActivity", "start: "+new Gson().toJson(status));
        Bundle arg = new Bundle();
        arg.putParcelable(ARG_STATUS_MODEL,status);
        intent.putExtra(ARG_BUNDLE,arg);
        context.startActivity(intent);
    }
}
