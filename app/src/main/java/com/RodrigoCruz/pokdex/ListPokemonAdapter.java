package com.RodrigoCruz.pokdex;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.RodrigoCruz.pokdex.models.Pokemon;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class ListPokemonAdapter extends RecyclerView.Adapter <ListPokemonAdapter.ViewHolder> {

    private ArrayList<Pokemon> dataset;
    private Context context;

    public ListPokemonAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolderDatos, int i) {
        Pokemon p =dataset.get(i);
        viewHolderDatos.nombreTextView.setText(p.getName()); //nombre
        //viewHolderDatos.idPokemon.setText(p.getNumber()); //id
        //get imagen
        Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+p.getNumber()+".png").centerCrop().crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolderDatos.fotoImageView);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaPokemon(ArrayList<Pokemon> listPokemon) {
        dataset.addAll(listPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView fotoImageView;
        private TextView nombreTextView;
        private TextView idPokemon;

        public ViewHolder( View itemView) {
            super(itemView);

            fotoImageView = itemView.findViewById(R.id.FotoID);
            nombreTextView = itemView.findViewById(R.id.DatoID);
            idPokemon = itemView.findViewById(R.id.PkmID);
        }
    }

}
