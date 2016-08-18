package com.finderlo.weixzz.model;

import android.content.Context;

import com.finderlo.weixzz.Constants;
import com.finderlo.weixzz.base.WeiXzzApplication;

/**
 * Created by Finderlo on 2016/8/6.
 */
public class APIManger {

    private static Context sContext;
    private static APIManger sAPIManger;

    static {
        sContext = WeiXzzApplication.getContext();
    }

//    private APIManger(Context context){
//        sContext = context.getApplicationContext();
//    }

//    public static APIManger getClientApiManger(Context context) {
//        if (sAPIManger ==null){
//            sAPIManger = new APIManger(context);
//        }
//        return sAPIManger;
//    }

    /**这些是管理的API*/
    private static StatusesAPI sStatusesAPI;

    public  static StatusesAPI getStatusesAPI() {
        if (AccessTokenManger.isAccessTokenExist(sContext)){
            sStatusesAPI = new StatusesAPI(sContext, Constants.APP_KEY,AccessTokenManger.readAccessToken(sContext));
        }else {
            new Exception("Token为空").printStackTrace();
        }
        return sStatusesAPI;
    }
}
