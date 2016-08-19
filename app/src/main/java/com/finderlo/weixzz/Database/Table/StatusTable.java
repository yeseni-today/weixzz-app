package com.finderlo.weixzz.database.Table;

/**
 * Created by Finderlo on 2016/8/6.
 */
public class StatusTable {

    public static final String TABLE_NAME_STATUS = " Status ";
    public static final String CREATE_TABLE_SQL = "create table " +TABLE_NAME_STATUS+
            "( " +
            "tableId                    integer primary key autoincrement ," +
            "id                         text ,"     +
            "mid                        text ,"     +
            "idstr                      text ,"     +
            "json                       text "      +
            ")";
}
