package com.finderlo.weixzz.ui.usercenter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.adapter.statusDetail.StadetailViewPagerAdapter;
import com.finderlo.weixzz.base.BaseActivity;
import com.finderlo.weixzz.dao.UserCenterDao;
import com.finderlo.weixzz.model.model.StatusModel;
import com.finderlo.weixzz.model.model.UserModel;
import com.finderlo.weixzz.ui.AddNewStatus;
import com.finderlo.weixzz.ui.statusDetail.StatusDetailFragment;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

public class UserCenterActivity extends BaseActivity {

    Toolbar toolbar;
    TabLayout mTabLayout;
    ViewPager mViewPager;

    UserModel mUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usercenter_activity);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FragmentManager manager = getFragmentManager();
//        Fragment fragment = manager.findFragmentById(R.id.id_ContentFragment);

        new GetUserInfo().execute(this);

//        if (fragment == null) {
//            fragment = StatusDetailFragment.newInstance();
//            manager.beginTransaction().add(R.id.id_ContentFragment, fragment).commit();
//        }
//
//        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbarLayout.setExpandedTitleColor(Color.argb(00,49,37,37));
//        collapsingToolbarLayout.setTitle("微博详情");


        mTabLayout.addTab(mTabLayout.newTab().setText("评论"));
        mTabLayout.addTab(mTabLayout.newTab().setText("转发"));


        mTabLayout.setupWithViewPager(mViewPager);
    }

    class GetUserInfo extends AsyncTask<Context,Void,UserModel>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("正在加载用户信息");
        }

        @Override
        protected UserModel doInBackground(Context... params) {
            try {
                 return new UserCenterDao().getCurrentUserModel(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(UserModel result) {
            super.onPostExecute(result);
            closeProgressDialog();
            mUserModel = result;
            if (mUserModel==null){
                Toast.makeText(UserCenterActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                UserCenterActivity.this.finish();
            }

            FragmentManager manager = getFragmentManager();
            Fragment fragment = manager.findFragmentById(R.id.id_ContentFragment);


            if (fragment == null) {
                fragment = UserCenterFragment.newInstance(mUserModel);
                manager.beginTransaction().add(R.id.id_ContentFragment, fragment).commit();
            }


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.argb(00,49,37,37));
        collapsingToolbarLayout.setTitle(mUserModel.name);

        }
    }

    public static void start(Context context){
        Intent intent  = new Intent(context,UserCenterActivity.class);
        context.startActivity(intent);
    }
}
