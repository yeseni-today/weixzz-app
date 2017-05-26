package me.ticknick.weixzz.ui.mainview;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;
import java.util.ArrayList;

import me.ticknick.weixzz.R;
import me.ticknick.weixzz.base.BaseActivity;
import me.ticknick.weixzz.dao.UserCenterDao;
import me.ticknick.weixzz.model.UserModel;
import me.ticknick.weixzz.ui.NewStatusActivity;
import me.ticknick.weixzz.ui.setting.AccountManageActivity;
import me.ticknick.weixzz.ui.timeline.CommentTimelineFragment;
import me.ticknick.weixzz.ui.timeline.HomeTimelineFragment;
import me.ticknick.weixzz.ui.timeline.MentionsTimelineFragment;
import me.ticknick.weixzz.ui.usercenter.UserCenterActivity;
import me.ticknick.weixzz.util.ImageLoader;
import me.ticknick.weixzz.widgt.RoundImageView;

/**
 * 进入主页显示的activity，包含了最近微博、@我的微博、转发微博和侧边工具栏
 */
public class MainViewActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //内容fragment
    Fragment mContainerFragment;

    ViewPager mViewPager;
    /**
     * 这是主layout的drawer
     */
    DrawerLayout mDrawerLayout;
    /**
     * 这是侧边栏的naviView
     */
    NavigationView mNavigationView;

    TextView user_name;
    RoundImageView user_pic;

    private ImageView itemHomeIcon;
    private ImageView itemMentionMeIcon;
    private ImageView itemCommentIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainview_drawerlayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("WeiXzz");
        setSupportActionBar(toolbar);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        user_name = (TextView) mNavigationView.getHeaderView(R.id.mainview_user_name_textview);
        user_pic = (RoundImageView) mNavigationView.getHeaderView(R.id.mainview_user_pic_RoundImageView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        //viewpager用来三张页面的切换。即显示承载通过viewpager
        mViewPager = (ViewPager) findViewById(R.id.Container_ViewPager);
        mViewPager.setAdapter(new ViewAdapter(getFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentPosition = 0;

            //viewpager的切换对应toolbar上引导图标的显示方式
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        if (currentPosition == 1) {
                            hideAnimator(itemMentionMeIcon);
                        } else if (currentPosition == 2) {
                            hideAnimator(itemCommentIcon);
                        }
                        showAnimator(itemHomeIcon);
                        break;
                    case 1:
                        if (currentPosition == 0) {
                            hideAnimator(itemHomeIcon);
                        } else if (currentPosition == 2) {
                            hideAnimator(itemCommentIcon);
                        }
                        showAnimator(itemMentionMeIcon);
                        break;
                    case 2:
                        if (currentPosition == 1) {
                            hideAnimator(itemMentionMeIcon);
                        } else if (currentPosition == 0) {
                            hideAnimator(itemHomeIcon);
                        }
                        showAnimator(itemCommentIcon);
                }
                currentPosition = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //toolbar上引导显示当前页面的图标
        itemHomeIcon = (ImageView) findViewById(R.id.action_home);
        itemMentionMeIcon = (ImageView) findViewById(R.id.action_mention_me);
        itemCommentIcon = (ImageView) findViewById(R.id.action_message);

        itemHomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0, true);
            }
        });
        itemMentionMeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1, true);
            }
        });
        itemCommentIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2, true);
            }
        });

        itemMentionMeIcon.setAlpha(0.3f);
        itemCommentIcon.setAlpha(0.3f);

        //屏幕右下角的发送微博按钮
        FloatingActionButton addNewStatusFab = (FloatingActionButton) findViewById(R.id.fab_add_new);
        addNewStatusFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewStatusActivity.start(MainViewActivity.this);
            }
        });

        new GetUserInfo(mDrawerLayout).execute(this);


    }


    private void showAnimator(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0.3f, 1f);
        animator.setDuration(1000);
        animator.start();
    }

    private void hideAnimator(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.3f);
        animator.setDuration(1000);
        animator.start();
    }

    class ViewAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> mFragmentArrayList = new ArrayList<Fragment>();

        ViewAdapter(FragmentManager fm) {
            super(fm);
            mFragmentArrayList.add(HomeTimelineFragment.newInstance(""));
            mFragmentArrayList.add(MentionsTimelineFragment.newInstance());
            mFragmentArrayList.add(CommentTimelineFragment.newInstance(""));
        }


        @Override
        public Fragment getItem(int position) {
            return mFragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentArrayList.size();
        }
    }


    /**
     * 用户按下返回键的逻辑
     **/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
//            showProgressDialog("正在加载用户信息");
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
            UserModel userModel = result;
            if (userModel == null || user_name == null || user_pic == null) {
                return;
            }
            Log.d(TAG, "onPostExecute: usermodel:" + userModel);
            user_name.setText(userModel.screen_name);
            ImageLoader.load(MainViewActivity.this, userModel.profile_image_url, user_pic);
        }
    }

//    /**
//     *这是actionbar上的设置界面
//     **/
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_view_acivity, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        if (id == R.id.action_refresh){
//            showAnimator(itemHomeIcon);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * 侧边栏上的按钮被按下
     **/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user || id == R.id.mainview_user_name_textview || id == R.id.mainview_user_pic_RoundImageView) {
            UserCenterActivity.start(this);
        }
        if (id == R.id.nav_account_manage) {
            AccountManageActivity.start(this);
        }
        if (id == R.id.nav_menu_mention_me) {
            mViewPager.setCurrentItem(1, true);
        }
//        mDrawerLayout.closeDrawer(Gravity.LEFT, true);
//        mDrawerLayout.closeDrawer(mNavigationView,true);
        Log.e(TAG, "onNavigationItemSelected: " + id);

        return true;
    }


}

