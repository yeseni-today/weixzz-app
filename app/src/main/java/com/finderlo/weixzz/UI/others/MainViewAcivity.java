package com.finderlo.weixzz.UI.others;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.finderlo.weixzz.Dao.MentionsDao;
import com.finderlo.weixzz.Dao.StatusDao;
import com.finderlo.weixzz.UI.Login.LoginActivity;
import com.finderlo.weixzz.Utility.Util;
import com.finderlo.weixzz.base.BaseActivity;
import com.finderlo.weixzz.model.APIManger;
import com.finderlo.weixzz.model.StatusesAPI;
import com.finderlo.weixzz.model.bean.Status;
import com.finderlo.weixzz.R;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.util.ArrayList;
public class MainViewAcivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainViewActivity";

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



        mContainerFragment = getFragmentManager().findFragmentById(R.id.Container);

        ArrayList<Status> mDataList = StatusDao.getInstance().queryLastStatuses(25);

        if (mContainerFragment==null){
            mContainerFragment = MainViewFragment.newInstance(mDataList);
            getFragmentManager().beginTransaction().add(R.id.Container,mContainerFragment,null).commit();
        }

    }



    /**
     *用户按下返回键的逻辑
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

    /**
     *这是actionbar上的设置界面
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_view_acivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *侧边栏上的按钮被按下
     **/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_menu_mention_me:
                ArrayList<Status> data = queryLastStatus();
                replaceMainFragment(data);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void replaceMainFragment(ArrayList<Status> data){
        mContainerFragment = MainViewFragment.newInstance(data);
        getFragmentManager().beginTransaction().replace(R.id.Container,mContainerFragment,null).commit();
    }


    /**
     * 从服务器获取最新的50条微博消息 提到我的
     **/
    private ArrayList<Status> queryLastStatus() {
         ArrayList<Status> data = new ArrayList<Status>();
        showProgressDialog();
        StatusesAPI mStatusesAPI = APIManger.getStatusesAPI();
        if (null == mStatusesAPI) {
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
            return data;
        }
        mStatusesAPI.mentions(0, 0, 50, 1, 0, 0,0,false, new RequestListener() {
            @Override
            public void onComplete(String s) {
                Util.handleMentiosJSONStringData(MainViewAcivity.this, s);
                closeProgressDialog();
            }

            @Override
            public void onWeiboException(WeiboException e) {
                e.printStackTrace();
            }
        });

        data = MentionsDao.getInstance().queryLastStatuses(25);
        return data;
    }



}

