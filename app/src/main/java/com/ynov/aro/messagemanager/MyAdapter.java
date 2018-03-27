package com.ynov.aro.messagemanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by alexa on 26/03/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    List<Entree> list;
    OnClickListener listener;

    //ajouter un constructeur prenant en entrée une liste
    public MyAdapter(List<Entree> list, OnClickListener l) {
        this.list = list;
        listener = l;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listemessage,viewGroup,false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listener.onClick(v, viewHolder.getPosition());
            }
        });
        return viewHolder;
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Entree myObject = list.get(position);
        myViewHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}