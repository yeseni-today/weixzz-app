package com.finderlo.weixzz.ui.Mainview;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.base.BaseActivity;
import com.finderlo.weixzz.dao.HttpClientUtils;
import com.finderlo.weixzz.model.AccessTokenManger;
import com.finderlo.weixzz.ui.timeline.CommentTimelineFragment;
import com.finderlo.weixzz.ui.timeline.HomeTimelineFragment;
import com.finderlo.weixzz.ui.timeline.MentionsTimelineFragment;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;

public class MainViewActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Fragment mContainerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainview_drawerlayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("WeiXzz");
        setSupportActionBar(toolbar);
        /**这是主layout的drawer*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /**这是侧边栏的naviView*/
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        ViewPager viewPager = (ViewPager) findViewById(R.id.Container_ViewPager);
        viewPager.setAdapter(new ViewAdapter(getFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentPosition = 0;

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        if (currentPosition == 1) {
                            hideAnimator(itemMentionMe);
                        }
                        showAnimator(itemHome);
                        break;
                    case 1:
                        if (currentPosition == 0) {
                            hideAnimator(itemHome);
                        }else if (currentPosition == 2){
                            hideAnimator(itemComment);
                        }
                        showAnimator(itemMentionMe);
                        break;
                    case 2:
                        if (currentPosition == 1){
                            hideAnimator(itemComment);
                        }
                        showAnimator(itemMentionMe);
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


        itemHome = (ImageView) findViewById(R.id.action_home);
        itemMentionMe = (ImageView) findViewById(R.id.action_mention_me);
        itemComment = (ImageView) findViewById(R.id.action_message);
        itemHome.setAlpha(0.3f);
        itemMentionMe.setAlpha(0.3f);

        Oauth2AccessToken token = AccessTokenManger.readAccessToken(this);
        HttpClientUtils.setAccessToken(token.getToken());


    }

    ImageView itemHome;
    ImageView itemMentionMe;
    ImageView itemComment;

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
//            showAnimator(itemHome);
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

        return true;
    }


}

