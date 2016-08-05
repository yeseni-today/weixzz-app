package com.finderlo.weixzz.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.Util.StatusDatabaseTool;
import com.finderlo.weixzz.XzzConstants;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.User;
import com.sina.weibo.sdk.widget.*;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.finderlo.weixzz.Util.AccessTokenKeeper;

/**
 * Created by Finderlo on 2016/8/1 0001.
 */
public class WeiXzzLoginMainActivity extends AppCompatActivity {

    private static final String TAG = "WeiXzzLoginMainActivity";

    private LoginButton mLoginButton;
    private Button mBtnTokenInfo;
    private Button mBtnGetLastWeibo;
    private StatusesAPI mStatusesAPI;
    private Oauth2AccessToken mOauth2AccessToken;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginview_aty);
        mBtnTokenInfo = (Button) findViewById(R.id.btn_TokenInfo);

        if (AccessTokenKeeper.readAccessToken(getApplicationContext()) != null) {
            mOauth2AccessToken = AccessTokenKeeper.readAccessToken(getApplicationContext());
            mStatusesAPI = new StatusesAPI(this, XzzConstants.APP_KEY, mOauth2AccessToken);
            String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                    new java.util.Date(mOauth2AccessToken.getExpiresTime()));
            String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
            mBtnTokenInfo.setText(String.format(format, mOauth2AccessToken.getToken(), date));
        } else {
            Log.d(TAG, "onCreate: Token is null");
        }
//        mBtnTokenInfo.setText();

        mBtnGetLastWeibo = (Button) findViewById(R.id.btn_get_last_weibo);
        mBtnGetLastWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                showProgressDialog();
                queryLastStatus();

            }
        });

        Button button1 = (Button) findViewById(R.id.btn_weibo);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeiXzzLoginMainActivity.this, WeiXzzMainViewActivity.class));
            }
        });

        mLoginButton = (LoginButton) findViewById(R.id.btn_sina_login);
        mLoginButton.setWeiboAuthInfo(XzzConstants.APP_KEY,
                XzzConstants.REDIRECT_URL, XzzConstants.SCOPE,
                mWeiboAuthListener);

        Log.i(TAG, "onCreate: Complete");

    }

    /**
     * 从服务器获取最新的50条微博消息
     **/
    private void queryLastStatus() {
        mStatusesAPI.friendsTimeline(0, 0, 50, 1, false, 0, false, new RequestListener() {
            @Override
            public void onComplete(String s) {
                JSONArray array;
                List<Status> statusList = new ArrayList<Status>();
                List<User> userList = new ArrayList<User>();
                try {
                    array = new JSONObject(s).getJSONArray("statuses");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject statusObj = (JSONObject) array.get(i);
                        Status status = Status.parse(statusObj);
                        User user = User.parse(statusObj.getJSONObject("user"));
                        status.user_idstr = user.idstr;
                        status.user = user;
                        statusList.add(status);
                        userList.add(user);
                        Log.i(TAG, "onComplete: 用户："+user.name+"  是否认证:" + status.user.verified + "\n");
                    }
                    StatusDatabaseTool.getInstance(WeiXzzLoginMainActivity.this).insertStatuses(statusList);
                    StatusDatabaseTool.getInstance(WeiXzzLoginMainActivity.this).insertUsers(userList);
                    closeProgressDialog();
                    Toast.makeText(WeiXzzLoginMainActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e(TAG, "onComplete: 转换为json数组失败", e);
                    Toast.makeText(WeiXzzLoginMainActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                } finally {
                    closeProgressDialog();
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
//                        e.printStackTrace();
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
    }

    /**
     * 登入按钮的监听器，接收授权结果。
     */
    private WeiboAuthListener mWeiboAuthListener = new WeiboAuthListener() {
        @Override
        public void onComplete(Bundle values) {
            //这是获取用户的taken信息
            mOauth2AccessToken = Oauth2AccessToken.parseAccessToken(values);
            mStatusesAPI = new StatusesAPI(WeiXzzLoginMainActivity.this, XzzConstants.APP_KEY, mOauth2AccessToken);
            ////显示token的有效期
            if (mOauth2AccessToken != null && mOauth2AccessToken.isSessionValid()) {
                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                        new java.util.Date(mOauth2AccessToken.getExpiresTime()));
                String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
                mBtnTokenInfo.setText(String.format(format, mOauth2AccessToken.getToken(), date));

                AccessTokenKeeper.writeAccessToken(getApplicationContext(), mOauth2AccessToken);
            } else {
                // 当您注册的应用程序签名不正确时，就会收到错误Code，请确保签名正确
                String code = values.getString("code", "");
                Log.e(TAG, "onComplete: " + code);
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(WeiXzzLoginMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(WeiXzzLoginMainActivity.this,
                    "Cancel", Toast.LENGTH_SHORT).show();
        }
    };

    private void savaStatuses(Status status) {

    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("正在加载...");
            mProgressDialog.setCanceledOnTouchOutside(false);

        }
        mProgressDialog.show();
    }

    private void closeProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
