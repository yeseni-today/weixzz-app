package com.finderlo.weixzz.ui.StatusDetail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.finderlo.weixzz.adapter.StatusViewRecyclerAdapter;
import com.finderlo.weixzz.Constants;
import com.finderlo.weixzz.dao.StatusDao;
import com.finderlo.weixzz.R;
import com.finderlo.weixzz.model.bean.Status;

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
        view = inflater.inflate(R.layout.statusdetail_fragment2,container,false);

//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            list.add("Comment "+ i);
//        }
//        ListView listView = (ListView) view.findViewById(R.id.statusdetail_comment_listview);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
//        listView.setAdapter(arrayAdapter);

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
