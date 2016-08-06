package com.finderlo.weixzz.UI.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.finderlo.weixzz.R;
import com.finderlo.weixzz.SinaAPI.widget.LoginButton;
import com.finderlo.weixzz.UI.Mainview.MainViewAcivity;
import com.finderlo.weixzz.Util.AccessTokenKeeper;
import com.finderlo.weixzz.XzzConstants;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;


/**
 * Created by Finderlo on 2016/8/5.
 */
public class LoginActivity extends AppCompatActivity {

    private static  final  String TAG = "LoginActivity";

    private LoginButton mLoginButton;

    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginview_activity);


        mLoginButton = new LoginButton(this);
        mLoginButton.setWeiboAuthInfo(XzzConstants.APP_KEY,
                XzzConstants.REDIRECT_URL, XzzConstants.SCOPE,
                mWeiboAuthListener);

        mImageView = (ImageView) findViewById(R.id.imageButton_login);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginButton.onClick(view);
            }
        });
        findViewById(R.id.textView_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginButton.onClick(view);
            }
        });
//        mLoginButton.setStyle(4);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginButton.onActivityResult(requestCode,resultCode,data);
        Log.d(TAG, "onActivityResult: ");
        startActivity(new Intent(LoginActivity.this, MainViewAcivity.class));
    }

    /**
     * 登入按钮的监听器，接收授权结果。
     */
    private WeiboAuthListener mWeiboAuthListener = new WeiboAuthListener() {
        @Override
        public void onComplete(Bundle values) {
            Log.d(TAG, "onComplete: 9999");
            //这是获取用户的taken信息
            Oauth2AccessToken mOauth2AccessToken = Oauth2AccessToken.parseAccessToken(values);
            ////显示token的有效期
            if (mOauth2AccessToken != null && mOauth2AccessToken.isSessionValid()) {
//                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
//                        new java.util.Date(mOauth2AccessToken.getExpiresTime()));
//                String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
//                mBtnTokenInfo.setText(String.format(format, mOauth2AccessToken.getToken(), date));
                AccessTokenKeeper.setOauth2AccessToken(Oauth2AccessToken.parseAccessToken(values));
                AccessTokenKeeper.writeAccessToken(getApplicationContext(), mOauth2AccessToken);
                Log.d(TAG, "WeiboAuthListener onComplete: ");
            } else {
                // 当您注册的应用程序签名不正确时，就会收到错误Code，请确保签名正确
                String code = values.getString("code", "");
                Log.e(TAG, "onComplete: 授权错误代码" + code);
            }
        }
        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this,
                    "授权取消", Toast.LENGTH_SHORT).show();
        }
    };

}