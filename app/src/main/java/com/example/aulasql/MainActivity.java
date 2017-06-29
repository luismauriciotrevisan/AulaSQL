package com.example.aulasql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*
    Declarações de variáveies para as Views da Tela Principal
     */
    Button bt_cancel, bt_del, bt_add, bt_save; //botões na tela principal
    EditText et_nome, et_mat, et_curso; //Campos de Edição na tela principal
    TextView tv_main_id;//mostra o id do aluno selecionado

    /*
    Declaração de um ArrayList contendo instâncias de Aluno
    Esta lista será passado como argumento ao construtor do Adapter da RecyclerView
     */
    List<Aluno> alunosList = new ArrayList<Aluno>(); //lista de objetos alunos

    /*
    Declarações relativas ao Database SQL
     */

    SqlOpenHelper helper; //helper SQL, ajuda a abrir e criar um database
    SQLiteDatabase database;// referência para database, através dele podemos inserir, atualizar, deletar itens no database

    //Definições de String para construção do database
    public static final String DBNAME = "alunos.sqlite";//nome do DB
    public static final int VERSION = 1;//versão do DB
    public static final String TABLE_NAME = "alunos";//nome da tabela
    public static final String ID = "id";//coluna
    public static final String NOME = "nome";//coluna
    public static final String MATRICULA = "matricula";//coluna
    public static final String CURSO = "curso";//coluna

    /*
    Aqui começamos a trabalhar com a RecyclerView
     */
    RecyclerView myRecyclerView; //referência para a RecyclerView que apresentará a listagem de alunos
    RecyclerView.Adapter myAdapter;// adaptador entre o DB/List e a listagem


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //encontrando referências para algumas Views
        et_nome = (EditText)findViewById(R.id.et_nome);
        et_mat = (EditText) findViewById(R.id.et_mat);
        et_curso = (EditText)findViewById(R.id.et_curso);
        tv_main_id = (TextView)findViewById(R.id.tv_main_id);

        /*
        Inicializamos o Helper e o database
         */
        //instancia objeto da classe personalizada SqlOpenHelper, que ajuda a criar e abrir o database
        helper = new SqlOpenHelper(this, DBNAME, VERSION);
        //de posse do helper cria ou abre o databse e mantém uma referência para este em database...
        //...com o qual é possível executar comandos SQL like, query, inserte, delete, add
        database = helper.getReadableDatabase();
        //Agora precisamos ler o database _todo para exibílo na RecyclerView
        LeTodoDatabase();

        /*
        Recuperamos referências e atribuímos funções a cada um dos botões da tela principal
         */
        bt_save = (Button)findViewById(R.id.bt_save);
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] whereArgs = new String[]{tv_main_id.getText().toString()};
                ContentValues values = new ContentValues();
                values.put(NOME, et_nome.getText().toString());
                values.put(MATRICULA, et_mat.getText().toString());
                values.put(CURSO, et_curso.getText().toString());
                database.update(TABLE_NAME, values, "id=?", whereArgs);
                LeTodoDatabase();
                myAdapter.notifyDataSetChanged();
            }
        });

        bt_add = (Button)findViewById(R.id.bt_add);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
                LeTodoDatabase();
                myAdapter.notifyDataSetChanged();
            }
        });

        bt_cancel = (Button)findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_curso.setText("");
                et_mat.setText("");
                et_nome.setText("");
                tv_main_id.setText("");
            }
        });

        bt_del = (Button)findViewById(R.id.bt_del);
        bt_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] whereArgs = new String[]{tv_main_id.getText().toString()};
                database.delete(TABLE_NAME, "id=?", whereArgs);
                LeTodoDatabase();
                myAdapter.notifyDataSetChanged();
            }
        });

        /*
        A seguir trabalhamos com a RecyclerView utilizada para a exibição da lista de alunos
         */
        //encontra uma referência para a view RecyclerView rv_01
        myRecyclerView = (RecyclerView) findViewById(R.id.rv_01);
        //cria um LinearLayoutManager para esta atividade
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //Associa o LinearLayoutManager à RecyclerView
        myRecyclerView.setLayoutManager(mLayoutManager);
        //Criamos uma instância handleClick do tipo interface NoClickDoItem e implementamos o tratamento do click para um item da lista
        AlunoAdapter.NoClickDoItem handleClick = new AlunoAdapter.NoClickDoItem(){
            @Override
            public void onClick(View view, int position) {
                et_nome.setText(alunosList.get(position).getNome());
                et_mat.setText(String.format("%d", alunosList.get(position).getMatricula()));
                et_curso.setText(alunosList.get(position).getCurso());
                tv_main_id.setText(alunosList.get(position).getId().toString());
            }
        };
        //cria uma instância de Adapter
        myAdapter = new AlunoAdapter(handleClick, alunosList);
        //Associa Adapter ao RecyclerView
        myRecyclerView.setAdapter(myAdapter);
    }

    public void LeTodoDatabase(){
        //Criamos um cursor para navegarmos no databese
        Cursor listCursor = database.query(TABLE_NAME, null, null, null, null, null, String.format("%s", ID));
        //posicionamos o cursor na primeira posição da tabela
        listCursor.moveToFirst();

        //executamos o loop até o fim da tabela
        if(! listCursor.isAfterLast()) {
            do {
                //instanciamos um novo Aluno
                Aluno aluno = new Aluno();
                //lemos a primeira coluna e salvamos o id em aluno
                aluno.setId(listCursor.getLong(0));
                //lemos a segunda coluna e salvamos a matricula em aluno
                aluno.setMatricula(listCursor.getInt(1));
                //lemos a terceira coluna e salvamos o id em nome do aluno
                aluno.setNome(listCursor.getString(2));
                //lemos a quarta coluna e salvamos em curso do aluno
                aluno.setCurso(listCursor.getString(3));
                //adicionamos aluno à ListArray
                alunosList.add(aluno);
                //vamos para a próxima linha
            } while (listCursor.moveToNext());
        }
        //encerramos o cursor para fechar o arquivo
        listCursor.close();
    }

    public void addItem() {
        //ContentValues opera como um Bundle on a chave é o nome da coluna da tabela
        ContentValues values = new ContentValues();

        values.put(MATRICULA, et_mat.getText().toString());
        values.put(NOME, et_nome.getText().toString());
        values.put(CURSO, et_curso.getText().toString());
        long id = database.insert(TABLE_NAME, null, values);
    }

}