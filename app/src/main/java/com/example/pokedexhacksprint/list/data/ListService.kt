package com.example.pokedexhacksprint.list.data

import com.example.pokedexhacksprint.common.model.PokeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ListService {

    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int = 20, // limita a quantida de pokemons a 20
        @Query("offset") offset: Int = 0, // Offset para buscar pokemons a partir de uma posicao especifica
    ) : Response<PokeResponse>
}