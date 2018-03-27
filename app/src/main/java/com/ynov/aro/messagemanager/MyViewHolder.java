package com.ynov.aro.messagemanager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by alexa on 26/03/2018.
 */

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView nom;
    private TextView espece;
    private OnClickListener mlistener;

    //itemView est la vue correspondante à 1a cellule
    public MyViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        //c'est ici que l'on fait nos findView

        nom = itemView.findViewById(R.id.nomE);
        espece = itemView.findViewById(R.id.espece);
    }

    @Override
    public void onClick(View view) {
        mlistener.onClick(view, getAdapterPosition());
    }

    //puis ajouter une fonction pour remplir la cellule pour chaques entrées
    public void bind(Entree entree){
        nom.setText(entree.getNom());
        espece.setText(entree.getEspece());
    }
}