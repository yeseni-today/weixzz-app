package com.finderlo.weixzz.database.Table;

/**
 * Created by Finderlo on 2016/8/19.
 */


public class CommentTimelineTable {

    public static final String NAME = " comment_timeline ";

    public static final String ID = "id";
    public static final String TYPE = "type";
    public static final String JSON = "json";

    public static final String CREATE = "create table " + NAME
            + "("
            + ID + " integer primary key autoincrement,"
            + TYPE  + " text ,"
            + JSON + " text"
            + ");";
}
