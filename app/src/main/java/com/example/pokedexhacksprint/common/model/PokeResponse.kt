package com.example.pokedexhacksprint.common.model

import com.example.pokedexapp.PokemonDto

data class PokeResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonDto>,

)



