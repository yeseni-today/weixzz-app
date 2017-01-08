package com.finderlo.weixzz.database.Table;

/**
 * Created by Finderlo on 2016/11/13.
 */

public class TokenTable {

    /**
     * 授权用户的UID，本字段只是为了方便开发者，
     * 减少一次user/show接口调用而返回的，
     * 第三方应用不能用此字段作为用户登录状态的识别，只有access_token才是用户授权的唯一票据。
     */
    public static final String KEY_UID = "uid";
    /**
     * 用户授权的唯一票据，用于调用微博的开放接口，同时也是第三方应用验证微博用户登录的唯一票据，
     * 第三方应用应该用该票据和自己应用内的用户建立唯一影射关系，来识别登录状态，不能使用本返回值里的UID字段来做登录识别。
     */
    public static final String KEY_ACCESS_TOKEN = "access_token";
//    /**
//     * access_token的生命周期，单位是秒数。
//     */
//    private static final String KEY_EXPIRES_IN = "expires_in";
    /**
     * access_token的过期时间。为获得accesstoken的时间加上当前系统时间，单位为秒数
     */
    public static final String KEY_EXPIRE_DATE = "expire_date";

    public static final String KEY_ISCURRENT = "iscurrent";

    public static final String KEY_USER_NAME = "username";

    public static final String TABLE_NAME_Token = " TokensTable ";
    public static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_Token +
            " ( " +
            " tableId integer primary key autoincrement , "
            + KEY_UID + " text , "
            + KEY_ACCESS_TOKEN + " text , "
            + KEY_EXPIRE_DATE + "  text ,"
            + KEY_USER_NAME + "   text ,"
            + KEY_ISCURRENT + "   text " +
            ");";


}
