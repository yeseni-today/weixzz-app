package com.finderlo.weixzz.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.model.model.StatusModel;
import com.finderlo.weixzz.util.ImageLoader;
import com.finderlo.weixzz.base.BaseActivity;

import uk.co.senab.photoview.PhotoViewAttacher;

import java.util.ArrayList;

public class ImageDetailActivity extends BaseActivity {

    private static final String TAG = "TestActivity";

    public static final String ARG_STATUS_MODEL = "arg_status_model";
    public static final String ARG_INDEX = "arg_index";


    private StatusModel mStatusModel;
    private int mIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.image_detail_photo_view);

        if (getIntent()!=null){
            mStatusModel = getIntent().getParcelableExtra(ARG_STATUS_MODEL);
            mIndex = getIntent().getIntExtra(ARG_INDEX,0);
        }


        ViewPager viewPager = (ViewPager) findViewById(R.id.ViewPager);
        viewPager.setAdapter(new PhotosAdapter(getFragmentManager(),mStatusModel));

        viewPager.setCurrentItem(mIndex,false);

    }

    class PhotosAdapter extends FragmentPagerAdapter {


        ArrayList<Fragment> mFragmentArrayList = new ArrayList<Fragment>();


        PhotosAdapter(FragmentManager fm,StatusModel statusModel) {
            super(fm);
            if (statusModel.pic_urls==null||statusModel.pic_urls.size()<=0){
                return;
            }else {
                for (int i = 0; i < statusModel.pic_urls.size(); i++) {
                    mFragmentArrayList.add(new ImageFragemnt(statusModel.pic_urls.get(i).getLarge()));
                }
            }
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentArrayList.size();
        }
    }

    public static class ImageFragemnt extends Fragment{

        private String mUrl;

        public ImageFragemnt(){}

        public ImageFragemnt(String url){
            this.mUrl = url;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.image_fragment_layout,container,false);

            ImageView imageView = (ImageView) view;

            ImageLoader.load(getActivity(),mUrl,imageView);

            PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);
            attacher.update();
            attacher.setScaleType(ImageView.ScaleType.FIT_CENTER);

            return view;
        }
    }

    public static void start(Context context, StatusModel statusModel,int Imageindex){
        Intent intent  = new Intent(context,ImageDetailActivity.class);
        intent.putExtra(ARG_STATUS_MODEL,statusModel);
        intent.putExtra(ARG_INDEX,Imageindex);
        context.startActivity(intent);
    }

}
