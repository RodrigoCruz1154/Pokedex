package com.RodrigoCruz.pokdex.pokeapi;

import com.RodrigoCruz.pokdex.models.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeapiService {

    @GET("pokemon")
    Call<PokemonResponse> getListaPokemon(@Query("limit") int limit,@Query("offset") int offset);
}
