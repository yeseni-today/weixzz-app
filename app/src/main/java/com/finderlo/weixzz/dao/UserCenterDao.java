package com.finderlo.weixzz.dao;

import android.content.Context;

import com.finderlo.weixzz.model.model.UserModel;
import com.google.gson.Gson;

import java.io.IOException;

/**
 * Created by Finderlo on 2016/10/30.
 */

public class UserCenterDao {

    public UserModel getCurrentUserModel(Context context) throws IOException {
        String currentUserUid = TokenDao.getInstance(context).getCurrentTokenModel().getUid();

        WeiboParameters weiboParameters = new WeiboParameters();
        weiboParameters.put("uid",currentUserUid);
        String userjson = HttpClientUtils.doGetRequstWithAceesToken(UrlConstants.USER_SHOW,weiboParameters);
        return new Gson().fromJson(userjson,UserModel.class);
    }



}
