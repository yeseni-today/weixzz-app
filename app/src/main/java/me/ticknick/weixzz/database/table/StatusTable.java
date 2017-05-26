package me.ticknick.weixzz.database.table;

/**
 * Created by Finderlo on 2016/8/6.
 */
public class StatusTable {

    public static final String NAME = " home_timeline ";

    public static final String ID = "id";
    public static final String GROUP_ID = "group_id";
    public static final String JSON = "json";

    public static final String CREATE = "create table " + NAME
            + "("
            + ID + " integer primary key autoincrement,"
            + GROUP_ID  + " text ,"
            + JSON + " text"
            + ");";
}
