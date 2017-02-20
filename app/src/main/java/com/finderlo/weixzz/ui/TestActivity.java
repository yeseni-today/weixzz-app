package com.finderlo.weixzz.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.dao.LoginDao;
import com.finderlo.weixzz.dao.TokenDao;
import com.finderlo.weixzz.model.model.TokenModel;
import com.finderlo.weixzz.ui.login.LoadDataActivity;
import com.finderlo.weixzz.ui.mainview.MainViewActivity;

import java.util.List;
import java.util.Map;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = TestActivity.class.getSimpleName();
    private ListView mListView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(new SAdapter(this,TokenDao.getInstance(this).getAll()));

        List<TokenModel> list = TokenDao.getInstance(this).getAll();
        for (TokenModel model : list) {
            Log.d(TAG, "onCreate: Model uid :"+model.getUid()+";name:"+model.getUserName()+";iscurrent:"+model.isCurrent());
        }
    }


    private class SAdapter extends BaseAdapter {

        List<TokenModel> mModels;
        Context mContext;

        public SAdapter(Context context,List<TokenModel> models) {
            mModels = models;
            mContext =context;
        }

        @Override
        public int getCount() {
            return mModels.size();
        }

        @Override
        public Object getItem(int position) {
            return mModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.change_user_item,parent,false);
            TextView name = (TextView) view.findViewById(R.id.change_user_item_name);

            final TokenModel model = mModels.get(position);
            name.setText(model.getUserName());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TokenDao.getInstance(TestActivity.this).changeCurrentTokenModel(model);
                    Intent intent = new Intent(TestActivity.this, MainViewActivity.class);
                    startActivity(intent);

                    TestActivity.this.finish();

                }
            });
            return view;
        }
    }
}
