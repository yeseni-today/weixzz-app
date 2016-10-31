package com.finderlo.weixzz.base;

/**
 * Created by Finderlo on 2016/8/19.
 */

public class WeiException extends Exception {
    public WeiException() {
    }

    public WeiException(String message) {
        super(message);
    }

    public WeiException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeiException(Throwable cause) {
        super(cause);
    }

//    public WeiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//        super(message, cause, enableSuppression, writableStackTrace);
//    }
}
