package com.finderlo.weixzz.ui.StatusDetail;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.finderlo.weixzz.Constants;
import com.finderlo.weixzz.R;
import com.finderlo.weixzz.model.bean.Status;
import com.finderlo.weixzz.Utility.Util;

import java.util.ArrayList;
import java.util.List;

public class StatusDetailActivity extends AppCompatActivity {

    private static final String TAG = "StatusDetailActivity";

    Toolbar toolbar;
    Status mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statusdetail_activity2);

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

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Comment "+ i);
        }
        ListView listView = (ListView) findViewById(R.id.statusdetail_comment_listview);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.argb(00,49,37,37));
        collapsingToolbarLayout.setTitle("微博详情");
    }
}
