package com.tom.atm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tom on 2016/4/10.
 */
public class MyDBHelper extends SQLiteOpenHelper{
    private static MyDBHelper instance = null;

    public static MyDBHelper getInstance(Context ctx){
        if (instance==null){
            instance = new MyDBHelper(ctx, "expense.db", null, 1);
        }
        return instance;
    }

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE  TABLE main.exp " +
                "(_id INTEGER PRIMARY KEY  NOT NULL , " +
                "cdate DATETIME NOT NULL , " +
                "info VARCHAR, " +
                "amount INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
