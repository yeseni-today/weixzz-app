package com.finderlo.weixzz.ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.finderlo.weixzz.ui.Mainview.MainViewActivity;
import com.finderlo.weixzz.base.BaseActivity;
import com.finderlo.weixzz.model.AccessTokenManger;
import com.finderlo.weixzz.Utility.Util;

/**
 * Created by Finderlo on 2016/8/5.
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
            Intent intent = new Intent(LoadDataActivity.this,LoginActivity.class);
            startActivity(intent);

            finish();
        }



    }
}
