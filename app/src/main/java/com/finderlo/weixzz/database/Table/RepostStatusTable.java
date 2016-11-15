package com.finderlo.weixzz.database.Table;

/**
 * Created by Finderlo on 2016/8/24.
 */

public class RepostStatusTable {

    public static final String NAME = " repost_status ";

    public static final String ID = "id";
    public static final String STATUS_ID = "status_id";
    public static final String JSON = "json";

    public static final String CREATE = "create table " + NAME
            + "("
            + ID + " integer primary key autoincrement,"
            + STATUS_ID  + " text ,"
            + JSON + " text"
            + ");";
}
