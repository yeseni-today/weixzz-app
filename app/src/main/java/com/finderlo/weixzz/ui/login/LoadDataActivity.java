package com.finderlo.weixzz.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.finderlo.weixzz.ui.mainview.MainViewActivity;
import com.finderlo.weixzz.base.BaseActivity;
import com.finderlo.weixzz.model.AccessTokenManger;
import com.finderlo.weixzz.utility.Util;

/**
 * 应用初始化的activity
 * 判断第一次是否是登陆还是进入主界面
 */
public class LoadDataActivity extends BaseActivity {

    public static int PIC_WIDTH = 0;
    private String TAG = LoadDataActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PIC_WIDTH = (Util.getScreenWidthDpi(this)-40)/3;

        if(AccessTokenManger.isAccessTokenExist(this)){
            // TODO: 2016/8/5 if token exist
            Intent intent = new Intent(LoadDataActivity.this, MainViewActivity.class);
            startActivity(intent);

            finish();
        }else {
            // TODO: 2016/8/5  token isn't exist ,need login
            Intent intent = new Intent(LoadDataActivity.this,WebLoginActivity.class);
            startActivity(intent);

            finish();
        }



    }
}
