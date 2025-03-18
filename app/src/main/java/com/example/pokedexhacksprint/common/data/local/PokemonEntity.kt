package com.example.pokedexhacksprint.common.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedexapp.PokemonDto.Sprites
import com.example.pokedexapp.PokemonDto.StatSlot
import com.example.pokedexapp.PokemonDto.TypeSlot

@Entity
data class PokemonEntity (
    @PrimaryKey
    val id: Int,
    val name: String,
    val url: String?,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlot>,
    val stats: List<StatSlot>,
    val category: String

)

