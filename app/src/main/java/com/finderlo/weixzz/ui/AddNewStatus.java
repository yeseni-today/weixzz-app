package com.finderlo.weixzz.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.base.BaseActivity;
import com.finderlo.weixzz.dao.HttpClientUtils;
import com.finderlo.weixzz.dao.UrlConstants;
import com.finderlo.weixzz.dao.WeiboParameters;

import java.io.IOException;
import java.net.URLEncoder;

public class AddNewStatus extends BaseActivity {

    private static final String TAG = AddNewStatus.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_add_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("发送微博");

        final AppCompatEditText editText = (AppCompatEditText) findViewById(R.id.inputText);

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = editText.getText().toString();
                new UpdateStatusAsync().execute(status);
            }
        });


    }

    class UpdateStatusAsync extends AsyncTask<String,Voice,Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("正在发送");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            WeiboParameters weiboParameters = new WeiboParameters();
            weiboParameters.put("status",params[0]);
            String result = "";
            try {
                result = HttpClientUtils.doPostRequstWithAceesToken(UrlConstants.UPDATE,weiboParameters);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            Log.d(TAG, "onClick: "+result);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            closeProgressDialog();
            if (aBoolean){
                Toast.makeText(AddNewStatus.this,"发送成功",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(AddNewStatus.this,"发送失败，请重新尝试",Toast.LENGTH_SHORT).show();
            }
            AddNewStatus.this.finish();
        }
    }

    public static void start(Context context){
        Intent intent  = new Intent(context,AddNewStatus.class);
        context.startActivity(intent);
    }
}
