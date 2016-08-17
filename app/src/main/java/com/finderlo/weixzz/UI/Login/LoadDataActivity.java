package com.finderlo.weixzz.UI.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.finderlo.weixzz.Constants;
import com.finderlo.weixzz.UI.Mainview.MainViewAcivity;
import com.finderlo.weixzz.Utility.AccessTokenManger;
import com.finderlo.weixzz.Utility.Util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Finderlo on 2016/8/5.
 */
public class LoadDataActivity extends AppCompatActivity{

    public static int PIC_WIDTH = 0;
    private String TAG = LoadDataActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PIC_WIDTH = (Util.getScreenWidthDpi(this)-40)/3;


        if(AccessTokenManger.isAccessTokenExist(this)){
            // TODO: 2016/8/5 if token exist
            Intent intent = new Intent(LoadDataActivity.this, MainViewAcivity.class);
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
