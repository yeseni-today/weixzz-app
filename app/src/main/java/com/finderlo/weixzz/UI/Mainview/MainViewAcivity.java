package com.finderlo.weixzz.UI.Mainview;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.finderlo.weixzz.Adapter.StatusAdapter;
import com.finderlo.weixzz.Database.DatabaseTool;
import com.finderlo.weixzz.SinaAPI.openapi.StatusesAPI;
import com.finderlo.weixzz.SinaAPI.openapi.models.Status;
import com.finderlo.weixzz.UI.Login.LoginActivity;
import com.finderlo.weixzz.UI.StatusDetail.StatusDetailActivity;
import com.finderlo.weixzz.R;
import com.finderlo.weixzz.Util.ClientApiManger;
import com.finderlo.weixzz.Util.Util;
import com.finderlo.weixzz.XzzConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.util.ArrayList;

public class MainViewAcivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainViewActivity";

    private ListView mListView;
    private ArrayList<Status> mStatuses;
    private StatusAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private ArrayList<Status> mDataList = new ArrayList<Status>();
    private StatusesAPI mStatusesAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainview_navactivity);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        /**这是主layout的drawer*/
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        /**这是侧边栏的naviView*/
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();



//        mDataList = DatabaseTool.getInstance(this).queryStatuses();
//        mListView = (ListView) findViewById(R.id.listView_Statuses);
//        mAdapter = new StatusAdapter(this,
//                R.layout.mainview_listitem,
//                mDataList);
//        mListView.setAdapter(mAdapter);
////        mListView.addHeaderView(toolbar);
//
//        if (mDataList.size()==0){
//            showProgressDialog("正在加载数据...");
//            queryLastStatus();
//        }
//        Log.d(TAG, "onCreate: complete");
//
////        mListView.setOnItemSelectedListener(itemListSelectListener);
//        mListView.setOnItemClickListener(itemListClickListener);

        Fragment fragment = getFragmentManager().findFragmentById(R.id.Container);
        ArrayList<Status> mDataList = DatabaseTool.getInstance(this).queryStatuses();
        if (fragment==null){
            fragment = MainViewFragment.newInstance(mDataList);
            getFragmentManager().beginTransaction().add(R.id.Container,fragment,null).commit();
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
        if (id==R.id.action_refresh)
        {
            showProgressDialog("正在加载...");
            queryLastStatus();
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
        if (id == R.id.nav_gallery) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * 从服务器获取最新的50条微博消息
     **/
    private void queryLastStatus() {
        mStatusesAPI = ClientApiManger.getClientApiManger(this).getStatusesAPI();
        if (null==mStatusesAPI){
            startActivity(new Intent(MainViewAcivity.this, LoginActivity.class));
            finish();
            return;
        }
        mStatusesAPI.friendsTimeline(0, 0, 10, 1, false, 0, false, new RequestListener() {
            @Override
            public void onComplete(String s) {
                Util.handleJSONStringData(MainViewAcivity.this,s);

                Log.d(TAG, "onComplete: mDataList.size"+mDataList.size());
                refreshDatalist();
                Log.d(TAG, "onComplete: 99999999+9");
                closeProgressDialog();
            }
            @Override
            public void onWeiboException(WeiboException e) {
                e.printStackTrace();
            }
        });

    }

    private void refreshDatalist() {
        mStatuses = DatabaseTool.getInstance(MainViewAcivity.this).queryStatuses();
        mDataList.clear();
        for (Status s:mStatuses){
            mDataList.add(s);
        }
        mAdapter.notifyDataSetChanged();
    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("正在加载...");
            mProgressDialog.setCanceledOnTouchOutside(false);

        }
        mProgressDialog.show();
    }

    private void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(message);
            mProgressDialog.setCanceledOnTouchOutside(false);

        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    private void closeProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public MainViewAcivity getMainViewActivity(){
        return this;
    }

    private AdapterView.OnItemSelectedListener itemListSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Status status = mDataList.get(i);
            Intent intent = new Intent(MainViewAcivity.this,StatusDetailActivity.class);
            intent.putExtra(XzzConstants.STATUS_IDSTR,status.idstr);
            startActivity(intent);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private AdapterView.OnItemClickListener itemListClickListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Status status = mDataList.get(i);
            Intent intent = new Intent(MainViewAcivity.this,StatusDetailActivity.class);
            intent.putExtra(XzzConstants.STATUS_IDSTR,status.idstr);
            startActivity(intent);
        }
    };

}



/**
 *
 *
 *         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
 setSupportActionBar(toolbar);

 FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
 fab.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
.setAction("Action", null).show();
}
});
 *
 * //        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
 //                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
 //        drawer.setDrawerListener(toggle);
 //        toggle.syncState();
 **/
