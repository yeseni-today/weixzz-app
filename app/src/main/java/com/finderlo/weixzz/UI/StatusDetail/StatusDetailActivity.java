package com.finderlo.weixzz.UI.StatusDetail;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.finderlo.weixzz.Constants;
import com.finderlo.weixzz.R;
import com.finderlo.weixzz.SinaAPI.openapi.models.Status;
import com.finderlo.weixzz.Util.Util;

public class StatusDetailActivity extends AppCompatActivity {

    private static final String TAG = "StatusDetailActivity";

    Toolbar toolbar;
    Status mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statusdetail_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String status_idstr = "";
        if (intent == null){
        }else status_idstr = intent.getStringExtra(Constants.STATUS_IDSTR);

        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.id_ContentFragment);
        Log.d(TAG, "onCreate: " + Util.booleanToString(fragment == null));
        if (fragment == null) {
            fragment = StatusDetailFragment.newInstance(status_idstr);
            manager.beginTransaction().add(R.id.id_ContentFragment, fragment).commit();
        }
    }
}
