package com.example.pokedexhacksprint.common.data


import com.example.pokedexapp.PokemonDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokedexApi {

    @GET("pokemon?limit=151") // Obtendo os primeiros 151 Pokémon (1ª geração)
    suspend fun getPokemonList(): Response<PokemonListResponse>

    @GET("pokemon/{name}") // Detalhes de um Pokémon específico
    suspend fun getPokemonByName(@Path("name") name: String): Response<PokemonDto>
}

data class PokemonListResponse(
    val results: List<PokemonDto>
)
