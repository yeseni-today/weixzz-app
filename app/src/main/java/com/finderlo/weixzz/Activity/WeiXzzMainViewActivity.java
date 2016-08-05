package com.finderlo.weixzz.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.Util.CallbackListener.HttpLoadPicCallbackListener;
import com.finderlo.weixzz.Util.HttpUtil;
import com.finderlo.weixzz.Util.StatusDatabaseTool;
import com.sina.weibo.sdk.openapi.models.Status;

import java.util.ArrayList;
import java.util.List;

public class WeiXzzMainViewActivity extends AppCompatActivity {

    private static final String TAG = "WeiXzzMainViewActivity";

    private ListView mListView;
    private StatusAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainview_activity);
        mListView = (ListView) findViewById(R.id.listView_Statuses);
        StatusDatabaseTool statusDatabaseTool = StatusDatabaseTool.getInstance(this);
        ArrayList<Status> statusList = StatusDatabaseTool.getInstance(this).queryStatuses();
        mAdapter = new StatusAdapter(this,
                R.layout.mainview_listitem,
                statusList);
        mListView.setAdapter(mAdapter);


    }


    public class StatusAdapter extends ArrayAdapter<Status> {
        private int mResource;

        public StatusAdapter(Context context, int resource, List<Status> objects) {
            super(context, resource, objects);
            mResource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Status status = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(mResource, null);
            TextView title = (TextView) view.findViewById(R.id.list_item_username);
            TextView content = (TextView) view.findViewById(R.id.list_item_statusContent);
            TextView isVerified = (TextView) view.findViewById(R.id.textview_flag_Verified);
            TextView verifiedReason = (TextView) view.findViewById(R.id.list_item_Verified_reason);
            final ImageView userPic = (ImageView) view.findViewById(R.id.image_user_pic);

            HttpUtil.loadPicFromUrl(status.user.profile_image_url, new HttpLoadPicCallbackListener() {
                @Override
                public void onComplete(final Bitmap bitmap) {
                    final Bitmap bitmap1 = bitmap;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            userPic.setImageBitmap(bitmap1);
                        }
                    });
                }

                @Override
                public void onError(Exception exception) {
                    exception.printStackTrace();
                }
            });
            isVerified.setText(status.user.verified ? getString(R.string.isVerified):getString(R.string.isUnVerified));
            verifiedReason.setText(status.user.verified_reason);
            title.setText(status.user.name);
            content.setText(status.text);
            return view;
        }
    }

}
