package me.ticknick.weixzz.database.table;

/**
 * Created by Finderlo on 2016/8/24.
 */

public class CommentStatusDetailTable {
    public static final String NAME = " comment_status_detail";

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

