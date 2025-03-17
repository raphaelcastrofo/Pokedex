package com.example.pokedexhacksprint.common.data

import com.example.pokedexapp.PokemonDto.Sprites
import com.example.pokedexapp.PokemonDto.StatSlot
import com.example.pokedexapp.PokemonDto.TypeSlot

data class PokemonListUiState (
    val list : List<PokemonUiData> = emptyList(),
    val isLoading: Boolean = false,
    val isError : Boolean = false
)

data class PokemonUiData(
    val id: Int,
    val name: String,
    val url: String?,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlot>,
    val stats: List<StatSlot>,
    val sprites: Sprites,
   


)