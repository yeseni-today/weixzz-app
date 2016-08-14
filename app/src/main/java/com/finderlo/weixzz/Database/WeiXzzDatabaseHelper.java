package com.finderlo.weixzz.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.finderlo.weixzz.Database.Table.StatusTable;
import com.finderlo.weixzz.Database.Table.UserTable;

/**
 * Created by Finderlo on 2016/8/1 0001.
 */
public class WeiXzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "WeiXzzDatabaseHelper";

    public WeiXzzDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UserTable.CREATE_TABLE_SQL_STATUS);
        sqLiteDatabase.execSQL(StatusTable.CREATE_TABLE_SQL_STATUS);
        Log.d(TAG, "onCreate: 表创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}
