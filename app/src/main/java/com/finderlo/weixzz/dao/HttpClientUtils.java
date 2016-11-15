package com.finderlo.weixzz.dao;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import static com.finderlo.weixzz.BuildConfig.DEBUG;


/**
 * Created by Administrator on 2015-7-17.
 */
public class HttpClientUtils {
    private static final String TAG = HttpClientUtils.class.getSimpleName();

    private final static OkHttpClient client = new OkHttpClient();

    public static String doGetRequstWithAceesToken(String url, WeiboParameters params) throws IOException {
        return getInsance().doGetRequstWithAceesToken0(url, params);
    }

    public static String doGetRequst(String url, WeiboParameters param) throws IOException {
        return getInsance().doGetRequst0(url, param);
    }

    public static String doGetRequst(String url) throws IOException {
        return getInsance().doGetRequst0(url);
    }

    public static String doPostRequstWithAceesToken(String url, WeiboParameters params) throws IOException {
        return getInsance().doPostRequstWithAceesToken0(url, params);
    }

    public static String doPostRequst(String url, WeiboParameters params) throws IOException {
        return getInsance().doPostRequst0(url, params);
    }

    public String doGetRequstWithAceesToken0(String url, WeiboParameters params) throws IOException {
        params.put("access_token", mToken);
        Log.d(TAG, "doGetRequstWithAceesToken0: token"+mToken);
        return doGetRequst(url, params);
    }

    public String doGetRequst0(String url, WeiboParameters param) throws IOException {
        String send = param.encode();
        url = url + "?" + send;
        return doGetRequst(url);
    }


    public String doGetRequst0(String url) throws IOException {

        if (DEBUG) {
            Log.i(TAG, url);
        }
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();


        if (response.isSuccessful()) {

            String result = response.body().string();
            if (DEBUG) {
                Log.i(TAG, result);
            }
            return result;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    public String doPostRequstWithAceesToken0(String url, WeiboParameters params) throws IOException {
        params.put("access_token", mToken);
        return doPostRequst(url, params);
    }


    public String doPostRequst0(String url, WeiboParameters params) throws IOException {

        if (DEBUG) {
            Log.i(TAG, url);
        }
        RequestBody body = params.convertToRequestBody();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();


        if (response.isSuccessful()) {

            String result = response.body().string();
            if (DEBUG) {
                Log.i(TAG, result);
            }
            return result;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    public static void setAccessToken(String token) {
        getInsance().setToken(token);
    }

    private static HttpClientUtils sInstance = new HttpClientUtils();
    private String mToken;

    private HttpClientUtils() {
    }

    private static HttpClientUtils getInsance() {
        return sInstance;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        this.mToken = token;
    }
}
