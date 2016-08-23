package com.finderlo.weixzz.ui.StatusDetail;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.base.BaseActivity;
import com.finderlo.weixzz.model.model.StatusModel;
import com.finderlo.weixzz.Utility.Util;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class StatusDetailActivity extends BaseActivity {


    private static final String ARG_STATUS_MODEL = "arg_status_model";
    private static final String ARG_BUNDLE = "arg_bundle";

    Toolbar toolbar;
    StatusModel mStatusModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statusdetail_activity2);

        if (getIntent()!=null){
            mStatusModel = getIntent().getBundleExtra(ARG_BUNDLE).getParcelable(ARG_STATUS_MODEL);
        }

        Log.d(TAG, "The status detail: "+new Gson().toJson(mStatusModel));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.id_ContentFragment);

        if (fragment == null) {
            fragment = StatusDetailFragment.newInstance(mStatusModel);
            manager.beginTransaction().add(R.id.id_ContentFragment, fragment).commit();
        }

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("CommentModel "+ i);
        }

        ListView listView = (ListView) findViewById(R.id.statusdetail_comment_listview);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.argb(00,49,37,37));
        collapsingToolbarLayout.setTitle("微博详情");
    }



    public static void start(Context context,StatusModel status){
        Intent intent = new Intent(context,StatusDetailActivity.class);
        Log.d("StatusDetailActivity", "start: "+new Gson().toJson(status));
        Bundle arg = new Bundle();
        arg.putParcelable(ARG_STATUS_MODEL,status);
        intent.putExtra(ARG_BUNDLE,arg);
        context.startActivity(intent);
    }
}
