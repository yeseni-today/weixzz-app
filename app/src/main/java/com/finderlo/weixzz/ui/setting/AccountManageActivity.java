package com.finderlo.weixzz.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.dao.TokenDao;
import com.finderlo.weixzz.model.model.TokenModel;
import com.finderlo.weixzz.ui.login.WebLoginActivity;
import com.finderlo.weixzz.ui.mainview.MainViewActivity;
import com.finderlo.weixzz.ui.usercenter.UserCenterActivity;

import java.util.List;

public class AccountManageActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(new SAdapter(this,TokenDao.getInstance(this).getAll()));

        findViewById(R.id.change_user_aty_add_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebLoginActivity.start(AccountManageActivity.this);
                AccountManageActivity.this.finish();
            }
        });
    }

    public static void start(Context context){
        Intent intent  = new Intent(context,AccountManageActivity.class);
        context.startActivity(intent);
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
            TextView uid = (TextView) view.findViewById(R.id.change_user_item_uid);
            TextView token = (TextView) view.findViewById(R.id.change_user_item_token);

            final TokenModel model = mModels.get(position);
            name.setText(model.getUserName());
            uid.setText(model.getUid());
            token.setText(model.getToken());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TokenDao.getInstance(AccountManageActivity.this).changeCurrentTokenModel(model);
                    Intent intent = new Intent(AccountManageActivity.this, MainViewActivity.class);
                    startActivity(intent);

                    AccountManageActivity.this.finish();

                }
            });
            return view;
        }
    }
}
