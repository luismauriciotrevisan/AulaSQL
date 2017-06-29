package com.example.aulasql;

/**
 * Created by LuisMauricioTrevisan on 29/06/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by LuisMauricioTrevisan on 11/06/2017.
 */

//Cria-se um Adapter extendendo RecyclerView.Adapter
//É necessário passar a classe ViewHolder
public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.myViewHolder> {

    List<Aluno> alunosList;
    NoClickDoItem noClickDoItem;

    //O construtor do Adaptador depende do tipo de dados da lista
    public AlunoAdapter(NoClickDoItem handle, List<Aluno> aList){
        this.alunosList = aList;
        this.noClickDoItem = handle;
    }

    //Um ViewHolder representa uma linha de uma lista a ser exibida e pode ter inúmeras Views
    //Neste exemplo a linha tem apenas uma ImageView para a bandeira e um TextView para o Nome do País
    public class myViewHolder extends RecyclerView.ViewHolder{
        public TextView nome, matricula, curso, id;

        public myViewHolder(View view){
            super(view);
            nome = (TextView) view.findViewById(R.id.tv_nome);
            matricula = (TextView) view.findViewById(R.id.tv_mat);
            curso = (TextView) view.findViewById(R.id.tv_curso);
            id = (TextView) view.findViewById(R.id.tv_id);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //chama o onClick da interface passando a view (viewHolder) e posição do item
                    noClickDoItem.onClick(view, getAdapterPosition());
                }
            });
        }

    }


    //Substitui o conteúdo das Views, invocado pelo LayoutManager
    @Override
    public void onBindViewHolder(AlunoAdapter.myViewHolder viewHolder, int position){
        viewHolder.nome.setText(alunosList.get(position).getNome());
        viewHolder.matricula.setText(String.format("%d", alunosList.get(position).getMatricula()));
        viewHolder.curso.setText(alunosList.get(position).getCurso());
        viewHolder.id.setText(alunosList.get(position).getId().toString());

    }

    //Cria novas Views, é invocado pelo LayoutManager
    @Override
    public AlunoAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_row, parent, false);
        return new myViewHolder(itemView);
    }

    //Retorna a quantidade de ítens da lista
    @Override
    public int getItemCount(){
        return alunosList.size();
    }

    //Interface pública para tratar o click de um item da lista
    public interface NoClickDoItem{
        void onClick(View view, int position);
    }
}
