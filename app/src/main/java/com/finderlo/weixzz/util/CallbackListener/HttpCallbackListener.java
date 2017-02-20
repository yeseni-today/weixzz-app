package com.finderlo.weixzz.util.callbackListener;

/**
 * Created by Finderlo on 2016/8/3.
 */
public interface HttpCallbackListener {
    void onComplete();
    void onError(Exception e);
}

