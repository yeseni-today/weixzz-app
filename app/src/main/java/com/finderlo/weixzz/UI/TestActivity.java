package com.finderlo.weixzz.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.Utility.ImageLoader;
import com.finderlo.weixzz.Widgt.MatrixImageView;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.testlayout);

        MatrixImageView mImageView = (MatrixImageView) findViewById(R.id.imageView);
        String url = "http://ww3.sinaimg.cn/thumbnail/afbd4572jw1f6eb9eppj2j20c80c5dh4.jpg";

        ImageLoader.load(this,url,mImageView);
        Log.d(TAG, "onCreate: ");

    }

}
