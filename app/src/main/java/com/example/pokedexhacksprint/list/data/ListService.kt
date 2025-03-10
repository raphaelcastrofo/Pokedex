package com.example.pokedexhacksprint.list.data

import com.example.pokedexhacksprint.common.model.PokeResponse
import retrofit2.Response
import retrofit2.http.GET

interface ListService {

    @GET("pokemon?limit=40&offset=0")
    suspend fun getPokemons() : Response<PokeResponse>
}