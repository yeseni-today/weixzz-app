package com.finderlo.weixzz.UI.StatusDetail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.finderlo.weixzz.Adapter.StatusViewRecyclerAdapter;
import com.finderlo.weixzz.Constants;
import com.finderlo.weixzz.Dao.StatusDao;
import com.finderlo.weixzz.R;
import com.finderlo.weixzz.Model.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finderlo on 2016/8/4.
 */
public class StatusDetailFragment extends Fragment {

    private static final String TAG = "StatusDetailFragment";
    private Status mStatus;
    public StatusDetailFragment() {
        super();
    }
    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            String status_idstr = getArguments().getString(Constants.STATUS_IDSTR);
            mStatus = StatusDao.getInstance().queryStatus(StatusDao.TYPE_IDSTR,status_idstr);
        }else mStatus = Status.parse(Constants.TEST_STATUS_JSON_STRING_1);
        Log.d(TAG, "onCreate: "+mStatus.text);




    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.statusdetail_fragment,container,false);
//        imageViews_count_2to9 = new ImageView[9];
//
//
//        retweetedimageViews_count_2to9 = new ImageView[9];
//        for (int i = 0; i < 9; i++) {
//            imageViews_count_2to9[i] = (ImageView) view.findViewById(imageView_grid[i]);
//            retweetedimageViews_count_2to9[i] = (ImageView) view.findViewById(retweetedimageView_grid[i]);
//        }
//        mImageView_count_1 = (ImageView) view.findViewById(R.id.statusdetail_pic_count_one_imageview);
//        mRetweetedImageView_count_1 = (ImageView) view.findViewById(R.id.statusdetail_retweeted_pic_count_one_imageview);
//
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Comment "+ i);
        }
        ListView listView = (ListView) view.findViewById(R.id.statusdetail_comment_listview);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
//
//        initView(view);
//
//        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.addbar_layout);
//        appBarLayout.invalidate();
//        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.detail_container);

//        StatusViewitem statusViewitem = new StatusViewitem(getActivity());
//        statusViewitem.setStatus(getActivity(),mStatus);
//        statusViewitem.setHeadViewVisible(false);
//        linearLayout.addView(statusViewitem);
//        linearLayout.invalidate();

        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.statusdetail_statuscoent_framlayout);

        StatusViewRecyclerAdapter adapter = new StatusViewRecyclerAdapter(getActivity());
        frameLayout.addView(adapter.getView4Status(getActivity(),mStatus));

        return view;
    }







    public static StatusDetailFragment newInstance(String status_idstr){
        StatusDetailFragment statusDetailFragment = new StatusDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.STATUS_IDSTR,status_idstr);
        statusDetailFragment.setArguments(bundle);
        return statusDetailFragment;
    }

    public static StatusDetailFragment newInstance(){
        StatusDetailFragment statusDetailFragment = new StatusDetailFragment();
        return statusDetailFragment;
    }
}
