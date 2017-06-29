package com.example.aulasql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button bt_add, bt_del, bt_id;
    TextView tv_01;
    int index=0;
    ArrayList<String> alunosArray = new ArrayList();
    SqlOpenHelper helper;
    SQLiteDatabase database;

    public static final String DBNAME = "alunos.sqlite";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "alunos";
    public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String MATRICULA = "matricula";
    public static final String CURSO = "curso";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new SqlOpenHelper(this, DBNAME, VERSION);
        database = helper.getReadableDatabase();


        bt_add = (Button)findViewById(R.id.bt_add);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues v = new ContentValues();
                v.put(NOME, "Anne");
                v.put(MATRICULA, 1);
                v.put(CURSO, "ADS");
                addItem(v);
                dbShow();
            }
        });

        bt_del = (Button)findViewById(R.id.bt_del);
        bt_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tv_01 = (TextView)findViewById(R.id.tv_01);
    }

    public void dbShow(){
        Cursor listCursor = database.query(TABLE_NAME, null, null, null, null, null, String.format("%s", ID));
        //"create table " + TABLE_NAME + "(" + " id integer primary key autoincrement not null, "
        //+ " matricula integer, " + " nome text not null" + " curso text not null "+ ");";

        listCursor.moveToFirst();

        String str;
        tv_01.setText("");

        if(! listCursor.isAfterLast()) {
            do {
                Long id = listCursor.getLong(0);
                int mat = listCursor.getInt(1);
                String name = listCursor.getString(2);
                String curso = listCursor.getString(3);
                str = new String(id + ", " + mat + ", " + name + ", " + curso);
                alunosArray.add(str);
                tv_01.append("\n" + str);
            } while (listCursor.moveToNext());
        }
        listCursor.close();

    }

    public void addItem(ContentValues v) {
        Long id = database.insert(TABLE_NAME, null, v);
    }

}