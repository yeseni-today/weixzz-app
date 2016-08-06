package com.finderlo.weixzz.Util;

import android.content.Context;

import com.finderlo.weixzz.SinaAPI.openapi.StatusesAPI;
import com.finderlo.weixzz.XzzConstants;

/**
 * Created by Finderlo on 2016/8/6.
 */
public class ClientApiManger {

    private static Context sContext;
    private static ClientApiManger sClientApiManger;

    private ClientApiManger(Context context){
        sContext = context.getApplicationContext();
    }

    public static ClientApiManger getClientApiManger(Context context) {
        if (sClientApiManger==null){
            sClientApiManger = new ClientApiManger(context);
        }
        return sClientApiManger;
    }

    /**这些是管理的API*/
    private static StatusesAPI sStatusesAPI;

    public  StatusesAPI getStatusesAPI() {
        if (AccessTokenManger.isAccessTokenExist(sContext)){
            sStatusesAPI = new StatusesAPI(sContext, XzzConstants.APP_KEY,AccessTokenManger.readAccessToken(sContext));
        }else {
            new Exception("Token为空").printStackTrace();
        }
        return sStatusesAPI;
    }
}
