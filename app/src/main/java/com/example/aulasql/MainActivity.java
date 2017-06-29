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
    ArrayList<String> histPedidos = new ArrayList();
    SqlOpenHelper helper;
    SQLiteDatabase database;

    public static final String DBNAME = "pedidos.sqlite";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "pedidos";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String QUANTIDADE = "qtd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new SqlOpenHelper(this, DBNAME, VERSION);
        database = helper.getReadableDatabase();

        String sql = "create table " + TABLE_NAME + "(" + ID + " integer primary key autoincrement not null, "
                + NAME + " text " + ");";



        bt_id = (Button)findViewById(R.id.bt_id);
        bt_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                bt_id.setText(String.format("%d", index));
            }
        });

        bt_add = (Button)findViewById(R.id.bt_add);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues v = new ContentValues();
                v.put(NAME, "Anne");
                v.put(QUANTIDADE, 1);
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
        Cursor listCursor = database.query(TABLE_NAME, new String []{ID, QUANTIDADE, NAME}, null, null, null, null, String.format("%s", NAME));

        listCursor.moveToFirst();

        String str;

        if(! listCursor.isAfterLast()) {
            do {
                Long id = listCursor.getLong(0);
                String name = listCursor.getString(2);
                int qtd = listCursor.getInt(1);
                str = new String(name);
                histPedidos.add(str);
                tv_01.append("\n" + str + qtd);
            } while (listCursor.moveToNext());
        }
        listCursor.close();

    }

    public void addItem(ContentValues v) {
        Long id = database.insert(TABLE_NAME, null, v);
    }

}