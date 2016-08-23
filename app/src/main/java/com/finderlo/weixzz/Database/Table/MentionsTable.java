package com.finderlo.weixzz.database.Table;

/**
 * Created by Finderlo on 2016/8/6.
 */
public class MentionsTable {

    public static final String NAME = " mention_timeline ";
    public static final String ID = "id";
    public static final String JSON = "json";

    public static final String CREATE = "create table " + NAME
            + "("
            + ID + " integer primary key autoincrement,"
            + JSON + " text"
            + ");";
}
