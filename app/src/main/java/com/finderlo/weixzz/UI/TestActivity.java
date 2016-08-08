package com.finderlo.weixzz.UI;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.finderlo.weixzz.Database.DatabaseTool;
import com.finderlo.weixzz.R;
import com.finderlo.weixzz.SinaAPI.openapi.models.Status;
import com.finderlo.weixzz.UI.Mainview.MainViewFragment;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.testlayout);

        Fragment fragment = getFragmentManager().findFragmentById(R.id.Container);
        ArrayList<Status> mDataList = DatabaseTool.getInstance(this).queryStatuses();
        if (fragment==null){
            fragment = MainViewFragment.newInstance(mDataList);
            getFragmentManager().beginTransaction().add(R.id.Container,fragment,null).commit();
        }

    }

}
