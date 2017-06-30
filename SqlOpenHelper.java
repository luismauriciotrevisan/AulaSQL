package com.example.aulasql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.aulasql.MainActivity.TABLE_NAME;

/**
 * Created by LuisMauricioTrevisan on 28/06/2017.
 */

public class SqlOpenHelper extends SQLiteOpenHelper {

    private String databaseName;
    private int version;

    public SqlOpenHelper(Context context, String dbName, int v){
        super(context, dbName, null, v);
        this.databaseName = dbName;
        this.version = v;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        createDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public void createDatabase(SQLiteDatabase db){
        String sql = "create table " + TABLE_NAME + "(" + " id integer primary key autoincrement not null, "
                + " matricula integer, " + " nome text not null," + " curso text not null "+ ");";
        db.execSQL(sql);
    }

}
