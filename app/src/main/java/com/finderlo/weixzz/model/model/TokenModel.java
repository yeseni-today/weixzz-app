package com.finderlo.weixzz.model.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Finderlo on 2016/11/13.
 */

public class TokenModel implements Parcelable {


//    KEY_UID+"                         text ,"     +
//    KEY_ACCESS_TOKEN+"                      text ,"     +
//    KEY_EXPIRE_DATE+"                       text "      +
//    KEY_USER_NAME+"                       text "      +
//    KEY_ISCURRENT+"                       text "      +

    private String uid;
    private String token;
    private long expireDate;
    private String userName;
    private boolean isCurrent;


    public TokenModel(){}

    public TokenModel(Parcel in) {
    }

    public static final Creator<TokenModel> CREATOR = new Creator<TokenModel>() {
        @Override
        public TokenModel createFromParcel(Parcel in) {
            return new TokenModel(in);
        }

        @Override
        public TokenModel[] newArray(int size) {
            return new TokenModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }
}
