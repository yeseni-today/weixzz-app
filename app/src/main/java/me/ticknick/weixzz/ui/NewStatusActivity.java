package me.ticknick.weixzz.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import me.ticknick.weixzz.R;

import java.io.IOException;

import me.ticknick.weixzz.base.BaseActivity;
import me.ticknick.weixzz.dao.HttpClientUtils;
import me.ticknick.weixzz.dao.UrlConstants;
import me.ticknick.weixzz.dao.WeiboParameters;

public class NewStatusActivity extends BaseActivity {

    private static final String TAG = NewStatusActivity.class.getSimpleName();

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
                Toast.makeText(NewStatusActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(NewStatusActivity.this,"发送失败，请重新尝试",Toast.LENGTH_SHORT).show();
            }
            NewStatusActivity.this.finish();
        }
    }

    public static void start(Context context){
        Intent intent  = new Intent(context,NewStatusActivity.class);
        context.startActivity(intent);
    }
}
