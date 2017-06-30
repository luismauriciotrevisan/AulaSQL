package com.example.aulasql;

/**
 * Created by LuisMauricioTrevisan on 29/06/2017.
 */

public class Aluno {
    private int matricula;
    private String nome;
    private String curso;
    private Long id;

    public void setId(Long index){
        id = index;
    }

    public void setNome(String n){
        nome = n;
    }

    public void setMatricula(int m){
        matricula = m;
    }

    public void setCurso(String c){
        curso = c;
    }

    public String getNome(){
        return nome;
    }

    public String getCurso(){
        return curso;
    }

    public int getMatricula(){
        return matricula;
    }

    public Long getId(){
        return id;
    }
}
