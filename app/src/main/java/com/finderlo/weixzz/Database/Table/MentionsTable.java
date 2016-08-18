package com.finderlo.weixzz.Database.Table;

/**
 * Created by Finderlo on 2016/8/6.
 */
public class MentionsTable {

    public static final String TABLE_NAME_MENTIONS = " Mentions ";
    public static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_MENTIONS +
            "( " +
            "tableId                    integer primary key autoincrement ," +
            "user_id                    text ,"     +
            "user_idstr                    text ,"     +
            "id                         text ,"     +
            "mid                        text ,"     +
            "idstr                      text ,"     +
            "json                       text "      +
            ")";
}
