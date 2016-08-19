package com.finderlo.weixzz.database.Table;

/**
 * Created by Finderlo on 2016/8/19.
 */

public class CommentTable {

    public static final String TABLE_NAME_COMMENT = " Comment ";
    public static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_COMMENT +
            "( " +
            "tableId                    integer primary key autoincrement ," +
            "id                         text ,"     +
            "mid                        text ,"     +
            "idstr                      text ,"     +
            "json                       text "      +
            ")";
}
