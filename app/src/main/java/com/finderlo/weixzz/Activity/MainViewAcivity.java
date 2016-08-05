package com.finderlo.weixzz.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.Util.AccessTokenKeeper;
import com.finderlo.weixzz.Util.StatusDatabaseTool;
import com.finderlo.weixzz.Util.Util;
import com.finderlo.weixzz.XzzConstants;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.widget.LoginButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainViewAcivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "WeiXzzMainViewActivity";

    private ListView mListView;
    private com.finderlo.weixzz.Activity.StatusAdapter mStatusAdapter;
    private ArrayList<Status> mStatuses;
    private LoginButton mLoginButton;
    private StatusAdapter mAdapter;
    private Oauth2AccessToken mOauth2AccessToken = null;
    private StatusesAPI mStatusesAPI = null;
    private ProgressDialog mProgressDialog;
    private AlertDialog mAlertDialog;
    private ArrayList<Status> mDataList = new ArrayList<Status>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainview_navactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /**这是主layout的drawer*/
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        /**这是侧边栏的naviView*/
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        mLoginButton = new LoginButton(this);
        mLoginButton.setWeiboAuthInfo(XzzConstants.APP_KEY,
                XzzConstants.REDIRECT_URL, XzzConstants.SCOPE,
                mWeiboAuthListener);

        mDataList = StatusDatabaseTool.getInstance(this).queryStatuses();
        Log.d(TAG, "onCreate: mDataList size"+mDataList.size());
        mListView = (ListView) findViewById(R.id.listView_Statuses);
        mAdapter = new StatusAdapter(this,
                R.layout.mainview_listitem,
                mDataList);
        mListView.setAdapter(mAdapter);
        mAlertDialog =  new AlertDialog.Builder(this).setView(mLoginButton)
                .setTitle("你需要进行授权").create();

        if (mDataList.size()==0){
            showProgressDialog("正在加载数据...");
//            if (isUserTokenExist()) {
                queryLastStatus();
//            } else {
//                closeProgressDialog();
//                showProgressDialog("正在授权...");
//                closeProgressDialog();
//                mAlertDialog.show();
//            }
        }
        Log.d(TAG, "onCreate: complete");

//        mListView.setOnItemSelectedListener(itemListSelectListener);
        mListView.setOnItemClickListener(itemListClickListener);

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

    private boolean isUserTokenExist(){

        if (!"".equals(AccessTokenKeeper.readAccessToken(getApplicationContext()).getUid())) {
            mOauth2AccessToken = AccessTokenKeeper.readAccessToken(getApplicationContext());
            mStatusesAPI = new StatusesAPI(this, XzzConstants.APP_KEY, mOauth2AccessToken);
            String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                    new java.util.Date(mOauth2AccessToken.getExpiresTime()));
            String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
            Log.i(TAG, "isUserTokenExist: "+String.format(format, mOauth2AccessToken.getToken(), date));
            return true;
        }
        return false;
    }

    /**
     * 从服务器获取最新的50条微博消息
     **/
    private void queryLastStatus() {
        if (null==mStatusesAPI){
            mAlertDialog.show();
            return;
        }
        mStatusesAPI.friendsTimeline(0, 0, 10, 1, false, 0, false, new RequestListener() {
            @Override
            public void onComplete(String s) {
                Util.handleJSONStringData(MainViewAcivity.this,s);
                mStatuses = StatusDatabaseTool.getInstance(MainViewAcivity.this).queryStatuses();
                freshDatalist();
                Log.d(TAG, "onComplete: 99999999+9");
                closeProgressDialog();
            }
            @Override
            public void onWeiboException(WeiboException e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * 用于授权界面的回调回调
     **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 在此处调用
        mLoginButton.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: 授权回调");
        mAlertDialog.dismiss();
        showProgressDialog("授权成功,正在加载数据...");
        queryLastStatus();



    }

    /**
     * 登入按钮的监听器，接收授权结果。
     */
    private WeiboAuthListener mWeiboAuthListener = new WeiboAuthListener() {
        @Override
        public void onComplete(Bundle values) {
            //这是获取用户的taken信息
            mOauth2AccessToken = Oauth2AccessToken.parseAccessToken(values);
            mStatusesAPI = new StatusesAPI(MainViewAcivity.this, XzzConstants.APP_KEY, mOauth2AccessToken);
            ////显示token的有效期
            if (mOauth2AccessToken != null && mOauth2AccessToken.isSessionValid()) {
                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                        new java.util.Date(mOauth2AccessToken.getExpiresTime()));
                String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
//                mBtnTokenInfo.setText(String.format(format, mOauth2AccessToken.getToken(), date));

                AccessTokenKeeper.writeAccessToken(getApplicationContext(), mOauth2AccessToken);
            } else {
                // 当您注册的应用程序签名不正确时，就会收到错误Code，请确保签名正确
                String code = values.getString("code", "");
                Log.e(TAG, "onComplete: 授权错误代码" + code);
            }
        }
        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(MainViewAcivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCancel() {
            Toast.makeText(MainViewAcivity.this,
                    "授权取消", Toast.LENGTH_SHORT).show();
        }
    };

    private void freshDatalist(){
        mStatuses = StatusDatabaseTool.getInstance(this).queryStatuses();
        mDataList.clear();
        for (Status status:mStatuses)
            mDataList.add(status);
        mAdapter.notifyDataSetChanged();
        mListView.setSelection(0);
        Log.d(TAG, "freshDatalist: complete");
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
            Intent intent = new Intent(MainViewAcivity.this,WeiXzzStatusDetailActivity.class);
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
            Intent intent = new Intent(MainViewAcivity.this,WeiXzzStatusDetailActivity.class);
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
