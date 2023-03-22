package com.example.myapplicationlist.Adaptadores;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationlist.Curso;
import com.example.myapplicationlist.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ViewHolder> {

    ArrayList<Curso> lst = null;

    public  ListaAdapter(ArrayList<Curso> lstCursos)
    {
        lst = lstCursos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lst_items, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt1.setText(lst.get(position).Columna1);
        holder.txt2.setText(lst.get(position).Columna2);
        //13131231
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText txt1;
        EditText txt2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);
        }
    }
}
