package com.finderlo.weixzz.model;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.dao.LoginDao;

import java.util.Map;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mCurrentTextView;
    TextView mAllUserTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        mCurrentTextView = (TextView) findViewById(R.id.test_tv_current);
        mAllUserTextView = (TextView) findViewById(R.id.test_tv_alluser);
        Button button = (Button) findViewById(R.id.test_btn_change);

        testLogin();

        button.setOnClickListener(this);
    }

    TokenDao mTokenDao;

    @Override
    public void onClick(View v) {
        int count = mTokenDao.getTokenCount();
        int index = 0;

        if (index == count) {
            index = 0;
        }

        mTokenDao.setCurrentUser(index);
        setAllUserInfo();
        setCurrentUserInfo();
    }

    public void setCurrentUserInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name: ");
        stringBuilder.append(mTokenDao.getName()).append("\n");
        stringBuilder.append("getAccessToken: ");
        stringBuilder.append(mTokenDao.getAccessToken()).append("\n");
        stringBuilder.append("getCurrentTokenIndex: ");
        stringBuilder.append(mTokenDao.getCurrentTokenIndex()).append("\n");
        stringBuilder.append("getExpireDate: ");
        stringBuilder.append(mTokenDao.getExpireDate()).append("\n");
        stringBuilder.append("getTokenCount: ");
        stringBuilder.append(mTokenDao.getTokenCount()).append("\n");
        stringBuilder.append("getUid: ");
        stringBuilder.append(mTokenDao.getUid()).append("\n");

        mCurrentTextView.setText(stringBuilder.toString());
    }

    public void setAllUserInfo() {
        Map<String, Integer> users = mTokenDao.getTokens();

        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<String, Integer> entry : users.entrySet()) {
            stringBuilder.append("name:").append(entry.getKey()).append("; index:").append(entry.getValue()).append("\n");
        }
        mAllUserTextView.setText(stringBuilder.toString());
    }

    public void testLogin() {

        if (mTokenDao == null) {
            mTokenDao = TokenDao.getInstance(this);
        }

        for (int i = 0; i < 5; i++) {
            String token = "token" + i;
            String uuid = "uuid" + i;
            String exprite = "100" + i;
            String name = "name" + i;
            mTokenDao.loginTest(token, exprite, uuid, name);
        }
    }


}
