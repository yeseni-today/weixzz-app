package me.ticknick.weixzz.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import me.ticknick.weixzz.base.BaseActivity;
import me.ticknick.weixzz.dao.TokenDao;
import me.ticknick.weixzz.ui.mainview.MainViewActivity;
import me.ticknick.weixzz.util.Util;

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

        PIC_WIDTH = (Util.getScreenWidthDpi(this) - 40) / 3;

        if (TokenDao.isAccessTokenExist(this)) {
            // if token exist
            Intent intent = new Intent(LoadDataActivity.this, MainViewActivity.class);
            startActivity(intent);
            finish();
        } else {
            // token isn't exist ,need login
            Intent intent = new Intent(LoadDataActivity.this, WebLoginActivity.class);
            startActivity(intent);
            finish();
        }


    }
}
