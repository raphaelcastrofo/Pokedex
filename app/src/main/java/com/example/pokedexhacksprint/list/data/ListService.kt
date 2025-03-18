package com.example.pokedexhacksprint.list.data

import com.example.pokedexapp.PokemonDto
import com.example.pokedexhacksprint.common.model.PokeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryName
import retrofit2.http.Url

interface ListService {

    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ) : Response<PokeResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name: String): Response<PokemonDto>
}