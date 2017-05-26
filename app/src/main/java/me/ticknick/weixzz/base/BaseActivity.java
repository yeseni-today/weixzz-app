package me.ticknick.weixzz.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by Finderlo on 2016/8/18.
 */
public class BaseActivity extends AppCompatActivity {

    protected String TAG = this.getClass().getSimpleName();

    private Snackbar snackbar;
    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d(TAG, "Current Activity:  "+getClass().getSimpleName());
    }



    ProgressDialog mProgressDialog;

    

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("正在加载...");
            mProgressDialog.setCanceledOnTouchOutside(false);

        }
        mProgressDialog.show();
    }

    public void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(message);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    public void showSnackbar(View view,String message) {
        if (snackbar == null){
            snackbar = Snackbar.make(view,message,Snackbar.LENGTH_INDEFINITE);
        }
        snackbar.show();

    }

    public Snackbar getSnackbar(View v){
        if (snackbar == null) {
            snackbar = Snackbar.make(v,"",Snackbar.LENGTH_INDEFINITE);
        }
        return snackbar;
    }

    public void dismissSnackbar() {
        if (snackbar == null){
            return;
        }
        snackbar.dismiss();

    }

    public void closeProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
