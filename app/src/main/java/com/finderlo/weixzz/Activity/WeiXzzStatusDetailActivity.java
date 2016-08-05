package com.finderlo.weixzz.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.Util.StatusDatabaseTool;
import com.finderlo.weixzz.Util.Util;
import com.finderlo.weixzz.XzzConstants;
import com.sina.weibo.sdk.openapi.models.Status;

public class WeiXzzStatusDetailActivity extends AppCompatActivity {

    private static final String TAG = "StatusDetailActivity";

    Toolbar toolbar;
    Status mStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_xzz_status_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String status_idstr = intent.getStringExtra(XzzConstants.STATUS_IDSTR);
        mStatus = StatusDatabaseTool.getInstance(
                this).queryStatus(StatusDatabaseTool.TYPE_IDSTR,status_idstr);
        Log.d(TAG, "onCreate: mStatus:"+Util.booleanToString(mStatus==null));
        Log.d(TAG, "onCreate: toolbar:"+Util.booleanToString(toolbar==null));
        toolbar.setTitle(mStatus.user.name);


        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.id_ContentFragment);
        Log.d(TAG, "onCreate: "+ Util.booleanToString(fragment==null));
        if (fragment==null){
            fragment = WeiXzzStatusDetailFragment.newInstance(status_idstr);
            manager.beginTransaction().add(R.id.id_ContentFragment,fragment).commit();
        }
    }
}
