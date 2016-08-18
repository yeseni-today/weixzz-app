package com.finderlo.weixzz.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.finderlo.weixzz.Database.Table.StatusTable;
import com.finderlo.weixzz.Database.Table.UserTable;
import com.finderlo.weixzz.base.WeiXzzApplication;

/**
 * Created by Finderlo on 2016/8/1 0001.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DB_NAME= "WeiXzzDB";
    private static final int DB_VERSION= 1;

    private static DatabaseHelper sDatabaseHelper;
    private static Context sContext;

    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UserTable.CREATE_TABLE_SQL_STATUS);
        sqLiteDatabase.execSQL(StatusTable.CREATE_TABLE_SQL_STATUS);
        Log.d(TAG, "onCreate: 表创建成功");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {}

    public static synchronized DatabaseHelper getInstance(Context context){
        if (sDatabaseHelper==null){
            sContext = context.getApplicationContext();
            sDatabaseHelper=new DatabaseHelper(sContext,DB_NAME,null,DB_VERSION);
        }
        return sDatabaseHelper;
    }

    public static synchronized DatabaseHelper getInstance(){
        if (sDatabaseHelper==null){
            sContext = WeiXzzApplication.getContext();
            sDatabaseHelper=new DatabaseHelper(sContext,DB_NAME,null,DB_VERSION);
        }
        return sDatabaseHelper;
    }

}
