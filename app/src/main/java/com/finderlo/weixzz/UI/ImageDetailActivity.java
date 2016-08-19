package com.finderlo.weixzz.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.Utility.ImageLoader;
import com.finderlo.weixzz.base.BaseActivity;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageDetailActivity extends BaseActivity {

    private static final String TAG = "TestActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.image_detail_photo_view);

        String url = "";
        if (getIntent() != null) {
            url = getIntent().getStringExtra("image_url");
            Log.d(TAG, url);
        } else {
            Log.d(TAG, "onCreate: url is null,use default url");
             url = "http://ww3.sinaimg.cn/thumbnail/afbd4572jw1f6eb9eppj2j20c80c5dh4.jpg";
        }

        url = url.replace("thumbnail", "large");

        ImageView mImageView = (ImageView) findViewById(R.id.imageView);


        ImageLoader.load(this, url, mImageView);
        PhotoViewAttacher mAttacher;
        mAttacher = new PhotoViewAttacher(mImageView);
        Log.d(TAG, "onCreate: ");

    }

}
