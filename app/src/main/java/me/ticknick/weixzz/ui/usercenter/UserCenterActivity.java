package me.ticknick.weixzz.ui.usercenter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import me.ticknick.weixzz.R;

import java.io.IOException;

import me.ticknick.weixzz.base.BaseActivity;
import me.ticknick.weixzz.dao.UserCenterDao;
import me.ticknick.weixzz.model.UserModel;

public class UserCenterActivity extends BaseActivity {

    Toolbar toolbar;
    TabLayout mTabLayout;
    ViewPager mViewPager;

    UserModel mUserModel;

    View rootview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usercenter_activity);
        rootview = findViewById(R.id.content);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        new GetUserInfo(rootview).execute(this);

//        if (fragment == null) {
//            fragment = StatusDetailFragment.newInstance();
//            manager.beginTransaction().add(R.id.id_ContentFragment, fragment).commit();
//        }
//
//        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbarLayout.setExpandedTitleColor(Color.argb(00,49,37,37));
//        collapsingToolbarLayout.setTitle("微博详情");


//        mTabLayout.addTab(mTabLayout.newTab().setText("评论"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("转发"));


        mTabLayout.setupWithViewPager(mViewPager);
    }

    class GetUserInfo extends AsyncTask<Context, Void, UserModel> {

        private View view;

        public GetUserInfo(View view) {
            this.view = view;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showSnackbar(view,"正在加载用户信息");
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
            dismissSnackbar();
            mUserModel = result;
            if (mUserModel == null) {
                Toast.makeText(UserCenterActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                UserCenterActivity.this.finish();
            }

            FragmentManager manager = getFragmentManager();
            Fragment fragment = manager.findFragmentById(R.id.id_ContentFragment);


            if (fragment == null) {
                fragment = UserCenterFragment.newInstance(mUserModel);
                manager.beginTransaction().add(R.id.id_ContentFragment, fragment).commit();
            }


            CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            collapsingToolbarLayout.setExpandedTitleColor(Color.argb(00, 49, 37, 37));
            if (!(mUserModel == null)) {
                collapsingToolbarLayout.setTitle(mUserModel.name);
            }
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, UserCenterActivity.class);
        context.startActivity(intent);
    }
}
