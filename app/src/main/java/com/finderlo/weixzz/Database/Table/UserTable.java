package com.finderlo.weixzz.Database.Table;

/**
 * Created by Finderlo on 2016/8/6.
 */
public class UserTable {

        public static final String TABLE_NAME_USER = " User ";
        public static final String CREATE_TABLE_SQL = "create table " +TABLE_NAME_USER+
                "( " +
                "tableId                    integer primary key autoincrement ," +
                "id                         text ,"     +
                "idstr                      text ,"     +
                "json                       text "      +
                ")";

}
