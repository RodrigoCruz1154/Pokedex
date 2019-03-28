package com.RodrigoCruz.pokdex;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.RodrigoCruz.pokdex.models.Pokemon;
import com.RodrigoCruz.pokdex.models.PokemonResponse;
import com.RodrigoCruz.pokdex.pokeapi.PokeapiService;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recycler;
    private Retrofit retrofit;
    private ListPokemonAdapter listPokemonAdapter;
    private Button btnMostrar;
    private int offset;
    private boolean ableToLoad;
    private String TAG="POKEDEX";
    private LinearLayout pokemonSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Icono en el Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher); //

        //mostrar u ocultar
        findViewById(R.id.Btn_mostrar).setOnClickListener(this);
        btnMostrar = findViewById(R.id.Btn_mostrar);

        recycler = findViewById(R.id.recyclerID);
        final LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(layoutManager);
        listPokemonAdapter = new ListPokemonAdapter(this);
        recycler.setAdapter(listPokemonAdapter);
        recycler.setHasFixedSize(true);
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy>0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();

                    if(ableToLoad){
                        if((visibleItemCount + pastVisibleItems) >= totalItemCount){
                            Log.i(TAG,"Se llegó al final del offset");
                            ableToLoad = false;
                            offset+=20;
                            getDatos(offset);
                        }
                    }
                }
            }
        });

        //implementación de la PokeApi y conversión a objeto mediante gson
        retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();

        //elegir pokemon
        /*pokemonSel = findViewById(R.id.LayoutSel);
        findViewById(R.id.LayoutSel).setOnClickListener(this);*/

        ableToLoad=true;
        offset=0;
        getDatos(offset);


    }

    private void getDatos(int offset) {
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonResponse> pokemonResponseCall = service.getListaPokemon(20,offset);

        pokemonResponseCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                ableToLoad=true;
                if(response.isSuccessful()){
                    PokemonResponse pokemonResponse = response.body();
                    ArrayList<Pokemon> listPokemon = pokemonResponse.getResults();
                    listPokemonAdapter.adicionarListaPokemon(listPokemon);
                }else{
                    Toast.makeText(MainActivity.this,"Su programa ha fallado satisfactoriamente.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                ableToLoad=true;
                Toast.makeText(MainActivity.this,"No se ha establecido una conexión a Internet.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Btn_mostrar:
                if (recycler.getVisibility() == View.GONE){
                    recycler.setVisibility(View.VISIBLE);
                    btnMostrar.setText("HIDE");
                }else{
                    recycler.setVisibility(View.GONE);
                    btnMostrar.setText("SHOW ALL");
                }
                break;
            /*case R.id.LayoutSel:
                Toast.makeText(MainActivity.this,"Ha elegido algo",Toast.LENGTH_SHORT).show();
                break;*/
        }

    }
}
